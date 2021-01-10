package com.elasticsearch.demo.dto;

import lombok.Data;

@Data
public class IndexDTO {

    private String index;

    private String type;

    private String aliasName;

}
