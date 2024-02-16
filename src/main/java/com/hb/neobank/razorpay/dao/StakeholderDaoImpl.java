package com.hb.neobank.razorpay.dao;

import com.hb.neobank.common.CommonListTO;
import com.hb.neobank.razorpay.model.StakeholderBO;
import com.hb.neobank.razorpay.model.SubMerchantBO;
import com.hb.neobank.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class StakeholderDaoImpl implements StakeholderDao {

    private static final Logger logger = LoggerFactory.getLogger(StakeholderDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public StakeholderBO addStakeholder(StakeholderBO stakeholder) {
        entityManager.persist(stakeholder);
        logger.info("Stakeholder saved successfully, RazorpayApiCallHistory Details=" + stakeholder);
        return stakeholder;
    }

    @Override
    public StakeholderBO updateStakeholder(StakeholderBO stakeholder) {
        entityManager.merge(stakeholder);
        logger.info("Stakeholder has updated successfully, Stakeholder details=" + stakeholder);
        return stakeholder;
    }

    @Override
    public void deleteSubMerchant(String accountId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<StakeholderBO> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(StakeholderBO.class);
        Root<StakeholderBO> root = criteriaUpdate.from(StakeholderBO.class);
        criteriaUpdate.set("status", "suspended");
        criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), accountId));
        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public CommonListTO<StakeholderBO> getStakeholderList(HashMap<String, Integer> apiReqData) {
        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StakeholderBO> criteriaQuery = queryBuilder.createQuery(StakeholderBO.class);
        Root<StakeholderBO> entityRoot = criteriaQuery.from(StakeholderBO.class);
        Order order = queryBuilder.desc(entityRoot.get("id"));
        criteriaQuery.orderBy(order);
        ArrayList<Predicate> searchFilter = new ArrayList<>();
        searchFilter.add(queryBuilder.and(queryBuilder.notEqual(entityRoot.get("status"), "suspended")));
        if (CommonUtil.checkNullEmpty(searchFilter)) {
            criteriaQuery.where(searchFilter.toArray(new Predicate[0]));
        }
        TypedQuery<StakeholderBO> query = entityManager.createQuery(criteriaQuery);
        CommonListTO<StakeholderBO> data = new CommonListTO<>();
        // Adding Pagination total Count
        CriteriaQuery<Long> criteriaCountQuery = queryBuilder.createQuery(Long.class);
        criteriaCountQuery.where(searchFilter.toArray(new Predicate[0]));
        Root<StakeholderBO> entityRootCount = criteriaCountQuery.from(StakeholderBO.class);
        CriteriaQuery<Long> select = criteriaCountQuery.select(queryBuilder.count(entityRootCount));
        Long count = entityManager.createQuery(select).getSingleResult();
        data.setTotalRow(count);
        int size = count.intValue();
        int limit = apiReqData.get("limit").intValue();
        if (limit != 0) {
            data.setPageCount((size + limit - 1) / limit);
        } else {
            data.setPageCount(1);
        }
        if (apiReqData.get("page").intValue() != 0 && apiReqData.get("limit").intValue() > 0) {
            query.setFirstResult((apiReqData.get("page").intValue() - 1) * apiReqData.get("limit").intValue());
            query.setMaxResults(apiReqData.get("limit").intValue());
        }
        data.setDataList(query.getResultList());
        return data;
    }
}
