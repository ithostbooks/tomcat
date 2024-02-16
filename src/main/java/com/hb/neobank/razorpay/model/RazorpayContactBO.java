package com.hb.neobank.razorpay.model;

import com.hb.neobank.common.Audit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "razorpay_contacts")
public class RazorpayContactBO extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id", updatable = false)
    private Integer contactId;

    @Column
    private String id;

    @Column(name = "razorpayx_account")
    private String razorpayxAccount;

    @Column
    private String name;

    @Column
    private String contact;

    @Column
    private String email;

    @Column
    private String type;

    @Column
    private boolean active;
}
