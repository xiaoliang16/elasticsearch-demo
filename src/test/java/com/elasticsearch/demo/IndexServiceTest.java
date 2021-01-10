package com.elasticsearch.demo;

import com.elasticsearch.demo.document.IndexService;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class IndexServiceTest {
    private final static Logger log = LoggerFactory.getLogger(IndexServiceTest.class);
    private String indexName = "user1";
    private String type = "user-type";

    private String aliasName = "user";

    @Autowired
    private IndexService indexService;

    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        buildSetting(request);
        buildIndexMapping(request, type);
        indexService.createIndex(indexName,type,request);
        log.info("完成");
    }

    @Test
    public void testGetIndex() throws IOException {
        log.info("查询所有index");
        List<String> indices = indexService.getAllIndex();
        for (String index : indices) {
            System.out.println(index);
        }
    }

    @Test
    public void testDelIndex() throws IOException {
        indexService.deleteIndex(indexName);
    }

    @Test
    public void addAlias() throws IOException {
        indexService.addAlias(indexName, aliasName);
    }



    //设置分片
    private void buildSetting(CreateIndexRequest request) {
        request.settings(Settings.builder().put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2));
    }


    private void  buildIndexMapping(CreateIndexRequest request, String type) throws IOException {
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

}
