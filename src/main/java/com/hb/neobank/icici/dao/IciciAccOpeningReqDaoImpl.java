package com.hb.neobank.icici.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hb.neobank.icici.model.IciciAccOpeningReqBO;

@Repository
public class IciciAccOpeningReqDaoImpl implements IciciAccOpeningReqDao {

	private static final Logger logger = LoggerFactory.getLogger(IciciAccOpeningReqDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public IciciAccOpeningReqBO addIciciAccOpeningReq(IciciAccOpeningReqBO iciciAccOpeningReq) {
		entityManager.persist(iciciAccOpeningReq);
		logger.info("IciciAccOpening saved successfully, IciciAccOpening Details=" + iciciAccOpeningReq);
		return iciciAccOpeningReq;
	}
}
