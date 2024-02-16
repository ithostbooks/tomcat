package com.hb.neobank.razorpay.model;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Getter
@Setter
@Entity
@Table(name ="stakeholder_phone")
public class StakeholderPhoneBO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "primary")
    private Long primary;

    @Column(name = "secondary")
    private Long secondary;

}
