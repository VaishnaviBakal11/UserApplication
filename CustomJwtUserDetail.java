package com.FirstEmployeesProject.JwtAuthentications;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.FirstEmployeesProject.Entity.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomJwtUserDetail implements UserDetails{
	private static final long serialVersionUID = 1L;
	private User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> simpleGrantedAuthority = new ArrayList<>();
		simpleGrantedAuthority.add(new SimpleGrantedAuthority(user.getRole()));

		return simpleGrantedAuthority;
	}

	@Override
	public String getPassword() {

		return null;
	}

	@Override
	public String getUsername() {

		return user.getEmail();
	}

}
