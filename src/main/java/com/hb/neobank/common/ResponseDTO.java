package com.hb.neobank.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hb.neobank.icici.dto.IciciBankStatementTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResponseDTO {

    private int status;

    private String message;

    private String path;

    private String code;

    private Map<String, Object> data = new HashMap<>();

    private List<IciciBankStatementTO> iciciBankStatementList;

    public void putData(String key, Object Value) {
        data.put(key, Value);
    }

    public ResponseDTO() {
        super();
    }

    public ResponseDTO(int status, String code, String message) {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static ResponseDTO responseBuilder(int status, String code,String message, String path, String key, Object data) {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(status);
        response.setPath(path);
        response.setMessage(code);
        response.setCode(code);
        response.putData(key, data);
        return response;
    }
}

