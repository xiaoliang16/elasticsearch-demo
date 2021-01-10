package com.elasticsearch.demo.entity;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;

import java.io.IOException;
import java.io.Serializable;

public class User extends IndexMapping implements Serializable {

    private String name;

    private String id;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public void buildIndexMapping(CreateIndexRequest request, String type) throws IOException {
        XContentBuilder mappingBuilder = JsonXContent.contentBuilder()
                .startObject()
                .startObject("properties")
                .startObject("id")
                .field("type", "keyword")
                .field("index", "true")
                .endObject()

                .startObject("name")
                .field("type", "keyword")
                .field("index", "true")
                .endObject()

                .startObject("age")
                .field("type", "integer")
                .field("index", "true")
                .endObject()
                .endObject()
                .endObject();
        request.mapping(type, mappingBuilder);
    }

    @Override
    public void buildSetting(CreateIndexRequest request) {
        super.buildSetting(request);
    }


}
