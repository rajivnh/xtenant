package com.utp.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "xtenant.security.jwt")
public class JwtProperties {
	private String secretKey;
		
	private AccessToken accessToken;
	
	private RefreshToken refreshToken;
	
	@Data
	public static class AccessToken {
		private long expiration;
	}
	
	@Data
	public static class RefreshToken {
		private long expiration;
	}
}
