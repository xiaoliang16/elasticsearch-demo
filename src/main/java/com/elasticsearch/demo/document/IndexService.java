package com.elasticsearch.demo.document;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;

import java.io.IOException;
import java.util.List;

public interface IndexService {
    public void createIndex(String index, String type, CreateIndexRequest request) throws IOException;

    public boolean deleteIndex(String index) throws IOException;

    public boolean existsIndex(String index) throws IOException;

    public boolean createIndexWithAlias(String indexName, String aliasName) throws IOException;

    public boolean addAlias(String indexName, String aliasName) throws IOException;

    public void reindex(String oldIndexName, String newIndexName) throws IOException;

    public List<String> getAllIndex() throws IOException;

    public List<String> getAllIndex(String aliasName) throws IOException;

}

