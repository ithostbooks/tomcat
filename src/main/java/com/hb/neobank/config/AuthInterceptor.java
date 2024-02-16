package com.hb.neobank.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hb.neobank.common.ApplicationConfig;
import com.hb.neobank.common.ReqLimitExceedException;
import com.hb.neobank.common.UserNotValidException;
import com.hb.neobank.service.ClientDetailService;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Autowired
	private ClientDetailService clientDetailService;

	@Autowired
	private ApplicationConfig applicationConfig;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (request.getRequestURI().contains("/webhook") || request.getRequestURI().contains("/neo-webhook")) {
			return true;
		}
		if (UserSessionContext.getCurrentTenant() == null) {
			throw new UserNotValidException();
		} else if (request.getRequestURI().contains("/client/") && !UserSessionContext.getCurrentTenant().isAdminAccessFlag()) {
			throw new AccessDeniedException(null);
		} else if (this.applicationConfig.isEnable()) {
			if (!(UserSessionContext.getCurrentTenant().getReqCount() < UserSessionContext.getCurrentTenant().getReqLimit())) {
				throw new ReqLimitExceedException();
			}
			this.clientDetailService.increaseReqCount();
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}
}
