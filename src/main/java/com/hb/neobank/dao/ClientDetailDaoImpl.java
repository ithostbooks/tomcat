package com.hb.neobank.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.hb.neobank.common.AppConst;
import com.hb.neobank.config.UserSessionContext;
import com.hb.neobank.dto.ClientDetailsTO;
import com.hb.neobank.model.ClientDetailsBO;

@CacheConfig
@EnableJpaRepositories
@Repository
public class ClientDetailDaoImpl implements ClientDetailDao {

	private static final Logger logger = LoggerFactory.getLogger(ClientDetailDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ClientDetailsBO addClient(ClientDetailsBO clientDetails) {
		entityManager.persist(clientDetails);
		logger.info("ClientDetails saved successfully, ClientDetails Details=" + clientDetails);
		return clientDetails;
	}

	@Override
	public ClientDetailsBO getUserByToken(String clientToken) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ClientDetailsBO> query = builder.createQuery(ClientDetailsBO.class);
		Root<ClientDetailsBO> root = query.from(ClientDetailsBO.class);

		ArrayList<Predicate> filter = new ArrayList<>();
		filter.add(builder.equal(root.get("clientToken"), clientToken));
		filter.add(builder.equal(root.get("status"), AppConst.ClientStatus.ACTIVE));

		query.where(filter.toArray(filter.toArray(new Predicate[0])));
		List<ClientDetailsBO> clientDetails = entityManager.createQuery(query).getResultList();

		if (!clientDetails.isEmpty()) {
			return clientDetails.get(0);
		}
		return null;
	}

	@Override
	public ClientDetailsBO getClientByClientId(String clientId) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ClientDetailsBO> query = builder.createQuery(ClientDetailsBO.class);
		Root<ClientDetailsBO> root = query.from(ClientDetailsBO.class);

		ArrayList<Predicate> filter = new ArrayList<>();
		filter.add(builder.equal(root.get("clientId"), clientId));
		filter.add(builder.equal(root.get("status"), AppConst.ClientStatus.ACTIVE));

		query.where(filter.toArray(filter.toArray(new Predicate[0])));
		List<ClientDetailsBO> clientDetails = entityManager.createQuery(query).getResultList();

		if (!clientDetails.isEmpty()) {
			return clientDetails.get(0);
		}
		return null;
	}

	@Override
	public boolean activateClient(ClientDetailsTO clientDetails) {
		if (clientDetails != null && clientDetails.getClientId() != null && !clientDetails.getClientId().isEmpty()) {

			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaUpdate<ClientDetailsBO> updateCriteriaQuery = builder.createCriteriaUpdate(ClientDetailsBO.class);
			Root<ClientDetailsBO> entityRoot = updateCriteriaQuery.from(ClientDetailsBO.class);

			ArrayList<Predicate> filter = new ArrayList<>();
			filter.add(builder.equal(entityRoot.get("clientId"), clientDetails.getClientId()));

			updateCriteriaQuery.where(filter.toArray(filter.toArray(new Predicate[0])));
			updateCriteriaQuery.set("status", AppConst.ClientStatus.ACTIVE);
			int res = entityManager.createQuery(updateCriteriaQuery).executeUpdate();
			if (res > 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean deActivateClient(ClientDetailsTO clientDetails) {
		if (clientDetails != null && clientDetails.getClientId() != null && !clientDetails.getClientId().isEmpty()) {

			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaUpdate<ClientDetailsBO> updateCriteriaQuery = builder.createCriteriaUpdate(ClientDetailsBO.class);
			Root<ClientDetailsBO> entityRoot = updateCriteriaQuery.from(ClientDetailsBO.class);

			ArrayList<Predicate> filter = new ArrayList<>();
			filter.add(builder.equal(entityRoot.get("clientId"), clientDetails.getClientId()));

			updateCriteriaQuery.where(filter.toArray(filter.toArray(new Predicate[0])));
			updateCriteriaQuery.set("status", AppConst.ClientStatus.DEACTIVE);
			int res = entityManager.createQuery(updateCriteriaQuery).executeUpdate();
			if (res > 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean increaseReqCount() {
		if (UserSessionContext.getCurrentTenant() != null && UserSessionContext.getCurrentTenant().getId() != null) {

			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaUpdate<ClientDetailsBO> updateCriteriaQuery = builder.createCriteriaUpdate(ClientDetailsBO.class);
			Root<ClientDetailsBO> entityRoot = updateCriteriaQuery.from(ClientDetailsBO.class);

			ArrayList<Predicate> filter = new ArrayList<>();
			filter.add(builder.equal(entityRoot.get("id"), UserSessionContext.getCurrentTenant().getId()));
			filter.add(builder.equal(entityRoot.get("status"), AppConst.ClientStatus.ACTIVE));

			updateCriteriaQuery.where(filter.toArray(filter.toArray(new Predicate[0])));
			updateCriteriaQuery.set("reqCount", UserSessionContext.getCurrentTenant().getReqCount() + 1);
			int res = entityManager.createQuery(updateCriteriaQuery).executeUpdate();
			if (res > 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean resetReqLimit(ClientDetailsTO clientDetails, Integer reqLimit) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<ClientDetailsBO> updateCriteriaQuery = builder.createCriteriaUpdate(ClientDetailsBO.class);
		Root<ClientDetailsBO> entityRoot = updateCriteriaQuery.from(ClientDetailsBO.class);

		ArrayList<Predicate> filter = new ArrayList<>();
		filter.add(builder.equal(entityRoot.get("clientId"), clientDetails.getClientId()));
		filter.add(builder.equal(entityRoot.get("status"), AppConst.ClientStatus.ACTIVE));

		updateCriteriaQuery.where(filter.toArray(filter.toArray(new Predicate[0])));
		updateCriteriaQuery.set("reqCount", 0);
		updateCriteriaQuery.set("reqLimit", reqLimit);
		int res = entityManager.createQuery(updateCriteriaQuery).executeUpdate();
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}
}
