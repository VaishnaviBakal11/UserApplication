package com.FirstEmployeesProject.JwtAuthentications;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.FirstEmployeesProject.Entity.User;
import com.FirstEmployeesProject.Repository.UserRepository;

@Component
public class customJwtUserDetailService implements UserDetailsService {

	private UserRepository userRepo;

	public customJwtUserDetailService(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with email: " + username);
		}

//		return new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getUserPassword(),
//				new ArrayList<>());
		CustomJwtUserDetail customUserDetails = new CustomJwtUserDetail(user);
		return customUserDetails;
	}

}
