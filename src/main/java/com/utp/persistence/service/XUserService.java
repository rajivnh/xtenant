package com.utp.persistence.service;

import java.sql.SQLException;

import com.utp.persistence.model.XUser;

public interface XUserService {
	boolean isValid(String emailId, String password) throws SQLException;
	
	XUser findByEmailId(String emailId) throws SQLException;
}
