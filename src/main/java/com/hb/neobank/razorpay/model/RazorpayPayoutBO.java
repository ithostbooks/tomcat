package com.hb.neobank.razorpay.model;

import com.hb.neobank.common.Audit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "razorpay_payouts")
public class RazorpayPayoutBO extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payoutId", updatable = false)
    private Integer payoutId;

    @Column
    private String id;

    @Column(name = "razorpayx_account")
    private String razorpayxAccount;

    @Column(name = "fund_account_id")
    private String fundAccountId;

    @Column
    private Integer amount;

    @Column
    private String currency;

    @Column
    private String fees;

    @Column
    private String tax;

    @Column
    private String purpose;

    @Column
    private String utr;

    @Column
    private String mode;

    @Column(name = "reference_id")
    private String referenceId;

    @Column
    private String narration;

    @Column(name = "merchant_id")
    private String merchantId;

    @Column
    private String status;

    @Column(name = "status_details")
    private String statusDetails;

    @Column
    private String uuid;
}
