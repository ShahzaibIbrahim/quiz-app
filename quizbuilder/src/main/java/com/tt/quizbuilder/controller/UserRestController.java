package com.tt.quizbuilder.controller;
import com.tt.quizbuilder.entity.User;
import com.tt.quizbuilder.exception.GlobalResponse;
import com.tt.quizbuilder.service.UserService;
import com.tt.quizbuilder.util.ResponseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


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
	public GlobalResponse register(@RequestBody User theUser) {
		// set id to 0 to force to save a new item
		theUser.setUserId(0);

		Map responseMap = new HashMap();
		responseMap.put("authorization",  UserService.registerUser(theUser));

		GlobalResponse response = new GlobalResponse();
		response.setData(responseMap);
		response.setResponseCode(ResponseConstants.SUCCESS_CODE);

		return response;
	}

	@PostMapping("/login")
	public GlobalResponse loginUser(@RequestBody User theUser) {
		Map responseMap = new HashMap();
		responseMap.put("authorization",  UserService.loginUser(theUser));

		GlobalResponse response = new GlobalResponse();
		response.setData(responseMap);
		response.setResponseCode(ResponseConstants.SUCCESS_CODE);

		return response;
	}

	@PostMapping("/logout")
	public void logoutUser(@RequestHeader(value = "Authorization", required = false) String authorizationKey) {

		UserService.logoutUser(authorizationKey);
	}
}










