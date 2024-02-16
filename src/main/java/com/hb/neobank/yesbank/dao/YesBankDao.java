package com.hb.neobank.yesbank.dao;

import com.hb.neobank.icici.model.IciciBankApiCallHistoryBO;
import com.hb.neobank.icici.model.IciciBankLinkedAccBO;
import com.hb.neobank.yesbank.model.YesBankApiCallHistoryBO;
import java.util.List;

public interface YesBankDao {

	YesBankApiCallHistoryBO addApiCallHistory(YesBankApiCallHistoryBO historyData);
}
