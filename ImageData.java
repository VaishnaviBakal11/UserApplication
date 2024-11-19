package com.FirstEmployeesProject.Entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageData {

	private String name;
	private String address;
	private String email;
	private List<UserImage> userImage;
	public ImageData(String name, String address, String email, List<UserImage> userImage) {
		super();
		this.name = name;
		this.address = address;
		this.email = email;
		this.userImage = userImage;
	}

	

}
