package com.hb.neobank.razorpay.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="contact_info")
public class ContactInfoBO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name="refund_id",insertable = false,updatable = false)
    private Integer addressesId;

    @Column(name="support_id",insertable = false,updatable = false)
    private Integer supportId;

    @Column(name="charge_back_id",insertable = false,updatable = false)
    private Integer chargeBackId;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "refund_id")
    private RefundBO refundBO;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "charge_back_id")
    private ChargeBackBO chargeBackBO;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "support_id")
    private SupportBO supportBO;



}
