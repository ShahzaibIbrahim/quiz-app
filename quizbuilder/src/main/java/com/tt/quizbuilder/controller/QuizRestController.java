package com.tt.quizbuilder.controller;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.tt.quizbuilder.entity.Quiz;
import com.tt.quizbuilder.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/quiz")
public class QuizRestController {

	private QuizService QuizService;

	@Autowired
	public QuizRestController(com.tt.quizbuilder.service.QuizService theQuizService) {
		QuizService = theQuizService;
	}

	// add mapping for GET /Quiz/{QuizId}
	@GetMapping("/attempt/{quizId}")
	public MappingJacksonValue getQuiz(@PathVariable String quizId) {
		
		Quiz quiz = QuizService.findById(quizId);

		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAll();
		FilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("quizFilter", simpleBeanPropertyFilter);

		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(quiz);
		mappingJacksonValue.setFilters(filterProvider);

		return mappingJacksonValue;
		
		/*if (quiz == null) {
			throw new RuntimeException("Quiz id not found - " + quizId);
		}
		
		return quiz;*/
	}

	// add mapping for POST /Quiz - add new Quiz

	@PostMapping("/create")
	public String addQuiz(@RequestBody Quiz quiz, @RequestHeader(value = "Authorization", required = false) String authorizationKey) {

		QuizService.createQuiz(quiz, authorizationKey);

		return quiz.getId();
	}


	@GetMapping("/list")
	public MappingJacksonValue getQuizList(@RequestHeader(value = "Authorization", required = false) String authorizationKey) {

		List<Quiz> quizList = QuizService.getQuizList(authorizationKey);
		SimpleBeanPropertyFilter simpleBeanPropertyFilter =
		SimpleBeanPropertyFilter.serializeAllExcept("questions");
		FilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("quizFilter", simpleBeanPropertyFilter);

		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(quizList);
		mappingJacksonValue.setFilters(filterProvider);

		return mappingJacksonValue;
	}
}










