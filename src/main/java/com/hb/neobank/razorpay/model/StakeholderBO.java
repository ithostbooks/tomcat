package com.hb.neobank.razorpay.model;

import com.hb.neobank.razorpay.dto.RazorpayError;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "stakeholder")
public class StakeholderBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "percentage_ownership")
    private BigDecimal percentage_ownership;
    @Column(name = "kyc_id", insertable = false, updatable = false)
    private Integer kyc_id;

    @Column(name = "residential_address_id", insertable = false, updatable = false)
    private Integer residential_address_id;

    @Column(name = "phone_id", insertable = false, updatable = false)
    private Integer phone_id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "kyc_id", referencedColumnName = "id")
    private StakeholderKycBO kyc;

//    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
//    @JoinColumn(name = "stakeholder_id", referencedColumnName = "id")
//    private List<StakeholderRelationshipBO> relationship;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "residential_address_id", referencedColumnName = "addresses_id")
    private AddressesBO addresses;

    private transient RazorpayError error;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "phone_id", referencedColumnName = "id")
    private StakeholderPhoneBO phone;
}
