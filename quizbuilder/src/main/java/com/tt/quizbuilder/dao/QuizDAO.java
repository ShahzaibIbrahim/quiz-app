package com.tt.quizbuilder.dao;

import com.tt.quizbuilder.entity.Quiz;
import org.springframework.data.repository.CrudRepository;

public interface QuizDAO extends CrudRepository<Quiz, String> {
}
