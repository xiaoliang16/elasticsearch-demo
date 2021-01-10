package com.elasticsearch.demo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    private int statusCode;
    private String message;

    public ResponseMsg(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
