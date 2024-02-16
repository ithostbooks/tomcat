package com.hb.neobank.common;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditTO {

	private Date createdAt;
	private Long createdBy;
	private Date updatedAt;
	private Long updatedBy;
}