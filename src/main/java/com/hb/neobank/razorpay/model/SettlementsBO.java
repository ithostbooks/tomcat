package com.hb.neobank.razorpay.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "settlements")
public class SettlementsBO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "bank_id")
    private Integer bankId;
    @Column(name = "account_number")
    private String account_number;
    @Column(name = "ifsc_code")
    private String ifsc_code;
    @Column(name = "beneficiary_name")
    private String beneficiary_name;
}
