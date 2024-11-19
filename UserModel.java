package com.FirstEmployeesProject.Login;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserModel {
	
	private String name;
	private String address;
	private String email;
	public UserModel(String name, String address, String email) {
		super();
		this.name = name;
		this.address = address;
		this.email = email;
	}
	
	

}
