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
@Table(name = "icici_account_opening_req")
public class IciciAccOpeningReqBO extends Audit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "req_id", updatable = false)
	private Integer reqId;

	@Column(name = "request_data")
	private String requestData;

	@Column(name = "response_data")
	private String responseData;

	@Column(name = "application_no")
	private String applicationNo;

	@Column(name = "tracking_no")
	private String trackingNo;

	@Column(name = "web_url")
	private String webUrl;
}
