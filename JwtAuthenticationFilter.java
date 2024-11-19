package com.FirstEmployeesProject.JwtAuthentications;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.FirstEmployeesProject.Exception.AuthenticationException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private customJwtUserDetailService customJwtUserDetailServcie;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException, AuthenticationException {
		// Get JWT
		String requestHeaderToken = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;

		if (requestHeaderToken != null && requestHeaderToken.startsWith("Bearer ")) {
			jwtToken = requestHeaderToken.substring(7);
			try {
				// Extract username from the token
				username = jwtUtil.extractUsername(jwtToken);
				request.setAttribute("userId", jwtUtil.extractUserId(jwtToken));
			} catch (ExpiredJwtException e) {
				throw new AuthenticationException("Token has expired", e);
			} catch (JwtException e) {
				throw new AuthenticationException("Invalid token", e);
			}

			// Validate token
			UserDetails userDetails = customJwtUserDetailServcie.loadUserByUsername(username);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		chain.doFilter(request, response);
	}


}
