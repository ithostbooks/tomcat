package com.hb.neobank.razorpay.model;

import com.hb.neobank.common.Audit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "sub_merchant_profile")
public class ProfileBO extends Audit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", nullable = false)
    private Integer profileId;
    @Column(name ="category")
    private String category;
    @Column(name ="subcategory")
    private String subcategory;
    @Column(name ="description")
    private String description;
    @Column(name="addresses_id",insertable = false,updatable = false)
    private Integer addressesId;
    @Column(name ="business_model")
    private String businessModel;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "addresses_id",referencedColumnName = "addresses_id")
    private AddressesBO addresses;

}
