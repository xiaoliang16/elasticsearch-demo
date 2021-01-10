package com.elasticsearch.demo.document.impl;

import com.elasticsearch.demo.document.MigrationDocumentationService;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class MigrationDocumentationServiceImpl implements MigrationDocumentationService {
    private final static Logger log = LoggerFactory.getLogger(MigrationDocumentationServiceImpl.class);
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public boolean checkClusterHealth() throws IOException {
        Request request = new Request("GET", "/_cluster/health");
        request.addParameter("wait_for_status", "green");
        Response response = restHighLevelClient.getLowLevelClient().performRequest(request);

        ClusterHealthStatus healthStatus;
        try (InputStream is = response.getEntity().getContent()) {
            Map<String, Object> map = XContentHelper.convertToMap(XContentType.JSON.xContent(), is, true);
            healthStatus = ClusterHealthStatus.fromString((String)map.get("status"));
        }

        if (healthStatus != ClusterHealthStatus.GREEN) {
            log.error("cluster health status is not green");
            return false;
        }

        return true;
    }
}
