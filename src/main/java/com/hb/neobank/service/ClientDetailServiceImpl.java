package com.hb.neobank.service;

import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.neobank.common.AppConst;
import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.dao.ClientDetailDao;
import com.hb.neobank.dto.ClientDetailsTO;
import com.hb.neobank.model.ClientDetailsBO;

@Service
@Transactional
public class ClientDetailServiceImpl implements ClientDetailService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ClientDetailDao clientDetailDao;

	@Autowired
	private ModelMapper modelMapper;

	ResponseDTO response = new ResponseDTO();

	private void initResponse() {
		response = new ResponseDTO();
		response.setPath("/neobank/client");
	}

	private String randomString(int strLength, String clientName) {
		String generatedString = RandomStringUtils.random(strLength, true, true);
		if (clientName != null) {
			return clientName.toLowerCase() + "-" + generatedString;
		}
		return generatedString;
	}

	@Override
	public ResponseDTO addClient(ClientDetailsTO clientDetails) {
		this.initResponse();
		if (clientDetails.getName() != null && !clientDetails.getName().isEmpty()) {
			clientDetails.setClientId(this.randomString(10, clientDetails.getName()));
			clientDetails.setClientToken(this.randomString(10, clientDetails.getClientId()));
			ClientDetailsBO clientDetailBO = this.modelMapper.map(clientDetails, ClientDetailsBO.class);
			clientDetailBO.setClientToken(this.passwordEncoder.encode(clientDetailBO.getClientToken()));
			clientDetailBO.setAdminAccessFlag(false);
			clientDetailBO.setStatus(AppConst.ClientStatus.ACTIVE);
			clientDetailBO.setReqCount(0);
			clientDetailBO.setReqLimit(0);
			clientDetailBO = this.clientDetailDao.addClient(clientDetailBO);

			if (clientDetailBO != null) {
				response.setStatus(200);
				response.setMessage("Client Added Successfully");
				response.putData("Client Name", clientDetails.getName());
				response.putData("Client ID", clientDetails.getClientId());
				response.putData("Client Token", clientDetails.getClientToken());
				if (clientDetailBO.getDescription() != null) {
					response.putData("Client Description", clientDetailBO.getDescription());
				}
			} else {
				response.setStatus(400);
				response.setMessage("Something went wrong");
			}
		} else {
			response.setStatus(400);
			response.setMessage("Name cannot be empty");
		}
		return response;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ClientDetailsBO clientDetails = this.clientDetailDao.getClientByClientId(username);
		if (clientDetails != null) {
			return this.modelMapper.map(clientDetails, ClientDetailsTO.class);
		}
		return null;
	}

	@Override
	public ClientDetailsTO clientByClientId(String clientId) {
		ClientDetailsBO clientDetails = this.clientDetailDao.getClientByClientId(clientId);
		if (clientDetails != null) {
			return this.modelMapper.map(clientDetails, ClientDetailsTO.class);
		}
		return null;
	}

	@Override
	public ResponseDTO activateClient(ClientDetailsTO clientDetails) {
		this.initResponse();
		boolean status = this.clientDetailDao.activateClient(clientDetails);
		if (status) {
			response.setStatus(200);
			response.setMessage("Client Activated Successfully");
		} else {
			response.setStatus(400);
			response.setMessage("Client Not Found");
		}
		return response;
	}

	@Override
	public ResponseDTO deActivateClient(ClientDetailsTO clientDetails) {
		this.initResponse();
		boolean status = this.clientDetailDao.deActivateClient(clientDetails);
		if (status) {
			response.setStatus(200);
			response.setMessage("Client Deactivated Successfully");
		} else {
			response.setStatus(400);
			response.setMessage("Client Not Found");
		}
		return response;
	}

	@Override
	public boolean increaseReqCount() {
		return this.clientDetailDao.increaseReqCount();
	}

	@Override
	public ResponseDTO resetReqLimit(ClientDetailsTO clientDetails, Integer reqLimit) {
		this.initResponse();
		boolean status = this.clientDetailDao.resetReqLimit(clientDetails, reqLimit);
		if (status) {
			response.setStatus(200);
			response.setMessage("Req Limit Reset Successfully");
		} else {
			response.setStatus(400);
			response.setMessage("Client Not Found or Deactive");
		}
		return response;
	}
}
