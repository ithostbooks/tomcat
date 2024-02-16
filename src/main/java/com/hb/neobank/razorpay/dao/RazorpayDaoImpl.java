package com.hb.neobank.razorpay.dao;

import com.hb.neobank.icici.model.IciciBankLinkedAccBO;
import com.hb.neobank.razorpay.model.RazorpayAccountBO;
import com.hb.neobank.razorpay.model.RazorpayApiCallHistoryBO;
import com.hb.neobank.razorpay.model.RazorpayContactBO;
import com.hb.neobank.razorpay.model.RazorpayPayoutBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RazorpayDaoImpl implements RazorpayDao {

    private static final Logger logger = LoggerFactory.getLogger(RazorpayDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RazorpayApiCallHistoryBO addApiCallHistory(RazorpayApiCallHistoryBO historyData) {
        entityManager.persist(historyData);
        logger.info("RazorpayApiCallHistory saved successfully, RazorpayApiCallHistory Details=" + historyData);
        return historyData;
    }

    @Override
    public RazorpayContactBO addRazorpayContact(RazorpayContactBO razorpayContact) {
        entityManager.persist(razorpayContact);
        logger.info("RazorpayContact saved successfully, RazorpayContact Details=" + razorpayContact);
        return razorpayContact;
    }

    @Override
    public RazorpayAccountBO addRazorpayAccount(RazorpayAccountBO razorpayAccount) {
        entityManager.persist(razorpayAccount);
        logger.info("RazorpayAccount saved successfully, RazorpayAccount Details=" + razorpayAccount);
        return razorpayAccount;
    }

    @Override
    public RazorpayPayoutBO addRazorpayPayout(RazorpayPayoutBO razorpayPayout) {
        entityManager.persist(razorpayPayout);
        logger.info("RazorpayPayout saved successfully, RazorpayPayout Details=" + razorpayPayout);
        return razorpayPayout;
    }

    @Override
    public RazorpayPayoutBO updateRazorpayPayout(RazorpayPayoutBO razorpayPayout) {
        entityManager.merge(razorpayPayout);
        logger.info("RazorpayPayout saved successfully, RazorpayPayout Details=" + razorpayPayout);
        return razorpayPayout;
    }

    @Override
    public RazorpayPayoutBO fetchRazorpayPayoutById(String id) {
        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RazorpayPayoutBO> criteriaQuery = queryBuilder.createQuery(RazorpayPayoutBO.class);
        Root<RazorpayPayoutBO> entityRoot = criteriaQuery.from(RazorpayPayoutBO.class);
        ArrayList<Predicate> searchFilter = new ArrayList<>();
        searchFilter.add(queryBuilder.equal(entityRoot.get("id"), id));
        criteriaQuery.where(searchFilter.toArray(new Predicate[0]));
        List<RazorpayPayoutBO> list = entityManager.createQuery(criteriaQuery).setMaxResults(1).getResultList();
        return list != null && !list.isEmpty() ? list.get(0) : null;
    }

    @Override
    public boolean isContactFound(String id) {
        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RazorpayContactBO> criteriaQuery = queryBuilder.createQuery(RazorpayContactBO.class);
        Root<RazorpayContactBO> entityRoot = criteriaQuery.from(RazorpayContactBO.class);
        ArrayList<Predicate> searchFilter = new ArrayList<>();
        searchFilter.add(queryBuilder.equal(entityRoot.get("id"), id));
        criteriaQuery.where(searchFilter.toArray(new Predicate[0]));
        List<RazorpayContactBO> list = entityManager.createQuery(criteriaQuery).setMaxResults(1).getResultList();
        return list != null && !list.isEmpty() ? true : false;
    }

    @Override
    public boolean isAccountFound(String id) {
        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RazorpayAccountBO> criteriaQuery = queryBuilder.createQuery(RazorpayAccountBO.class);
        Root<RazorpayAccountBO> entityRoot = criteriaQuery.from(RazorpayAccountBO.class);
        ArrayList<Predicate> searchFilter = new ArrayList<>();
        searchFilter.add(queryBuilder.equal(entityRoot.get("id"), id));
        criteriaQuery.where(searchFilter.toArray(new Predicate[0]));
        List<RazorpayAccountBO> list = entityManager.createQuery(criteriaQuery).setMaxResults(1).getResultList();
        return list != null && !list.isEmpty() ? true : false;
    }
}
