package com.hb.neobank.razorpay.dao;

import com.hb.neobank.common.CommonListTO;
import com.hb.neobank.razorpay.dto.SubMerchantTO;
import com.hb.neobank.razorpay.model.DocumentBO;
import com.hb.neobank.razorpay.model.SubMerchantBO;
import com.hb.neobank.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {

    private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SubMerchantBO addSubMerchant(SubMerchantBO subMerchantBO) {
        entityManager.persist(subMerchantBO);
        logger.info("SubMerchant saved successfully, RazorpayApiCallHistory Details=" + subMerchantBO);
        return subMerchantBO;
    }

    @Override
    public SubMerchantBO updateSubMerchant(SubMerchantBO subMerchantBO) {
        entityManager.merge(subMerchantBO);
        logger.info("SubMerchant has updated successfully, SubMerchant details=" + subMerchantBO);
        return subMerchantBO;
    }

    @Override
    public SubMerchantBO getSubMerchantById(String accountId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SubMerchantBO> criteriaQuery = criteriaBuilder.createQuery(SubMerchantBO.class);
        Root<SubMerchantBO> root = criteriaQuery.from(SubMerchantBO.class);
        Predicate predicateForId = criteriaBuilder.equal(root.get("id"), accountId);
        Predicate predicateForDeleteFlag = criteriaBuilder.notEqual(root.get("status"), "suspended");
        Predicate predicate = criteriaBuilder.and(predicateForId, predicateForDeleteFlag);
        criteriaQuery.where(predicate);
        return CommonUtil.getSingleResult(entityManager.createQuery(criteriaQuery).getResultList());
    }

    @Override
    public void deleteSubMerchant(String Id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<SubMerchantBO> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(SubMerchantBO.class);
        Root<SubMerchantBO> root = criteriaUpdate.from(SubMerchantBO.class);
        criteriaUpdate.set("status", "suspended");
        criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), Id));
        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public CommonListTO<SubMerchantBO> getSubMerchantList(HashMap<String, Integer> apiReqData) {
        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SubMerchantBO> criteriaQuery = queryBuilder.createQuery(SubMerchantBO.class);
        Root<SubMerchantBO> entityRoot = criteriaQuery.from(SubMerchantBO.class);
        Order order = queryBuilder.desc(entityRoot.get("contact_name"));
        criteriaQuery.orderBy(order);
        ArrayList<Predicate> searchFilter = new ArrayList<>();
        searchFilter.add(queryBuilder.and(queryBuilder.notEqual(entityRoot.get("status"), "suspended")));
        if (CommonUtil.checkNullEmpty(searchFilter)) {
            criteriaQuery.where(searchFilter.toArray(new Predicate[0]));
        }
        TypedQuery<SubMerchantBO> query = entityManager.createQuery(criteriaQuery);
        CommonListTO<SubMerchantBO> data = new CommonListTO<>();
        // Adding Pagination total Count
        CriteriaQuery<Long> criteriaCountQuery = queryBuilder.createQuery(Long.class);
        criteriaCountQuery.where(searchFilter.toArray(new Predicate[0]));
        Root<SubMerchantBO> entityRootCount = criteriaCountQuery.from(SubMerchantBO.class);
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

    @Override
    public DocumentBO addDocument(DocumentBO documentBO) {
        entityManager.persist(documentBO);
        logger.info("Document saved successfully, RazorpayApiCallHistory Details=" + documentBO);
        return documentBO;
    }

    @Override
    public List<DocumentBO> getDocument(String submerchantId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentBO> criteriaQuery = criteriaBuilder.createQuery(DocumentBO.class);
        Root<DocumentBO> root = criteriaQuery.from(DocumentBO.class);
        Predicate predicateForId = criteriaBuilder.equal(root.get("submerchant_id"), submerchantId);
        Predicate predicateForDelete = criteriaBuilder.isNull(root.get("status"));
        Predicate predicate = criteriaBuilder.and(predicateForId, predicateForDelete);
        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public SubMerchantTO getSubMerchantList() {
        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SubMerchantBO> criteriaQuery = queryBuilder.createQuery(SubMerchantBO.class);
        Root<SubMerchantBO> entityRoot = criteriaQuery.from(SubMerchantBO.class);
        Order order = queryBuilder.desc(entityRoot.get("id"));
        criteriaQuery.orderBy(order);
        ArrayList<Predicate> searchFilter = new ArrayList<>();
        searchFilter.add(queryBuilder.and(queryBuilder.equal(entityRoot.get("defaultFlag"), true)));
        if (CommonUtil.checkNullEmpty(searchFilter)) {
            criteriaQuery.where(searchFilter.toArray(new Predicate[0]));
        }
        SubMerchantTO subMerchant = new SubMerchantTO();
        List<SubMerchantBO> list = entityManager.createQuery(criteriaQuery).setMaxResults(1).getResultList();
        if (CommonUtil.checkNullEmpty(list)) {
            subMerchant = modelMapper.map(list.get(0), SubMerchantTO.class);
        } else {
            return null;
        }
        return subMerchant;
    }
}
