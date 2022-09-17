package com.tt.quizbuilder.controller;
import com.tt.quizbuilder.entity.Quiz;
import com.tt.quizbuilder.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/quiz")
public class QuizRestController {

	private QuizService QuizService;

	@Autowired
	public QuizRestController(com.tt.quizbuilder.service.QuizService theQuizService) {
		QuizService = theQuizService;
	}

	// add mapping for GET /Quiz/{QuizId}
	@GetMapping("/{QuizId}")
	public Quiz getQuiz(@PathVariable String quizId) {
		
		Quiz quiz = QuizService.findById(quizId);
		
		if (quiz == null) {
			throw new RuntimeException("Quiz id not found - " + quizId);
		}
		
		return quiz;
	}

	// add mapping for POST /Quiz - add new Quiz

	@PostMapping("/create")
	public String addQuiz(@RequestBody Quiz quiz) {

		QuizService.save(quiz);

		return quiz.getId();
	}
}










