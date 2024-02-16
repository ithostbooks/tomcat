package com.hb.neobank.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.hb.neobank.common.AppConst;
import com.hb.neobank.dto.ClientDetailsTO;
import com.hb.neobank.service.ClientDetailService;

public class TokenAuthenticationFilter extends GenericFilterBean {

	private final ClientDetailService clientDetailService;

	public TokenAuthenticationFilter(ClientDetailService clientDetailService) {
		super();
		this.clientDetailService = clientDetailService;
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final String clientId = httpRequest.getHeader(AppConst.CLIENT_ID);
		final String clientToken = httpRequest.getHeader(AppConst.CLIENT_TOKEN);

		if (clientId != null && clientToken != null) {
			ClientDetailsTO clientDetails = this.clientDetailService.clientByClientId(clientId);
			if (clientDetails != null) {
				UserSessionContext.setCurrentTenant(clientDetails);
				SecurityContextHolder.getContext()
						.setAuthentication(new UsernamePasswordAuthenticationToken(clientDetails, clientToken));
			}
		}

		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("Access-Control-Allow-Methods", AppConst.HTTP_ALLOWED_METHODS);
		res.setHeader("Access-Control-Max-Age", "3600");
		res.setHeader("Access-Control-Allow-Headers", AppConst.HTTP_ALLOWED_HEADERS);
		chain.doFilter(request, res);
	}
}