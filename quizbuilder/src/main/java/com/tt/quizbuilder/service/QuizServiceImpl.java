package com.tt.quizbuilder.service;

import com.tt.quizbuilder.dao.QuizDAO;
import com.tt.quizbuilder.dao.UserDAO;
import com.tt.quizbuilder.entity.Quiz;
import com.tt.quizbuilder.entity.User;
import com.tt.quizbuilder.entity.UserAuthentication;
import com.tt.quizbuilder.exception.ProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    @Transactional
    public Quiz findById(String theId) {
        Quiz quiz = quizDAO.findById(theId).orElse(null);

        if(quiz == null) {
            throw new ProcessException("Quiz not found with id "+theId);
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

    @Override
    public List<Quiz> getQuizList(String authorizationKey) {
        //1. Get User from authentication token
        UserAuthentication userAuthentication = userDAO.getAuthenticationByToken(authorizationKey);
        List<Quiz> quizList = new ArrayList<>();

        if(userAuthentication !=null) {
            User user = userAuthentication.getUser();

            quizList = user.getQuizzes();
        } else {
            throw new ProcessException("User not found");
        }

        return quizList;
    }
}
