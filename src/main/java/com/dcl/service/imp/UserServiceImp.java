package com.dcl.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dcl.dto.UserDto;
import com.dcl.entity.User;
import com.dcl.exception.AppException;
import com.dcl.repo.UserRepo;
import com.dcl.request.AddUser;
import com.dcl.service.UserService;

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	private UserRepo urepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserDto registeruser(AddUser request) {
		User alreadyExists=urepo.findByEmail(request.getEmail()).orElse(null);
		if(alreadyExists!=null) {
			throw new AppException("User Already Exists", HttpStatus.CONFLICT);
		}
		User u=mapper.map(request, User.class);
		u.setPassword(encoder.encode(u.getPassword()));
		u=urepo.save(u);
		return mapper.map(u, UserDto.class);
	}

	@Override
	public void deleteuser(Integer userId) {
		User u=urepo.findById(userId).orElseThrow(()->new AppException("User Not Found!", HttpStatus.NOT_FOUND));
		urepo.deleteById(userId);
		
	}

	@Override
	public UserDto updateUser(Integer userId) {
		//User u=urepo.findById(userId).orElseThrow(()->new AppException("User Not Found!", HttpStatus.NOT_FOUND));
		return null;
	}

	@Override
	public List<UserDto> getAllUser() {
		return urepo.findAll().stream().map(u->mapper.map(u, UserDto.class)).collect(Collectors.toList());
	}


}
