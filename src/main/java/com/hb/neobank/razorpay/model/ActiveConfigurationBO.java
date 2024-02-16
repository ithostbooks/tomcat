package com.hb.neobank.razorpay.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "active_configuration")
public class ActiveConfigurationBO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "settlement_id",insertable = false,updatable = false)
    private Integer settlement_id;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "settlement_id", referencedColumnName = "id")
    private SettlementsBO settlements;
}
