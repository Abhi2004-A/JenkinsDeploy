package com.dcl.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	private String userName;
	
	@UpdateTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;
	
	private LocalDateTime modifiedAt;
	
	public LocalDateTime getmodifiedAt() {
		return modifiedAt;
	}

}
