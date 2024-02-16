package com.hb.neobank.dao;

import com.hb.neobank.dto.ClientDetailsTO;
import com.hb.neobank.model.ClientDetailsBO;

public interface ClientDetailDao {

	ClientDetailsBO addClient(ClientDetailsBO clientDetails);

	ClientDetailsBO getUserByToken(String clientToken);

	ClientDetailsBO getClientByClientId(String clientId);

	boolean activateClient(ClientDetailsTO clientDetails);

	boolean deActivateClient(ClientDetailsTO clientDetails);

	boolean increaseReqCount();

	boolean resetReqLimit(ClientDetailsTO clientDetails, Integer reqLimit);
}
