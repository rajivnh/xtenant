package com.utp.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.utp.constant.ClientEnum;
import com.utp.context.ClientContextHolder;
import com.utp.jwt.JwtService;
import com.utp.jwt.JwtStore;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
	@Autowired
	JwtService jwtService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		
		this.setXTenantId(request.getHeader("X-TenantId"));
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			
			return;
		}
		
		final String accessToken = authHeader.substring(7);
		final String emailId = jwtService.extractUsername(accessToken);
		
		if(!JwtStore.validate(emailId, accessToken)) {
			filterChain.doFilter(request, response);
			
			return;
		}
		
		UserDetails userDetails = null;
		
		if(userDetails == null) {
			filterChain.doFilter(request, response);
			
			return;
		}
			
        @SuppressWarnings("unused")
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
            
        filterChain.doFilter(request, response);
	}
	
	private void setXTenantId(String xTenantId) {
		xTenantId = Optional
				.ofNullable(xTenantId)
				.orElseThrow(() -> new RuntimeException("X-TenantId is missing in the request header."));

		if(xTenantId.equals(ClientEnum.CIBC.name())) 
			ClientContextHolder.setClientContext(ClientEnum.CIBC);			
		else if(xTenantId.equals(ClientEnum.CITI.name()))
			ClientContextHolder.setClientContext(ClientEnum.CITI);				
		else
			throw new RuntimeException("X-TenantId does not exist.");	
	}
}
