package com.FirstEmployeesProject.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.FirstEmployeesProject.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "select * from user where id=:id", nativeQuery = true)
	User findByUserId(long id);

	@Query(value = "SELECT * FROM User where status='active' ORDER BY created_at DESC", nativeQuery = true)
	List<User> findAllOrderByCreatedAtDesc();

	User findByIdAndStatus(long id, String string);

	User findByEmail(String username);

	Optional<User> findByIdAndStatus(int userId, String string);

}
