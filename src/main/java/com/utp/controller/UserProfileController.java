package com.utp.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utp.persistence.model.XUser;
import com.utp.persistence.service.XUserService;

@RestController
public class UserProfileController {
	@Autowired
	XUserService xUserService;
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/user/profile")
	public ResponseEntity<XUser> findByEmailId() throws SQLException {
		String emailId = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		
		var userDetails = xUserService.findByEmailId(emailId);
		
		userDetails.setPassword(null);
		
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}
}
