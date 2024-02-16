package com.hb.neobank.razorpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hb.neobank.common.Audit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "address")
public class AddressBO extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", updatable = false)
    private Integer addressId;
    @Column(name ="street1")
    private String street1;
    @Column(name ="street2")
    private String street2;
    @Column(name ="city")
    private String city;
    @Column(name ="state")
    private String state;
    @Column(name ="postal_code")
    private Integer postal_code;
    @Column(name ="country")
    private String country;
    @Column(name = "address_type")
    private String addressType;
}
