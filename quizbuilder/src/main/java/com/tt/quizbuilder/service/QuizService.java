package com.tt.quizbuilder.service;

import com.tt.quizbuilder.entity.Quiz;

import java.util.List;
import java.util.Map;

public interface QuizService {
    Quiz findById(String theId);

    void createQuiz(Quiz theQuiz, String authorizationKey);

    List<Quiz> getQuizList(String authorizationKey);

    void deleteQuiz(String quizId, String authorizationKey);

    Map<String, Integer> submitQuiz(String quizId, Map<String, List<Integer>> answerMap);
}
