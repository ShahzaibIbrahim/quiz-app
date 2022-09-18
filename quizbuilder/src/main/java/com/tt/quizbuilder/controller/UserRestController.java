package com.tt.quizbuilder.controller;
import com.tt.quizbuilder.entity.User;
import com.tt.quizbuilder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
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

	@PostMapping("/register")
	public void register(@RequestBody User theUser) {
		// set id to 0 to force to save a new item
		theUser.setUserId(0);

		UserService.save(theUser);
	}

	@PostMapping("/login")
	public String loginUser(@RequestBody User theUser) {

		return UserService.loginUser(theUser);
	}

	@PostMapping("/logout")
	public void logoutUser(@RequestHeader(value = "Authorization", required = false) String authorizationKey) {

		UserService.logoutUser(authorizationKey);
	}
}










