package com.elasticsearch.demo.document.impl;

import com.elasticsearch.demo.document.IndexService;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class IndexServiceImpl implements IndexService {
    private final static Logger log = LoggerFactory.getLogger(IndexServiceImpl.class);
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     *  创建索引
     * @param index       索引名称
     * @param type        索引类型
     * @param request     创建索引的REQUEST
     * @throws IOException
     */
      @Override
      public void createIndex(String index, String type, CreateIndexRequest request) throws IOException {
          log.info("source:" + request.toString());
          CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
          System.out.println(response.toString());
          log.info("索引:" + response.isAcknowledged());
      }


    /**
     * 删除索引
     * @param index 索引名称
     * @throws IOException
     */
    @Override
    public boolean deleteIndex(String index) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(index);
        if (restHighLevelClient.indices().exists(getIndexRequest)) {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
            log.info("source:" + deleteIndexRequest.toString());
            AcknowledgedResponse response = restHighLevelClient.indices().delete(deleteIndexRequest);
            if (response.isAcknowledged()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 判断索引是否存在
     * @param index
     * @return
     * @throws IOException
     */
    public boolean existsIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(index);
        log.info("source:" + request.toString());
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        log.debug("existsIndex：" + exists);
        return exists;
    }

    @Override
    public boolean createIndexWithAlias(String indexName, String aliasName) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        if (!StringUtils.isEmpty(aliasName)) {
            request.alias(new Alias(aliasName));
        }
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        return createIndexResponse.isAcknowledged();
    }

    @Override
    public boolean addAlias(String indexName, String aliasName) throws IOException {
        IndicesAliasesRequest aliasesRequest = new IndicesAliasesRequest();
        IndicesAliasesRequest.AliasActions aliasActions =
                new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
                     .index(indexName)
                     .alias(aliasName);
        aliasesRequest.addAliasAction(aliasActions);
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().updateAliases(aliasesRequest, RequestOptions.DEFAULT);
        return acknowledgedResponse.isAcknowledged();
    }

    @Override
    public void reindex(String oldIndexName, String newIndexName) throws IOException {
        ReindexRequest request = new ReindexRequest();
        request.setSourceIndices(oldIndexName);
        request.setDestIndex(newIndexName);
        request.setSlices(2);
        request.setScroll(TimeValue.timeValueMinutes(0L));
        request.setSourceBatchSize(1000);
        request.setDestOpType("create");
        request.setConflicts("proceed");

        restHighLevelClient.reindex(request, RequestOptions.DEFAULT);
    }

    @Override
    public List<String> getAllIndex() throws IOException {
        List<String> list = new ArrayList<>();
        GetAliasesRequest request = new GetAliasesRequest();
        GetAliasesResponse getAliasesResponse = restHighLevelClient.indices().getAlias(request, RequestOptions.DEFAULT);
        Map<String, Set<AliasMetaData>> map = getAliasesResponse.getAliases();
        Set<String> indices = map.keySet();
        for (String index : indices) {
            list.add(index);
        }
        return list;
    }

    @Override
    public List<String> getAllIndex(String aliasName) throws IOException {
        List<String> list = new ArrayList<>();
        GetAliasesRequest request = new GetAliasesRequest(aliasName);
        GetAliasesResponse getAliasesResponse = restHighLevelClient.indices().getAlias(request, RequestOptions.DEFAULT);
        Map<String, Set<AliasMetaData>> map = getAliasesResponse.getAliases();
        Set<String> indices = map.keySet();
        for (String index : indices) {
            list.add(index);
        }
        return list;
    }


}
