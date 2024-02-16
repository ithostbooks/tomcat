package com.hb.neobank.yesbank.service;

import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.icici.model.IciciBankLinkedAccBO;
import com.hb.neobank.yesbank.common.YesBankApiException;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class YesBankingServiceImpl implements YesBankingService {

    @Autowired
    private YesBankApiService yesBankApiService;

    private ResponseDTO response = new ResponseDTO();
    private String corpId = null;
    private String iciciUserId = null;
    private List<IciciBankLinkedAccBO> userLinkAccList = null;

    private void fetchLinkedAccount(Object apiReqData, boolean fetchData) {
//		this.corpId = null;
//		this.iciciUserId = null;
//		this.userLinkAccList = null;
//		response = new ResponseDTO();
//		response.setPath("/icici-banking");
//		response.setCode("ICICI");
//		corpId = apiReqData.get(IciciConst.CORP_ID);
//		iciciUserId = apiReqData.get(IciciConst.USER_ID);
//		if (fetchData && corpId != null && iciciUserId != null) {
//			userLinkAccList = this.iciciBankDao.fetchLinkedAcc(corpId, iciciUserId,
//					UserSessionContext.getCurrentTenant().getId().longValue(), null, null);
//			if (userLinkAccList != null && !userLinkAccList.isEmpty()) {
//				if (!userLinkAccList.get(0).getStatus().equals(IciciConst.ACC_STATUS_RESPONSE.ACTIVE)) {
//					userLinkAccList = null;
//					response.setStatus(400);
//					response.setMessage("User Id or Corp Id Not Register.");
//				} else {
//					IciciBankLinkedAccBO iciciBankLinkedAcc = userLinkAccList.get(0);
//					apiReqData.put(IciciConst.URN, iciciBankLinkedAcc.getUrn());
//				}
//			} else {
//				userLinkAccList = null;
//				response.setStatus(400);
//				response.setMessage("User Id or Corp Id Not Found.");
//			}
//		} else {
//			response.setStatus(400);
//			response.setMessage("User Id or Corp Id Not Found.");
//		}
    }

    @Override
    public ResponseDTO txnPayment(Object apiReqData) throws YesBankApiException {
        return this.yesBankApiService.txnPayment(apiReqData);
    }

    @Override
    public ResponseDTO fundConfirmation(Object apiReqData) throws YesBankApiException {
        return this.yesBankApiService.fundConfirmation(apiReqData);
    }

    @Override
    public ResponseDTO txnPaymentDetail(Object apiReqData) throws YesBankApiException {
        return this.yesBankApiService.txnPaymentDetail(apiReqData);
    }

    @Override
    public ResponseDTO accountStatement(Object apiReqData) throws YesBankApiException {
        return this.yesBankApiService.accountStatement(apiReqData);
    }
}
