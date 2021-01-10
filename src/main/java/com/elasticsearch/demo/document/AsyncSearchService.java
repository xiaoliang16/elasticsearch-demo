package com.elasticsearch.demo.document;

import java.io.IOException;

public interface AsyncSearchService {

    void asyncSearch(String index) throws IOException, InterruptedException;
}
