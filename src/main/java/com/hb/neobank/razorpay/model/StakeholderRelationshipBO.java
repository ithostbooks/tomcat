package com.hb.neobank.razorpay.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "stakeholder_relationship")
public class StakeholderRelationshipBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "stakeholder_id")
    private String stakeholder_id;
    @Column(name = "director")
    private boolean director;
    @Column(name = "executive")
    private boolean executive;
}
