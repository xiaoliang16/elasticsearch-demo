package com.elasticsearch.demo.document;

import java.io.IOException;

public interface MigrationDocumentationService {

    boolean checkClusterHealth() throws IOException;
}
