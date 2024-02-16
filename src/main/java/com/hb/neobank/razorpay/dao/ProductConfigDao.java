package com.hb.neobank.razorpay.dao;

import com.hb.neobank.razorpay.model.ProductConfigBO;

public interface ProductConfigDao {
    ProductConfigBO addProductConfig (ProductConfigBO productConfigDao);

    ProductConfigBO updateProductConfig(ProductConfigBO productConfigDao);


    ProductConfigBO getProductConfig(String accountId);

}
