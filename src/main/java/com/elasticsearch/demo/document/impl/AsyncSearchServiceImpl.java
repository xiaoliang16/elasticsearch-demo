package com.elasticsearch.demo.document.impl;

import com.elasticsearch.demo.document.AsyncSearchService;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.LatchedActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@Service
public class AsyncSearchServiceImpl implements AsyncSearchService {
    private final static Logger log = LoggerFactory.getLogger(AsyncSearchServiceImpl.class);
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 异步查询
     * @param index
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void asyncSearch(String index) throws IOException, InterruptedException {
      SearchRequest searchRequest = new SearchRequest(index);
      SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
      searchSourceBuilder.query(QueryBuilders.matchAllQuery());
      searchRequest.source(searchSourceBuilder);
      ActionListener<SearchResponse> listener = new ActionListener<SearchResponse>() {
          @Override
          public void onResponse(SearchResponse searchResponse) {
              RestStatus status = searchResponse.status();
              SearchHits hits = searchResponse.getHits();
              long totalHits = hits.getTotalHits();
              float maxScore = hits.getMaxScore();
              SearchHit[] searchHits = hits.getHits();
              for (SearchHit hit : searchHits) {
                  String index = hit.getIndex();
                  String type = hit.getType();
                  String id = hit.getId();
                  float score = hit.getScore();
                  String sourceAsString = hit.getSourceAsString();
                  Map<String, Object> sourceAsMap = hit.getSourceAsMap();
              }
          }

          @Override
          public void onFailure(Exception e) {
             log.error(e.getMessage(), e);
          }
      };

      final CountDownLatch latch = new CountDownLatch(1);
      listener = new LatchedActionListener<>(listener, latch);

      restHighLevelClient.searchAsync(searchRequest, RequestOptions.DEFAULT, listener);

      latch.await(60L, TimeUnit.SECONDS);
    }
}
