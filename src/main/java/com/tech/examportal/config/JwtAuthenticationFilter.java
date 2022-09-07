package com.tech.examportal.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tech.examportal.entity.User;
import com.tech.examportal.rest.UserResource;
//import com.tech.examportal.security.CustomUserDetails;
import com.tech.examportal.security.CustomUserDetailsService;
import com.tech.examportal.util.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtils jwtUtil;

	private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	//@Autowired
	//private CustomUserDetails userDetails;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		final String jwtHeaderTokenRequest = request.getHeader("Authorization");

		logger.info("Jwt token from request :" + jwtHeaderTokenRequest);

		String username = null;
		String jwtToken = null;

		if (jwtHeaderTokenRequest != null && jwtHeaderTokenRequest.startsWith("Bearer")) {

			try {
				jwtToken = jwtHeaderTokenRequest.substring(7);
				username = this.jwtUtil.extractUsername(jwtToken);
			} catch (ExpiredJwtException e) {
				e.printStackTrace();
				logger.error("Jwt token expired");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());

			}

		} else {
			logger.info("Invalid Token :" + jwtHeaderTokenRequest);

		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			final UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

			if (this.jwtUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}else {
			logger.info("Invalid Token");

		}
		
		filterChain.doFilter(request, response);

	}

}
