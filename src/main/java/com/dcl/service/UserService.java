package com.dcl.service;

import java.util.List;

import com.dcl.dto.UserDto;
import com.dcl.entity.User;
import com.dcl.request.AddUser;

public interface UserService {
	
	UserDto registeruser(AddUser request);
	
	void deleteuser(Integer userId);
	
	UserDto updateUser(Integer userId);
	
	List<UserDto> getAllUser();

}
