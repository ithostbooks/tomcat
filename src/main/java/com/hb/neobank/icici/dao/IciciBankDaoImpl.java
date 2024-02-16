package com.hb.neobank.icici.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hb.neobank.icici.model.IciciBankApiCallHistoryBO;
import com.hb.neobank.icici.model.IciciBankLinkedAccBO;

@Repository
public class IciciBankDaoImpl implements IciciBankDao {

	private static final Logger logger = LoggerFactory.getLogger(IciciBankDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public IciciBankApiCallHistoryBO addApiCallHistory(IciciBankApiCallHistoryBO historyData) {
		entityManager.persist(historyData);
		logger.info("IciciBankApiCallHistory saved successfully, IciciBankApiCallHistory Details=" + historyData);
		return historyData;
	}

	@Override
	public IciciBankLinkedAccBO addIciciBankLinkedAcc(IciciBankLinkedAccBO linkedAcc) {
		entityManager.persist(linkedAcc);
		logger.info("IciciBankLinkedAcc saved successfully, IciciBankLinkedAcc Details=" + linkedAcc);
		return linkedAcc;
	}

	@Override
	public IciciBankLinkedAccBO updateIciciBankLinkedAcc(IciciBankLinkedAccBO linkedAcc) {
		entityManager.merge(linkedAcc);
		logger.info("IciciBankLinkedAcc update successfully, IciciBankLinkedAcc Details=" + linkedAcc);
		return linkedAcc;
	}

	@Override
	public List<IciciBankLinkedAccBO> fetchLinkedAcc(String corpId, String iciciUserId, Long createdBy, String status,
			Long notCreatedBy) {
		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<IciciBankLinkedAccBO> criteriaQuery = queryBuilder.createQuery(IciciBankLinkedAccBO.class);
		Root<IciciBankLinkedAccBO> entityRoot = criteriaQuery.from(IciciBankLinkedAccBO.class);
		ArrayList<Predicate> searchFilter = new ArrayList<>();
		searchFilter.add(queryBuilder.equal(entityRoot.get("corpId"), corpId));
		searchFilter.add(queryBuilder.equal(entityRoot.get("iciciUserId"), iciciUserId));
		if (createdBy != null) {
			searchFilter.add(queryBuilder.equal(entityRoot.get("createdBy"), createdBy));
		}
		if (status != null) {
			searchFilter.add(queryBuilder.equal(entityRoot.get("status"), status));
		}
		if (notCreatedBy != null) {
			searchFilter.add(queryBuilder.notEqual(entityRoot.get("createdBy"), notCreatedBy));
		}
		criteriaQuery.where(searchFilter.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
}
