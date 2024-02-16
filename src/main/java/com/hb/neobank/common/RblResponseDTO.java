package com.hb.neobank.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RblResponseDTO {
	
	private int status;

	private String message;

	private String path;

	private String code;

	private Map<String, Object> data = new HashMap<>();


	public void putData(String key, Object Value) {
		data.put(key, Value);
	}

	public RblResponseDTO() {
		super();
	}

	public RblResponseDTO(int status, String code, String message) {
		super();
		this.status = status;
		this.code = code;
		this.message = message;
	}

}
