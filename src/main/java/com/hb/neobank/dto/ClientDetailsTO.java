package com.hb.neobank.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDetailsTO implements UserDetails {

	private static final long serialVersionUID = 5807185929922640794L;

	private Integer id;
	private String name;
	private String clientId;
	private String clientToken;
	private String description;
	private boolean adminAccessFlag;
	private Integer reqCount;
	private Integer reqLimit;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.clientId;
	}

	@Override
	public String getPassword() {
		return this.clientToken;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
