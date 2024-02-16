package com.hb.neobank.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.dto.ClientDetailsTO;

public interface ClientDetailService extends UserDetailsService {

	ResponseDTO addClient(ClientDetailsTO clientDetails);

	ClientDetailsTO clientByClientId(String clientId);
	
	ResponseDTO activateClient(ClientDetailsTO clientDetails);

	ResponseDTO deActivateClient(ClientDetailsTO clientDetails);
	
	boolean increaseReqCount();

	ResponseDTO resetReqLimit(ClientDetailsTO clientDetails, Integer reqLimit);
}