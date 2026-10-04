package com.dcl.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUser {
	
	private String email;
	
	private String password;
	
	private String userName;

}
