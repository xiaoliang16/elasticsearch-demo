package com.elasticsearch.demo.config;

import com.elasticsearch.demo.document.IndexService;
import com.elasticsearch.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitIndex implements CommandLineRunner {

    @Autowired
    private IndexService indexService;

    @Override
    public void run(String... args) throws Exception {
        initIndex();
    }

    private void initIndex() {
        try {
            log.info("开始创建索引");
            User user = new User();
            String index = "test-user-index";
            String type = "user-type";
            if (!indexService.existsIndex(index)) {
                CreateIndexRequest request = new CreateIndexRequest(index);
                user.buildSetting(request);
                user.buildIndexMapping(request, type);
                indexService.createIndex(index, type, request);
            }
            log.info("创建索引完成");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
