package com.hb.neobank.razorpay.dao;


import com.hb.neobank.common.CommonListTO;
import com.hb.neobank.razorpay.dto.SubMerchantTO;
import com.hb.neobank.razorpay.model.DocumentBO;
import com.hb.neobank.razorpay.model.SubMerchantBO;

import java.util.HashMap;
import java.util.List;

public interface AccountDao  {
    SubMerchantBO addSubMerchant (SubMerchantBO subMerchantBO);

    SubMerchantBO updateSubMerchant (SubMerchantBO subMerchantBO);

    SubMerchantBO getSubMerchantById(String accountId);

    void deleteSubMerchant (String accountId);

    CommonListTO<SubMerchantBO> getSubMerchantList(HashMap<String, Integer> apiReqData);

    DocumentBO addDocument (DocumentBO documentBO);

    List<DocumentBO> getDocument(String subMerchantId);

    SubMerchantTO getSubMerchantList();

}
