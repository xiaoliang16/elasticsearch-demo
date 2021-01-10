package com.elasticsearch.demo.query.impl;

import com.elasticsearch.demo.query.SortQuery;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("sortQuery")
public class SortQueryImpl implements SortQuery {
   private static final Logger log = LoggerFactory.getLogger(SortQueryImpl.class);
   @Autowired
   private RestHighLevelClient restHighLevelClient;


    @Override
    public void queryMatch(String indexName, String typeName, String field, String keyWord) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(field, keyWord));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:" + hits.totalHits);
        SearchHit[] h = hits.getHits();
        for (SearchHit hit : h) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    @Override
    public void sortQuery(String indexName, String typeName, String field, String keyWord, String sort, SortOrder sortOrder) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(field, keyWord));
        searchSourceBuilder.sort(sort, sortOrder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:" + hits.totalHits);
        SearchHit[] h = hits.getHits();
        for (SearchHit hit : h) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    @Override
    public void multSortQuery(String indexName, String typeName, String field, String keyWord, String sort1, String sort2, SortOrder sortOrder) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(field, keyWord));
        searchSourceBuilder.sort(sort1, sortOrder);
        searchSourceBuilder.sort(sort2, sortOrder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:" + hits.totalHits);
        SearchHit[] h = hits.getHits();
        for (SearchHit hit : h) {
            System.out.println(hit.getSourceAsMap());
        }
    }
}
