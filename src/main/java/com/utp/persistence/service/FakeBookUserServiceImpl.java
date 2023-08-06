package com.utp.persistence.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.utp.persistence.model.XUser;
import com.utp.persistence.repository.XUserRepository;

@Service
public class FakeBookUserServiceImpl implements XUserService {
	@Autowired
	XUserRepository repository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public XUser findByEmailId(String emailId) throws SQLException {
		XUser userDetails = repository.findById(emailId).orElseThrow(() -> new SQLException("Email Id Not Found"));
		
		return userDetails;
	}
	
	@Override
	public boolean isValid(String emailId, String password) throws SQLException {
		XUser userDetails = repository.findById(emailId).orElseThrow(() -> new SQLException("Email Id Not Found"));
		
		return encoder.matches(password, userDetails.getPassword()) ? true : false;
	}
}
