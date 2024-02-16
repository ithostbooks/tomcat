package com.hb.neobank.razorpay.dao;

import com.hb.neobank.razorpay.model.ProductConfigBO;
import com.hb.neobank.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProductConfigDaoImpl implements ProductConfigDao{
    private static final Logger logger = LoggerFactory.getLogger(StakeholderDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductConfigBO addProductConfig(ProductConfigBO productConfigDao) {
        entityManager.persist(productConfigDao);
        logger.info("ProductConfig saved successfully, RazorpayApiCallHistory Details=" + productConfigDao);
        return productConfigDao;
    }

    @Override
    public ProductConfigBO updateProductConfig(ProductConfigBO productConfig) {
        entityManager.merge(productConfig);
        logger.info("ProductConfig saved successfully, RazorpayApiCallHistory Details=" + productConfig);
        return productConfig;
    }

    @Override
    public ProductConfigBO getProductConfig(String accountId) {
        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductConfigBO> criteriaQuery = queryBuilder.createQuery(ProductConfigBO.class);
        Root<ProductConfigBO> entityRoot = criteriaQuery.from(ProductConfigBO.class);
        if(CommonUtil.checkNullEmpty(accountId)){
            criteriaQuery.where(
                    queryBuilder.equal(entityRoot.get("account_id"), accountId)
            );
            criteriaQuery.orderBy(queryBuilder.desc(entityRoot.get("account_id")));
            List<ProductConfigBO> productConfigBO = entityManager.createQuery(criteriaQuery).setMaxResults(1).getResultList();
            if(CommonUtil.checkNullEmpty(productConfigBO)){
                return  productConfigBO.get(0);
            }else {
                return  new ProductConfigBO();
            }
        }
        return null;
    }
}
