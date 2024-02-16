package com.hb.neobank.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {

	@Autowired
	public CustomBasicAuthenticationFilter(final AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void onSuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authResult) {
		SecurityContextHolder.getContext().getAuthentication();
	}

	String generateRandomToken(String username) {
		return username + randomWithRange();
	}

	int randomWithRange() {
		int range = (10000000 - 1000) + 1;
		return (int) (Math.random() * range) + 1000;
	}
}
