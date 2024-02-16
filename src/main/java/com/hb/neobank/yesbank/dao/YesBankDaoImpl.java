package com.hb.neobank.yesbank.dao;

import com.hb.neobank.yesbank.model.YesBankApiCallHistoryBO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class YesBankDaoImpl implements YesBankDao {

	private static final Logger logger = LoggerFactory.getLogger(YesBankDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public YesBankApiCallHistoryBO addApiCallHistory(YesBankApiCallHistoryBO historyData) {
		entityManager.persist(historyData);
		logger.info("YesBankApiCallHistory saved successfully, YesBankApiCallHistory Details=" + historyData);
		return historyData;
	}
}
