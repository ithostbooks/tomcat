package com.hb.neobank.razorpay.model;

import com.hb.neobank.common.Audit;
import com.hb.neobank.razorpay.dto.RazorpayError;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "razorpay_sub_merchant")
public class SubMerchantBO extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "account_id")
    private String id;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "default_flag")
    private boolean defaultFlag;

    @Column(name = "legal_business_name")
    private String legal_business_name;

    @Column(name = "customer_facing_business_name")
    private String customer_facing_business_name;

    @Column(name = "business_type")
    private String business_type;

    @Column(name = "contact_name")
    private String contact_name;

    @Column(name = "stakeholder_id", insertable = false, updatable = false)
    private String stakeholder_id;

    @Column(name = "profile_id", insertable = false, updatable = false)
    private Integer profileId;

    @Column(name = "legal_info_id", insertable = false, updatable = false)
    private Integer legalInfoId;

    @Column(name = "brand_id", insertable = false, updatable = false)
    private Integer brandId;

    @Column(name = "contact_info_id", insertable = false, updatable = false)
    private Integer contactInfoId;

    @Column(name = "notes_id", insertable = false, updatable = false)
    private Integer notesId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
    private ProfileBO profile;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "notes_id",referencedColumnName = "id")
    private NotesBO notes;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "legal_info_id",referencedColumnName = "id")
    public LegalInfoBO legal_info;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "brand_id",referencedColumnName = "id")
    public BrandBO brand;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_info_id",referencedColumnName = "id")
    public ContactInfoBO contact_info;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "stakeholder_id", referencedColumnName = "id")
    private StakeholderBO stakeholder;

    @Transient
    private transient RazorpayError error;
}
