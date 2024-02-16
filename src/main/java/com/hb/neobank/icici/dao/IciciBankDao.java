package com.hb.neobank.icici.dao;

import java.util.List;

import com.hb.neobank.icici.model.IciciBankApiCallHistoryBO;
import com.hb.neobank.icici.model.IciciBankLinkedAccBO;

public interface IciciBankDao {

	IciciBankApiCallHistoryBO addApiCallHistory(IciciBankApiCallHistoryBO historyData);

	IciciBankLinkedAccBO addIciciBankLinkedAcc(IciciBankLinkedAccBO linkedAcc);

	IciciBankLinkedAccBO updateIciciBankLinkedAcc(IciciBankLinkedAccBO linkedAcc);

	List<IciciBankLinkedAccBO> fetchLinkedAcc(String corpId, String iciciUserId, Long createdBy, String status,
                                              Long notCreatedBy);
}
