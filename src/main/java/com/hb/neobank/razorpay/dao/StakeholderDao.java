package com.hb.neobank.razorpay.dao;

import com.hb.neobank.common.CommonListTO;
import com.hb.neobank.razorpay.model.StakeholderBO;
import com.hb.neobank.razorpay.model.SubMerchantBO;

import java.util.HashMap;

public interface StakeholderDao {
    StakeholderBO addStakeholder (StakeholderBO stakeholder);
    StakeholderBO updateStakeholder (StakeholderBO stakeholder);
    void deleteSubMerchant (String accountId);
    CommonListTO<StakeholderBO> getStakeholderList(HashMap<String, Integer> apiReqData);
}
