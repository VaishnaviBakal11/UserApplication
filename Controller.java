package com.FirstEmployeesProject.Controller;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FirstEmployeesProject.Entity.User;
import com.FirstEmployeesProject.Entity.UserImage;
import com.FirstEmployeesProject.Login.LoginReq;
import com.FirstEmployeesProject.Service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/emp")
public class Controller {
	@Autowired
	private UserService userservice;
	
	 @Operation(summary = "User Login", description = "This endpoint authenticates a user and returns a JWT token.")
	   @PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginReq loginRequest) throws AuthenticationException
	{
        return userservice.authenticateUser(loginRequest);

	}

	@Operation(summary = "Say hello", description = "This endpoint returns a greeting message.")
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAllUser() {
		return userservice.getAllUser();

	}

	@Operation(summary = "Say hello", description = "This endpoint returns a greeting message.")
	@GetMapping("/getUser/{userId}")
	public ResponseEntity<Object> getUserById(@PathVariable long userId) {
		return userservice.getUserById(userId);
	}

	@Operation(summary = "Say hello", description = "This endpoint returns a greeting message.")
	@PostMapping("/addUser")
	public  ResponseEntity<Object> createUser(@RequestBody User user) {
		return userservice.saveUser(user);
	}

	@Operation(summary = "Say hello", description = "This endpoint returns a greeting message.")
	@PutMapping("/putUser/{userId}")
	public ResponseEntity<Object> updateUserById(@PathVariable long userId,@RequestBody User updatedUser)
	{
		return userservice.updateUserById(userId, updatedUser);
	}

	@Operation(summary = "Say hello", description = "This endpoint returns a greeting message.")
	@DeleteMapping("/deleteUser/{Id}")
	public ResponseEntity<Object> deleteUserById(@PathVariable long Id)
	{
		return userservice.deleteUserById(Id);
	}
	@Operation(summary = "Say hello", description = "This endpoint returns a greeting message.")
	@PostMapping("/addUserImage/{userId}")
	public ResponseEntity<Object> addUserImage(@PathVariable int userId,@Valid @RequestBody UserImage user)
	{
		return userservice.addUserImage(userId, user);
	}

}
