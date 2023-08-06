package com.utp.persistence.service;

import java.sql.SQLException;

import com.utp.model.AuthResponse;

public interface AuthService {
	AuthResponse findByEmailId(String emailId, String password) throws SQLException;
}
