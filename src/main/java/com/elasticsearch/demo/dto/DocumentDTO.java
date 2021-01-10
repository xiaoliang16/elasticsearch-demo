package com.elasticsearch.demo.dto;

import lombok.Data;

@Data
public class DocumentDTO {

    private String index;

    private String type;

    private String id;

    private String fieldName;

    private String fieldValue;
}
