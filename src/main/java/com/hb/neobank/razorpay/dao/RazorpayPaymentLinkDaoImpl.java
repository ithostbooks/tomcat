package com.hb.neobank.razorpay.dao;

import com.hb.neobank.razorpay.model.*;
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
public class RazorpayPaymentLinkDaoImpl implements RazorpayPaymentLinkDao {

    private static final Logger logger = LoggerFactory.getLogger(RazorpayPaymentLinkDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RazorpayApiCallHistoryBO addApiCallHistory(RazorpayApiCallHistoryBO historyData) {
        entityManager.persist(historyData);
        logger.info("RazorpayApiCallHistory saved successfully, RazorpayApiCallHistory Details=" + historyData);
        return historyData;
    }

    @Override
    public RazorpayPaymentLinksBO addRazorpayPaymentLink(RazorpayPaymentLinksBO razorpayPayLink) {
        entityManager.persist(razorpayPayLink);
        logger.info("RazorpayPaymentLink saved successfully, RazorpayPaymentLink Details=" + razorpayPayLink);

        return razorpayPayLink;
    }

    @Override
    public RazorpayPaymentLinksBO updateRazorpayPaymentLink(RazorpayPaymentLinksBO razorpayPayLink) {
        entityManager.merge(razorpayPayLink);
        logger.info("RazorpayPaymentLink updated successfully, RazorpayPaymentLink Details=" + razorpayPayLink);
        return razorpayPayLink;
    }

    @Override
    public List<RazorpayPaymentLinksBO> getAllRzpPaymentLinks() {
        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RazorpayPaymentLinksBO> criteriaQuery = queryBuilder.createQuery(RazorpayPaymentLinksBO.class);
        Root<RazorpayPaymentLinksBO> entityRoot = criteriaQuery.from(RazorpayPaymentLinksBO.class);
        criteriaQuery.select(entityRoot);
        List<RazorpayPaymentLinksBO> list = entityManager.createQuery(criteriaQuery).getResultList();
        return list;
    }

    @Override
    public RazorpayPaymentLinksBO getRazorpayPaymentLinksByPlinkId(String pLinkId) {
        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RazorpayPaymentLinksBO> criteriaQuery = queryBuilder.createQuery(RazorpayPaymentLinksBO.class);
        Root<RazorpayPaymentLinksBO> entityRoot = criteriaQuery.from(RazorpayPaymentLinksBO.class);
        ArrayList<Predicate> searchFilter = new ArrayList<>();
        searchFilter.add(queryBuilder.equal(entityRoot.get("id"), pLinkId));
        criteriaQuery.where(searchFilter.toArray(new Predicate[0]));
        List<RazorpayPaymentLinksBO> list = entityManager.createQuery(criteriaQuery).setMaxResults(1).getResultList();
        return list != null && !list.isEmpty() ? list.get(0) : null;
    }

    @Override
    public RazorpayPaymentLinksBO fetchRazorpayPaymentLinkById(String id) {
        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RazorpayPaymentLinksBO> criteriaQuery = queryBuilder.createQuery(RazorpayPaymentLinksBO.class);
        Root<RazorpayPaymentLinksBO> entityRoot = criteriaQuery.from(RazorpayPaymentLinksBO.class);
        ArrayList<Predicate> searchFilter = new ArrayList<>();
        searchFilter.add(queryBuilder.equal(entityRoot.get("id"), id));
        criteriaQuery.where(searchFilter.toArray(new Predicate[0]));
        List<RazorpayPaymentLinksBO> list = entityManager.createQuery(criteriaQuery).setMaxResults(1).getResultList();
        return list != null && !list.isEmpty() ? list.get(0) : null;
    }
}
