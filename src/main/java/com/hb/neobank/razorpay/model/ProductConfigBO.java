package com.hb.neobank.razorpay.model;

import com.hb.neobank.razorpay.dto.ProductConfig.ActiveConfigurationTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "product_configuration")
public class ProductConfigBO {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "account_id")
    private String account_id;
    @Column(name = "activation_status")
    private String activation_status;
    @Column(name = "product_name")
    private String product_name;
    @Column(name = "tnc_accepted")
    private boolean tnc_accepted;
    @Column(name = "settlement_id")
    private int settlement_id;
    @Column(name = "active_configuration_id",insertable = false,updatable = false)
    private Integer activeConfiguration_id;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "active_configuration_id",referencedColumnName = "id")
    private ActiveConfigurationBO active_configuration;
}
