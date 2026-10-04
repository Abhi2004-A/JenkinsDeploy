package com.dcl.service.imp;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dcl.entity.User;
import com.dcl.exception.AppException;
import com.dcl.repo.UserRepo;

@Service
public class SecurityService implements UserDetailsService{

	@Autowired
	private UserRepo urepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User u=urepo.findByEmail(email).orElseThrow(()->new AppException("User Not Found!", HttpStatus.NOT_FOUND));
		return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(), Collections.EMPTY_LIST);
	}

}
