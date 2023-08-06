package com.utp.persistence.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utp.jwt.JwtService;
import com.utp.jwt.JwtStore;
import com.utp.model.AuthResponse;
import com.utp.persistence.model.XUser;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	XUserService xUserService;
	
	@Autowired
	JwtService jwtService;
	
	@Override
	public AuthResponse findByEmailId(String emailId, String password) throws SQLException {
		if(!xUserService.isValid(emailId, password)) {
			throw new SQLException("Invalid Password!");
		}
		
		XUser userDetails = xUserService.findByEmailId(emailId);
		
		var accessToken = jwtService.generateToken(userDetails);
	    var refreshToken = jwtService.generateRefreshToken(userDetails);
	    
	    JwtStore.add(emailId, accessToken);
	    
	    AuthResponse response = new AuthResponse();
	    
	    response.setAccessToken(accessToken);
	    response.setRefreshToken(refreshToken);
		    
		return response;
	}
}
