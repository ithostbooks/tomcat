package com.hb.neobank.icici.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hb.neobank.common.Audit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "icici_bank_linked_acc")
public class IciciBankLinkedAccBO extends Audit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "linked_acc_id", updatable = false)
	private Integer linkedAccId;

	@Column(name = "corp_id")
	private String corpId;

	@Column(name = "icici_user_id")
	private String iciciUserId;

	@Column
	private String urn;

	@Column(name = "message_str")
	private String messageStr;

	@Column
	private String status;
}
