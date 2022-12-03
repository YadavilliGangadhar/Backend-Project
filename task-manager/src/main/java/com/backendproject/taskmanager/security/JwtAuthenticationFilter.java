package com.backendproject.taskmanager.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		// get token from header
		String token = getToken(request);
		
		// check the token weather it is valid or not
		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
			String email = jwtTokenProvider.getEmailFromToken(token);
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken authentication = 
					new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
		
	}
	private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			return token.substring(7, token.length());
		}
		return null;
	}

}
