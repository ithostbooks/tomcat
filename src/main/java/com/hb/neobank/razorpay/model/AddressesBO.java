package com.hb.neobank.razorpay.model;


import com.hb.neobank.common.Audit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class AddressesBO extends Audit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addresses_id", nullable = false)
    private Integer addressesId;
    @Column(name = "operation_id",insertable = false,updatable = false)
    private Integer opreationId;
    @Column(name = "registered_id",insertable = false,updatable = false)
    private Integer registeredId;
    @Column(name = "residential_address_id",insertable = false,updatable = false)
    private Integer residentialId;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "operation_id",referencedColumnName = "address_id")
    private AddressBO operation;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "registered_id",referencedColumnName = "address_id")
    private AddressBO registered;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "residential_address_id",referencedColumnName = "id")
    private StakeholderAddressBO residential;

}
