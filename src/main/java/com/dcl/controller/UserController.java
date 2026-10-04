package com.dcl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcl.dto.UserDto;
import com.dcl.exception.AppException;
import com.dcl.request.AddUser;
import com.dcl.request.LoginRequest;
import com.dcl.response.ApiResponse;
import com.dcl.service.UserService;
import com.dcl.service.imp.SecurityService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService uservice;
	
	@Autowired
	private SecurityService sservice;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authmanager;
	
	@PostMapping("/register")
	public ResponseEntity<?> adduser(@RequestBody AddUser request){
		UserDto dto=uservice.registeruser(request);
		return ResponseEntity.ok(new ApiResponse<>("Account Created Successfully!",dto,HttpStatus.OK));
	}
	
	@PostMapping("/loginu")
	public ResponseEntity<?> Loginuser(@RequestBody LoginRequest request){ 
		UserDetails ud=sservice.loadUserByUsername(request.getEmail());
		Boolean status=encoder.matches(request.getPassword(), ud.getPassword());
		if(!status) {
			throw new AppException("Incorrect Password!", HttpStatus.UNAUTHORIZED);
		}
		
		UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
		Authentication auth=authmanager.authenticate(token);
		Boolean res=auth.isAuthenticated();
		if(!res) {
			throw new AppException("Authentication Failed!", HttpStatus.UNAUTHORIZED);
		}
		return ResponseEntity.ok(new ApiResponse<>("Login Successfull", null, HttpStatus.OK));
	}

}
