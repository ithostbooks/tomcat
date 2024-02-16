package com.hb.neobank.common;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.hb.neobank.config.UserSessionContext;

public class AuditListenerHandler {

	@PrePersist
	public void onPrePersist(Audit audit) {
		if (audit != null) {
			if (UserSessionContext.getCurrentTenant() != null) {
				audit.setCreatedBy(UserSessionContext.getCurrentTenant().getId().longValue());
				audit.setUpdatedBy(UserSessionContext.getCurrentTenant().getId().longValue());
			}
		}
	}

	@PreUpdate
	public void onPreUpdate(Audit audit) {
		if (audit != null) {
			if (UserSessionContext.getCurrentTenant() != null) {
				audit.setUpdatedBy(UserSessionContext.getCurrentTenant().getId().longValue());
			}
		}
	}
}
