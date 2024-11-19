package com.FirstEmployeesProject.Service;

import javax.security.sasl.AuthenticationException;

import org.springframework.http.ResponseEntity;

import com.FirstEmployeesProject.Entity.User;
import com.FirstEmployeesProject.Entity.UserImage;
import com.FirstEmployeesProject.Login.LoginReq;

import jakarta.validation.Valid;

public interface UserService {

	ResponseEntity<Object> getAllUser();

	ResponseEntity<Object> getUserById(long userId);

	ResponseEntity<Object> saveUser(User user);

	ResponseEntity<Object> deleteUserById(long userId);

	ResponseEntity<Object> updateUserById(long userId, User updatedUser);
	
	ResponseEntity<Object> authenticateUser(LoginReq loginRequest) throws AuthenticationException;

	ResponseEntity<Object> addUserImage(int userId, @Valid UserImage user);
	

}
