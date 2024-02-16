package com.hb.neobank.razorpay.model;

import com.hb.neobank.common.Audit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "razorpay_accounts")
public class RazorpayAccountBO extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", updatable = false)
    private Integer accountId;

    @Column
    private String id;

    @Column(name = "contact_id")
    private String contactId;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "bank_account_name")
    private String bankAccountName;

    @Column(name = "bank_account_ifsc")
    private String bankAccountIfsc;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column
    private boolean active;
}
