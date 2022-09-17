package com.tt.quizbuilder.service;

import com.tt.quizbuilder.dao.QuizDAO;
import com.tt.quizbuilder.entity.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {


    private QuizDAO quizDAO;


    @Autowired
    public QuizServiceImpl(QuizDAO quizDAO) {
        this.quizDAO = quizDAO;
    }

    @Override
    public Quiz findById(String theId) {
        return quizDAO.findById(theId).orElse(null);
    }

    @Override
    public void save(Quiz theQuiz) {
        quizDAO.save(theQuiz);
    }
}
