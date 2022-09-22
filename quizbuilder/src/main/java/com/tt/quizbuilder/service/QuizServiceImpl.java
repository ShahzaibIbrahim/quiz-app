package com.tt.quizbuilder.service;

import com.tt.quizbuilder.dao.QuizDAO;
import com.tt.quizbuilder.dao.UserDAO;
import com.tt.quizbuilder.entity.*;
import com.tt.quizbuilder.exception.ProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {


    private QuizDAO quizDAO;
    private UserDAO userDAO;


    @Autowired
    public QuizServiceImpl(QuizDAO quizDAO, UserDAO userDAO) {
        this.quizDAO = quizDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Quiz findById(String theId) {
        //1. Finding quiz in db
        Quiz quiz = quizDAO.findById(theId).orElse(null);

        //2. if not found then throw an error
        if(quiz == null) {
            throw new ProcessException("Quiz not found with id "+theId);
        }

        //3. Sending the type of question (multi-answer/ single-answer) to front so we can show checkbox/radio buttons accordingly. also not sending correct key to front end
        for(Question question : quiz.getQuestions()) {
            if(question.getAnswers().stream().filter(x -> x.isCorrect()).count() == 1) {
                question.setSingleAnswer(true);
            } else {
                question.setSingleAnswer(false);
            }

            question.getAnswers().forEach(ans -> ans.setCorrect(null));
        }

        return quiz;
    }

    @Override
    @Transactional
    public void createQuiz(Quiz theQuiz, String authorizationKey) {

        //1. Validate the user. Only logged in user can create new quiz
        UserAuthentication userAuthentication = userDAO.validateToken(authorizationKey);

        if(userAuthentication == null) {
            throw new ProcessException("You need to login to create new quiz");
        }
        theQuiz.setUser(userAuthentication.getUser());

        //2. Save the quiz
        quizDAO.save(theQuiz);
    }

    @Transactional
    @Override
    public List<Quiz> getQuizList(String authorizationKey) {
        //1. Get User from authentication token
        UserAuthentication userAuthentication = userDAO.getAuthenticationByToken(authorizationKey);
        List<Quiz> quizList = new ArrayList<>();

        if(userAuthentication !=null) {
            User user = userAuthentication.getUser();

            // Only return non deleted quizzes
            quizList = user.getQuizzes().stream().filter(x -> x.isActive()).collect(Collectors.toList());
        } else {
            throw new ProcessException("User not found");
        }

        return quizList;
    }

    @Transactional
    @Override
    public void deleteQuiz(String quizId, String authorizationKey) {
        //1. Validate the user. Only logged in user can delete the quiz
        UserAuthentication userAuthentication = userDAO.validateToken(authorizationKey);

        if(userAuthentication == null) {
            throw new ProcessException("You need to login to delete the quiz");
        }

        User user = userAuthentication.getUser();

        //2. Find the quiz to be deleted
        Quiz quizToBeDeleted = user.getQuizzes().stream().filter(x -> x.getId().equalsIgnoreCase(quizId)).findFirst().orElse(null);

        //3. if quiz is found then mark it inactive (soft delete)
        if(quizToBeDeleted != null) {
            quizToBeDeleted.setActive(false);
            quizDAO.save(quizToBeDeleted);
        }
    }

    @Override
    public Map<String, Integer> submitQuiz(String quizId, Map<String, List<Integer>> answerMap) {
        //1. Fetch Quiz
        Quiz quiz = quizDAO.findById(quizId).orElse(null);

        if(quiz == null) {
            throw new ProcessException("Quiz not found with id "+quizId);
        }

        if(answerMap == null) {
            throw new ProcessException("Please choose atleast one option for all the questions");
        }

        //2. Scoring the answers -- All correct options should be selected.
        int questionCount = 0;
        int correctAnswerCount = 0;

        // going through all the questions
        for(Question ques : quiz.getQuestions()) {
            questionCount++;
            int quesId = ques.getId();
            boolean isCorrect = false;
            List<Integer> userAnswers = answerMap.get(String.valueOf(quesId));

            // if any of the questions has no answer then throw an error
            if(userAnswers == null || userAnswers.size() == 0 ) {
                throw new ProcessException("Please choose atleast one option for all the questions");
            }

            // retrieving correct answers for the question
            List<Answer> dbCorrectAnswer =  ques.getAnswers().stream().filter(x -> x.isCorrect()).collect(Collectors.toList());

            // In case of multiple if user has selected more or less answer then we mark it 0 because all of them need to be correct, other wise we check each answer againt correct answers
            if(dbCorrectAnswer.size() == userAnswers.size()) {
                for (Answer answer : dbCorrectAnswer) {
                    if (answer.isCorrect()) {
                        if (!userAnswers.contains(answer.getId())) {
                            isCorrect = false;
                            break;
                        } else {
                            isCorrect = true;
                        }
                    }
                }
            }

            if (isCorrect) {
                correctAnswerCount++;
            }
        }

        //4. Finally returning the total and correct in a map. We let client app to decide how they want to show the message
        Map resultMap = new HashMap();
        resultMap.put("total", questionCount);
        resultMap.put("correct", correctAnswerCount);

        return resultMap;
    }
}
