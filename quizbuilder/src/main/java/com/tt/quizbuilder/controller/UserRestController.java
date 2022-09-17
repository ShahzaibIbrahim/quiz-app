package com.tt.quizbuilder.controller;
import com.tt.quizbuilder.entity.User;
import com.tt.quizbuilder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/User")
public class UserRestController {

	private UserService UserService;

	@Autowired
	public UserRestController(com.tt.quizbuilder.service.UserService theUserService) {
		UserService = theUserService;
	}

	// add mapping for GET /Users/{UserId}
	
	@GetMapping("/{UserId}")
	public User getUser(@PathVariable int UserId) {
		
		User theUser = UserService.findById(UserId);
		
		if (theUser == null) {
			throw new RuntimeException("User id not found - " + UserId);
		}
		
		return theUser;
	}

	// add mapping for POST /Users - add new User

	@PostMapping()
	public User addUser(@RequestBody User theUser) {

		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update

		theUser.setUserId(0);

		UserService.save(theUser);

		return theUser;
	}
}










