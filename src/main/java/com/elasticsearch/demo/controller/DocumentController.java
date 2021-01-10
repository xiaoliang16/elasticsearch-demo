package com.elasticsearch.demo.controller;

import com.alibaba.fastjson.JSON;
import com.elasticsearch.demo.document.DocService;
import com.elasticsearch.demo.document.IndexService;
import com.elasticsearch.demo.dto.UserDTO;
import com.elasticsearch.demo.query.BaseQuery;
import com.elasticsearch.demo.response.DocumentResponseMsg;
import com.elasticsearch.demo.response.ResponseMsg;
import com.elasticsearch.demo.response.StatusCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/document")
@Api(value = "document接口", description = "document接口", tags = {"DocumentController"})
public class DocumentController {

     @Autowired
     private BaseQuery baseQuery;

     @Autowired
     private DocService docService;

     @Autowired
     private IndexService indexService;

     @ApiOperation("添加文档")
     @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
     public void add(@RequestBody UserDTO dto) {
         try {
              String documentId = dto.getId();
              if (StringUtils.isEmpty(documentId)) {
                  docService.add(dto.getIndex(), dto.getType(), JSON.toJSONString(dto.getUser()));
              } else {
                  docService.add(dto.getIndex(), dto.getType(), JSON.toJSONString(dto.getUser()), documentId);
              }
         } catch (Exception e) {
             log.error(e.getMessage(), e);
         }
     }

    @ApiOperation("删除文档")
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@RequestBody UserDTO dto) {
        try {
            docService.deleteDocWithId(dto.getIndex(), dto.getType(), dto.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @ApiOperation("查询文档")
    @GetMapping(value = "/getAllDocument", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseMsg detail(@RequestParam String index, @RequestParam String type) {
        DocumentResponseMsg responseMsg = new DocumentResponseMsg(StatusCodeEnum.INVALID_REQUEST.getIndex(), "查询失败");
        try {
            SearchHits hits = baseQuery.queryAll(index, type);
            long count = hits.getTotalHits();
            responseMsg.setStatusCode(200);
            responseMsg.setMessage("查询成功");
            responseMsg.setSearchHits(hits);
            responseMsg.setCount(count);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return responseMsg;
    }

    @ApiOperation("条件查询文档")
    @GetMapping(value = "/termQuery", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseMsg termQuery(@RequestParam String index, @RequestParam String type, @RequestParam String fieldName, @RequestParam String fieldValue) {
        DocumentResponseMsg responseMsg = new DocumentResponseMsg(StatusCodeEnum.INVALID_REQUEST.getIndex(), "查询失败");
        try {
            List<Map<String, Object>> list = baseQuery.termQuery(index, type, fieldName, fieldValue);
            responseMsg.setDocuments(list);
            long count = list.size();
            responseMsg.setStatusCode(200);
            responseMsg.setMessage("查询成功");
            responseMsg.setCount(count);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return responseMsg;
    }

    @ApiOperation("根据id查询文档")
    @GetMapping(value = "/idsQuery", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseMsg idsQuery(@RequestParam String index, @RequestParam String type, @RequestParam String ids) {
        DocumentResponseMsg responseMsg = new DocumentResponseMsg(StatusCodeEnum.INVALID_REQUEST.getIndex(), "查询失败");
        try {
            SearchHits hits = baseQuery.idsQuery(index, type, ids);
            responseMsg.setSearchHits(hits);
            long count = hits.getTotalHits();
            responseMsg.setStatusCode(200);
            responseMsg.setMessage("查询成功");
            responseMsg.setCount(count);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return responseMsg;
    }

    @ApiOperation("根据条件删除文档")
    @DeleteMapping(value = "/deleteByQuery", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DocumentResponseMsg deleteByQuery(@RequestParam String index, @RequestParam String type, @RequestParam String fieldName, @RequestParam String fieldValue) {
        DocumentResponseMsg responseMsg = new DocumentResponseMsg(StatusCodeEnum.INVALID_REQUEST.getIndex(), "删除失败");
        try {
            long count = docService.deleteByQuery(index, type, fieldName, fieldValue);
            responseMsg.setCount(count);
            responseMsg.setStatusCode(StatusCodeEnum.SUCCESS.getIndex());
            responseMsg.setMessage("删除的文档数是: " + count);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return responseMsg;
    }

}
