package com.FirstEmployeesProject.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FirstEmployeesProject.Entity.UserImage;

public interface UserImageRepository extends JpaRepository<UserImage, Integer>{
	List<UserImage> findByUserId(int id);

}
