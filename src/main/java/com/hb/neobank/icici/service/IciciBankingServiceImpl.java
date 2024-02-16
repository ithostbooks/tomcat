package com.hb.neobank.icici.service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.config.UserSessionContext;
import com.hb.neobank.icici.common.IciciBankApiException;
import com.hb.neobank.icici.common.IciciConst;
import com.hb.neobank.icici.dao.IciciBankDao;
import com.hb.neobank.icici.model.IciciBankLinkedAccBO;

@Service
@Transactional
public class IciciBankingServiceImpl implements IciciBankingService {

	@Autowired
	private IciciBankDao iciciBankDao;

	@Autowired
	private IciciBankApiService iciciBankApiService;

	private ResponseDTO response = new ResponseDTO();
	private String corpId = null;
	private String iciciUserId = null;
	private List<IciciBankLinkedAccBO> userLinkAccList = null;

	private void fetchLinkedAccount(HashMap<String, String> apiReqData, boolean fetchData) {
		this.corpId = null;
		this.iciciUserId = null;
		this.userLinkAccList = null;
		response = new ResponseDTO();
		response.setPath("/icici-banking");
		response.setCode("ICICI");
		corpId = apiReqData.get(IciciConst.CORP_ID);
		iciciUserId = apiReqData.get(IciciConst.USER_ID);
		if (fetchData && corpId != null && iciciUserId != null) {
			userLinkAccList = this.iciciBankDao.fetchLinkedAcc(corpId, iciciUserId,
					UserSessionContext.getCurrentTenant().getId().longValue(), null, null);
			if (userLinkAccList != null && !userLinkAccList.isEmpty()) {
				if (!userLinkAccList.get(0).getStatus().equals(IciciConst.ACC_STATUS_RESPONSE.ACTIVE)) {
					userLinkAccList = null;
					response.setStatus(400);
					response.setMessage("User Id or Corp Id Not Register.");
				} else {
					IciciBankLinkedAccBO iciciBankLinkedAcc = userLinkAccList.get(0);
					apiReqData.put(IciciConst.URN, iciciBankLinkedAcc.getUrn());
				}
			} else {
				userLinkAccList = null;
				response.setStatus(400);
				response.setMessage("User Id or Corp Id Not Found.");
			}
		} else {
			response.setStatus(400);
			response.setMessage("User Id or Corp Id Not Found.");
		}
	}

	@Override
	public ResponseDTO linkAccount(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.fetchLinkedAccount(apiReqData, false);
		if (corpId != null && iciciUserId != null) {
			List<IciciBankLinkedAccBO> linkAccList = this.iciciBankDao.fetchLinkedAcc(corpId, iciciUserId, null,
					IciciConst.ACC_STATUS_RESPONSE.ACTIVE, null);
			if (linkAccList != null && !linkAccList.isEmpty()) {
				userLinkAccList = this.iciciBankDao.fetchLinkedAcc(corpId, iciciUserId,
						UserSessionContext.getCurrentTenant().getId().longValue(), null, null);
				if (userLinkAccList != null && !userLinkAccList.isEmpty()) {
					if (!userLinkAccList.get(0).getStatus().equals(IciciConst.ACC_STATUS_RESPONSE.ACTIVE)) {
						this.reRegistration(apiReqData);
					} else {
						response.setStatus(200);
						response.setMessage(userLinkAccList.get(0).getMessageStr());
						response.putData(IciciConst.CORP_ID, userLinkAccList.get(0).getCorpId());
						response.putData(IciciConst.USER_ID, userLinkAccList.get(0).getIciciUserId());
						response.putData(IciciConst.URN, userLinkAccList.get(0).getUrn());
						response.putData(IciciConst.STATUS, userLinkAccList.get(0).getStatus());
					}
				} else {
					response.setStatus(200);
					response.setMessage(linkAccList.get(0).getMessageStr());
					response.putData(IciciConst.CORP_ID, linkAccList.get(0).getCorpId());
					response.putData(IciciConst.USER_ID, linkAccList.get(0).getIciciUserId());
					response.putData(IciciConst.URN, linkAccList.get(0).getUrn());
					response.putData(IciciConst.STATUS, linkAccList.get(0).getStatus());

					IciciBankLinkedAccBO iciciBankLinkedAcc = new IciciBankLinkedAccBO();
					iciciBankLinkedAcc.setCorpId(linkAccList.get(0).getCorpId());
					iciciBankLinkedAcc.setIciciUserId(linkAccList.get(0).getIciciUserId());
					iciciBankLinkedAcc.setUrn(linkAccList.get(0).getUrn());
					iciciBankLinkedAcc.setMessageStr(linkAccList.get(0).getMessageStr());
					iciciBankLinkedAcc.setStatus(linkAccList.get(0).getStatus());
					this.iciciBankDao.addIciciBankLinkedAcc(iciciBankLinkedAcc);
				}
			} else {
				Random rand = new Random();
				String uniqueUrn = iciciUserId + rand.nextInt(100000);
				linkAccList = this.iciciBankDao.fetchLinkedAcc(corpId, iciciUserId, null, null, null);
				if (linkAccList != null && !linkAccList.isEmpty()) {
					uniqueUrn = linkAccList.get(0).getUrn();
				}
//				response.setStatus(200);
//				response.setMessage("User details are saved successfully and pending for self approval.");
				apiReqData.put(IciciConst.URN, uniqueUrn);
				response = this.iciciBankApiService.linkAccount(apiReqData);
				if (response.getStatus() == 200) {
					IciciBankLinkedAccBO iciciBankLinkedAcc = new IciciBankLinkedAccBO();
					iciciBankLinkedAcc.setCorpId(corpId);
					iciciBankLinkedAcc.setIciciUserId(iciciUserId);
					iciciBankLinkedAcc.setUrn(uniqueUrn);
					response.putData(IciciConst.CORP_ID, corpId);
					response.putData(IciciConst.USER_ID, iciciUserId);
					response.putData(IciciConst.URN, uniqueUrn);
					response.putData(IciciConst.STATUS, IciciConst.ACC_STATUS_RESPONSE.PENDING);
					iciciBankLinkedAcc.setStatus(IciciConst.ACC_STATUS_RESPONSE.PENDING);
					iciciBankLinkedAcc.setMessageStr(response.getMessage());
					this.iciciBankDao.addIciciBankLinkedAcc(iciciBankLinkedAcc);
				}
			}
		}
		return response;
	}

	@Override
	public ResponseDTO checkStatus(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.fetchLinkedAccount(apiReqData, false);
		userLinkAccList = this.iciciBankDao.fetchLinkedAcc(corpId, iciciUserId,
				UserSessionContext.getCurrentTenant().getId().longValue(), null, null);
		if (userLinkAccList != null && !userLinkAccList.isEmpty()) {
			IciciBankLinkedAccBO iciciBankLinkedAcc = userLinkAccList.get(0);
			apiReqData.put(IciciConst.URN, iciciBankLinkedAcc.getUrn());
//			response.setStatus(200);
//			response.setMessage("Registered");
			response = this.iciciBankApiService.checkStatus(apiReqData);
			if (response.getStatus() == 200) {
				response.setMessage(response.getMessage());
				iciciBankLinkedAcc.setMessageStr(response.getMessage());
				response.putData(IciciConst.STATUS, response.getMessage().toUpperCase());
				iciciBankLinkedAcc.setStatus(response.getMessage().toUpperCase());
				this.iciciBankDao.updateIciciBankLinkedAcc(iciciBankLinkedAcc);
			}
		}
		return response;
	}

	@Override
	public ResponseDTO deRegistration(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.fetchLinkedAccount(apiReqData, false);
		userLinkAccList = this.iciciBankDao.fetchLinkedAcc(corpId, iciciUserId,
				UserSessionContext.getCurrentTenant().getId().longValue(), null, null);
		if (userLinkAccList != null && !userLinkAccList.isEmpty()) {
			IciciBankLinkedAccBO iciciBankLinkedAcc = userLinkAccList.get(0);
			List<IciciBankLinkedAccBO> otherLinkedAccList = this.iciciBankDao.fetchLinkedAcc(corpId, iciciUserId, null,
					IciciConst.ACC_STATUS_RESPONSE.ACTIVE, UserSessionContext.getCurrentTenant().getId().longValue());
			if (otherLinkedAccList != null && !otherLinkedAccList.isEmpty()) {
				response.setStatus(200);
				response.setMessage(IciciConst.ACC_STATUS_RESPONSE.DE_ACTIVE);
			} else {
				apiReqData.put(IciciConst.URN, iciciBankLinkedAcc.getUrn());
				response = this.iciciBankApiService.deRegistration(apiReqData);
			}
			if (response.getStatus() == 200) {
				response.setMessage(response.getMessage());
				iciciBankLinkedAcc.setMessageStr(response.getMessage());
				response.putData(IciciConst.STATUS, IciciConst.ACC_STATUS_RESPONSE.DE_ACTIVE);
				iciciBankLinkedAcc.setStatus(IciciConst.ACC_STATUS_RESPONSE.DE_ACTIVE);
				this.iciciBankDao.updateIciciBankLinkedAcc(iciciBankLinkedAcc);
			}
		}
		return response;
	}

	@Override
	public ResponseDTO reRegistration(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.fetchLinkedAccount(apiReqData, false);
		userLinkAccList = this.iciciBankDao.fetchLinkedAcc(corpId, iciciUserId,
				UserSessionContext.getCurrentTenant().getId().longValue(), null, null);
		if (userLinkAccList != null && !userLinkAccList.isEmpty()) {
			IciciBankLinkedAccBO iciciBankLinkedAcc = userLinkAccList.get(0);
			List<IciciBankLinkedAccBO> otherLinkedAccList = this.iciciBankDao.fetchLinkedAcc(corpId, iciciUserId, null,
					IciciConst.ACC_STATUS_RESPONSE.ACTIVE, UserSessionContext.getCurrentTenant().getId().longValue());
			if (otherLinkedAccList != null && !otherLinkedAccList.isEmpty()) {
				response.setStatus(200);
				response.putData(IciciConst.URN, otherLinkedAccList.get(0).getUrn());
				response.setMessage(IciciConst.ACC_STATUS_RESPONSE.ACTIVE);
			} else {
				apiReqData.put(IciciConst.URN, iciciBankLinkedAcc.getUrn());
				response = this.iciciBankApiService.reRegistration(apiReqData);
			}
			if (response.getStatus() == 200) {
				response.setMessage(response.getMessage());
				iciciBankLinkedAcc.setMessageStr(response.getMessage());
				response.putData(IciciConst.STATUS, IciciConst.ACC_STATUS_RESPONSE.PENDING);
				iciciBankLinkedAcc.setStatus(IciciConst.ACC_STATUS_RESPONSE.PENDING);
				this.iciciBankDao.updateIciciBankLinkedAcc(iciciBankLinkedAcc);
			}
		}
		return response;
	}

	@Override
	public ResponseDTO fetchBalance(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.fetchLinkedAccount(apiReqData, true);
		if (userLinkAccList != null && !userLinkAccList.isEmpty()) {
			response = this.iciciBankApiService.fetchBalance(apiReqData);
		}
		return response;
	}

	@Override
	public ResponseDTO fetchStatement(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.fetchLinkedAccount(apiReqData, true);
		if (userLinkAccList != null && !userLinkAccList.isEmpty()) {
			response = this.iciciBankApiService.fetchStatement(apiReqData);
		}
		return response;
	}

	@Override
	public ResponseDTO txnOtpGeneration(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.fetchLinkedAccount(apiReqData, true);
		if (userLinkAccList != null && !userLinkAccList.isEmpty()) {
			response = this.iciciBankApiService.txnOtpGeneration(apiReqData);
			if (response.getStatus() == 200) {
				response.putData(IciciConst.STATUS, response.getMessage().toUpperCase());
				response.setMessage("OTP Sent");
			}
		}
		return response;
	}

	@Override
	public ResponseDTO txnPayment(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.fetchLinkedAccount(apiReqData, true);
		if (userLinkAccList != null && !userLinkAccList.isEmpty()) {
//			response.setStatus(200);
//			response.setMessage("PENDING FOR PROCESSING");
			response = this.iciciBankApiService.txnPayment(apiReqData);
			if (response.getStatus() == 200) {
				response.setMessage(response.getMessage());
				response.putData(IciciConst.STATUS, response.getMessage().toUpperCase());
			}
		}
		return response;
	}

	@Override
	public ResponseDTO txnStatus(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.fetchLinkedAccount(apiReqData, true);
		if (userLinkAccList != null && !userLinkAccList.isEmpty()) {
//			response.setStatus(200);
//			response.setMessage("SUCCESS");
			response = this.iciciBankApiService.txnStatus(apiReqData);
			if (response.getStatus() == 200) {
				response.setMessage(response.getMessage());
				response.putData(IciciConst.STATUS, response.getMessage().toUpperCase());
			}
		}
		return response;
	}
}
