package com.hb.neobank.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client_details")
public class ClientDetailsBO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Integer id;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "client_id", unique = true)
	private String clientId;

	@Column(name = "client_token", unique = true)
	private String clientToken;

	@Column
	private String description;

	@Column(name = "admin_access_flag")
	private boolean adminAccessFlag;

	@Column
	private String status;

	@Column(name = "req_count")
	private Integer reqCount;

	@Column(name = "req_limit")
	private Integer reqLimit;
}
