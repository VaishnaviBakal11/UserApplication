package com.FirstEmployeesProject.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.FirstEmployeesProject.Entity.ImageData;
import com.FirstEmployeesProject.Entity.User;
import com.FirstEmployeesProject.Entity.UserImage;
import com.FirstEmployeesProject.Exception.ResourceNotFoundException;
import com.FirstEmployeesProject.JwtAuthentications.JwtUtil;
import com.FirstEmployeesProject.JwtAuthentications.customJwtUserDetailService;
import com.FirstEmployeesProject.Login.LoginReq;
import com.FirstEmployeesProject.Login.UserModel;
import com.FirstEmployeesProject.Repository.UserImageRepository;
import com.FirstEmployeesProject.Repository.UserRepository;
import com.FirstEmployeesProject.Service.UserService;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

	Map<Object, Object> response;

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserImageRepository userImageRepository;
	
	@Autowired
	private JwtUtil j;

	@Autowired
	private customJwtUserDetailService customJwtUserDetailService;
	
	 @Autowired
	    private BCryptPasswordEncoder passwordEncoder;


	@Override
	public ResponseEntity<Object> getAllUser() {
		List<User> data = repository.findAllOrderByCreatedAtDesc();
		response = new HashMap<>();
		if (data == null || data.isEmpty()) {
			response.put("status", "Failed");
			response.put("message", "User details not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} 
		
		List<ImageData> imageData = data.stream()
		        .map(user -> new ImageData(
		            user.getName(),
		            user.getAddress(),
		            user.getEmail(),
		            user.getUserImage()
		        ))
		        .collect(Collectors.toList());
		response.put("ImageData", imageData);
		return ResponseEntity.status(HttpStatus.OK).body(response);
		

//		
//		List<UserModel> userModel=data.stream()
//				.map(user ->new UserModel(user.getName(),user.getAddress(),
//						user.getEmail())).collect(Collectors.toList());
//		
//			response.put("User Details", userModel);
			//response.put("Message", "User Details");
		//return ResponseEntity.status(HttpStatus.OK).body(response);
//		else {
//			response.put("userDetails", data);
//			return ResponseEntity.status(HttpStatus.OK).body(response);
//		}
		
		
	}
	
	 

	@Override
	public ResponseEntity<Object> getUserById(long id) {
		User data = repository.findByIdAndStatus(id, "active");
		response = new HashMap<>();
		if (data == null) {
			response.put("status", "Failed");
			response.put("message", "User details not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
			response.put("userDetails", data);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	@Override
	public ResponseEntity<Object> saveUser(User user) {
		response=new HashMap<>();
		String token=null;
		user.setStatus("active");
		User existingUser = repository.findByEmail(user.getEmail());
				
		if(existingUser!=null)
		{
			response.put("Status", "Failed");
			response.put("Message", "Email is Already in Used");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		User savedUser = repository.save(user);
		
// 		Step1
		final UserDetails ud = customJwtUserDetailService.loadUserByUsername(user.getEmail());
//		Step2
		token = j.generateToken(ud, savedUser.getId());
//		Step3
		final String expirationDate = j.extractExpiration(token).toLocaleString();
		
		response = new HashMap<>();
		response.put("status", "Success");
		response.put("message", "User created successfully");
		response.put("token", token);
		response.put("Expiration time", expirationDate);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Override
	public ResponseEntity<Object> deleteUserById(long userId) {
		Optional<User> user = repository.findById(userId);
		if (user.isEmpty()) {
			response = new HashMap<>();
			response.put("status", "failed");
			response.put("message", "user not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
			repository.deleteById(userId);
			response = new HashMap<>();
			response.put("status", "success");
			response.put("message", "user found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		}

	}

	@Override
	public ResponseEntity<Object> updateUserById(long userId, User updatedUser) {
		Optional<User> user = repository.findById(userId);
		response = new HashMap<>();
		if (user.isEmpty()) {

			response.put("status", "failed");
			response.put("message", "user not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {

			user.get().setName(updatedUser.getName());
			user.get().setEmail(updatedUser.getEmail());
			user.get().setStatus(updatedUser.getStatus());
			repository.save(user.get());

			response.put("status", "success");
			response.put("message", "User details are updated succsss");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}

	}

	@Override
	public ResponseEntity<Object> authenticateUser(LoginReq  loginRequest) throws AuthenticationException {
		
		String email=loginRequest.getEmail();
		String password=loginRequest.getPassword();
		
		User user = repository.findByEmail(email);
		if(user==null)
		{
			throw new AuthenticationException("Invalid Email or Password");
		}
		
		  // Validate the password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }
        
        // Generate JWT token
        final String token = j.generateToken(customJwtUserDetailService.loadUserByUsername(email), user.getId());

        // Prepare the response
        return ResponseEntity.status(HttpStatus.OK)
                             .body(Map.of("status", "Success", "message", "Login successful", "token", token));
    }
	
	@Override
	public ResponseEntity<Object> addUserImage(int userId, @Valid UserImage user) {
		response = new HashMap<>();
		User data = repository.findByIdAndStatus(userId,"active")
				.orElseThrow(() -> new ResourceNotFoundException("User eith the id" + userId + "not found"));
		
		UserImage ob = new UserImage();
		ob.setImageUrl(user.getImageUrl());
		ob.setUser(data);
		userImageRepository.save(ob);
		response.put("status", "success");
		response.put("message", "Image stored successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}
	
	

	}


