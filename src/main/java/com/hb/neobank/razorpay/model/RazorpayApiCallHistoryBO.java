package com.hb.neobank.razorpay.model;

import com.hb.neobank.common.Audit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "razorpay_api_call_history")
public class RazorpayApiCallHistoryBO extends Audit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_id", updatable = false)
	private Integer historyId;

	@Column(name = "request_url")
	private String requestUrl;

	@Column(name = "request_method")
	private String requestMethod;

	@Column(name = "request_data")
	private String requestData;

	@Column(name = "response_data")
	private String responseData;
}
