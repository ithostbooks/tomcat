package com.hb.neobank.razorpay.model;

import com.hb.neobank.common.Audit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "razorpay_payment_links")
public class RazorpayPaymentLinksBO extends Audit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_link_id", updatable = false)
    private Integer payLinkId;

    @Column(name = "expire_by")
    private Integer expireBy;

    @Column
    private String id;

    @Column
    private double amount;

    @Column(name = "amount_paid")
    private double amountPaid;

    @Column(name = "secret_id")
    private String secretId;

    @Column(name = "cancelled_at")
    private Integer cancelledAt;

    @Column(name = "short_url")
    private String shortUrl;

    @Column
    private String description;

    private String name;

    private String email;

    private String contact;

    @Column(name = "reference_id")
    private String referenceId;

    @Column
    private String status;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "uuid")
    private String uuid;
}
