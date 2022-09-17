package com.tt.quizbuilder.service;

import com.tt.quizbuilder.entity.Quiz;

public interface QuizService {
    public Quiz findById(String theId);

    public void save(Quiz theQuiz);

}
