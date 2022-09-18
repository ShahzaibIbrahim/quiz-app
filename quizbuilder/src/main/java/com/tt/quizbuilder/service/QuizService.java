package com.tt.quizbuilder.service;

import com.tt.quizbuilder.entity.Quiz;

import java.util.List;

public interface QuizService {
    public Quiz findById(String theId);

    public void createQuiz(Quiz theQuiz, String authorizationKey);

    List<Quiz> getQuizList(String authorizationKey);
}
