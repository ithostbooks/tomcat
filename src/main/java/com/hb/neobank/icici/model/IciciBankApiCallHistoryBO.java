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
@Table(name = "icici_bank_api_call_history")
public class IciciBankApiCallHistoryBO extends Audit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_id", updatable = false)
	private Integer historyId;

	@Column(name = "request_data")
	private String requestData;

	@Column(name = "request_type")
	private String requestType;

	@Column(name = "response_data")
	private String responseData;
}
