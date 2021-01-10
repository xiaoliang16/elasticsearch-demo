package com.elasticsearch.demo.response;

import org.elasticsearch.search.SearchHits;

import java.util.List;
import java.util.Map;

public class DocumentResponseMsg extends ResponseMsg{

    private SearchHits searchHits;

    private long count;

    private List<Map<String, Object>> documents;

    public List<Map<String, Object>> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Map<String, Object>> documents) {
        this.documents = documents;
    }

    public DocumentResponseMsg(int statusCode, String message) {
        super(statusCode, message);
    }

    public SearchHits getSearchHits() {
        return searchHits;
    }

    public void setSearchHits(SearchHits searchHits) {
        this.searchHits = searchHits;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }


}
