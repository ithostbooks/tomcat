package com.hb.neobank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hb.neobank.dto.ClientDetailsTO;

public class UserSessionContext {

	private static final Logger logger = LoggerFactory.getLogger(UserSessionContext.class.getName());

	private static final ThreadLocal<ClientDetailsTO> currentTenant = new ThreadLocal<>();

	public static void setCurrentTenant(ClientDetailsTO userTO) {
		logger.debug("Client Details session Context ************************************ ===> " + userTO);
		currentTenant.set(userTO);
	}

	public static ClientDetailsTO getCurrentTenant() {
		return currentTenant.get();
	}

	public static void clear() {
		currentTenant.set(null);
	}

}
