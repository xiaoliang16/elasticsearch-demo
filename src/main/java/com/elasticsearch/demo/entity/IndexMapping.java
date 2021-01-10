package com.elasticsearch.demo.entity;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;
import java.io.Serializable;

public abstract class IndexMapping implements Serializable {

    public abstract void  buildIndexMapping(CreateIndexRequest request, String type) throws IOException;

    public void buildSetting(CreateIndexRequest request) {
        request.settings(Settings.builder().put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2));
    }

}
