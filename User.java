package com.FirstEmployeesProject.Entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class User {
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotEmpty(message = "Name cannot be empty or blank")
	private String name;
	
	private String address;
	
	@JsonIgnore
	private String status;
	
	@Email
	private String email;
	
	private String password;
	
    @Pattern(regexp = "^(ROLE_USER|ROLE_ADMIN)$", message = "Invalid role. Only 'ROLE_USER' and 'ROLE_ADMIN' are allowed.")
	private String role;
    
    @JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserImage> userImage;


	
	@JsonIgnore
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@JsonIgnore
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@PrePersist
	public void hashPassword() {
        if (password != null) {
            this.password = new BCryptPasswordEncoder().encode(password);
        }
    }

}
