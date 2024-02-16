package com.hb.neobank.razorpay.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "support")
public class SupportBO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email")
    public String email;
    @Column(name = "phone")
    public String phone;
    @Column(name = "policy_url")
    public String policy_url;

}
