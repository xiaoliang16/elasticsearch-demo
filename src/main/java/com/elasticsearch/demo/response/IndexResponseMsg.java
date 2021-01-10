package com.elasticsearch.demo.response;

import java.util.List;

public class IndexResponseMsg extends ResponseMsg{

    private List<String> indices;

    public IndexResponseMsg(int statusCode, String message) {
        super(statusCode, message);
    }

    public List<String> getIndices() {
        return indices;
    }

    public void setIndices(List<String> indices) {
        this.indices = indices;
    }
}
