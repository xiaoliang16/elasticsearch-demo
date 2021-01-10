package com.elasticsearch.demo.controller;

import com.elasticsearch.demo.document.DocService;
import com.elasticsearch.demo.document.IndexService;
import com.elasticsearch.demo.query.BaseQuery;
import com.elasticsearch.demo.response.IndexResponseMsg;
import com.elasticsearch.demo.response.ResponseMsg;
import com.elasticsearch.demo.response.StatusCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/index接口")
@Api(value = "index接口", description = "index接口", tags = {"IndexController"})
public class IndexController {

    @Autowired
    private BaseQuery baseQuery;

    @Autowired
    private DocService docService;

    @Autowired
    private IndexService indexService;

    @ApiOperation("查询所有索引")
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseMsg getIndices() {
        IndexResponseMsg responseMsg = new IndexResponseMsg(StatusCodeEnum.INVALID_REQUEST.getIndex(), "查询失败");
        try {
            List<String> indices = indexService.getAllIndex();
            responseMsg.setIndices(indices);
            responseMsg.setMessage("success");
            responseMsg.setStatusCode(StatusCodeEnum.SUCCESS.getIndex());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return responseMsg;
    }

    @ApiOperation("删除索引")
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseMsg deleteIndex(@RequestParam String index) {
        IndexResponseMsg responseMsg = new IndexResponseMsg(StatusCodeEnum.INVALID_REQUEST.getIndex(), "删除失败");
        try {
            boolean result = indexService.deleteIndex(index);
            if (result) {
                responseMsg.setStatusCode(StatusCodeEnum.SUCCESS.getIndex());
                responseMsg.setMessage(index + "删除成功");
            } else {
                responseMsg.setStatusCode(StatusCodeEnum.SUCCESS.getIndex());
                responseMsg.setMessage("删除失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return responseMsg;
    }

    @ApiOperation("根据别名查询所有索引")
    @GetMapping(value = "/getIndices", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseMsg getIndices(@RequestParam String aliasName) {
        IndexResponseMsg responseMsg = new IndexResponseMsg(StatusCodeEnum.INVALID_REQUEST.getIndex(), "查询失败");
        try {
            List<String> indices = indexService.getAllIndex(aliasName);
            responseMsg.setIndices(indices);
            responseMsg.setMessage("success");
            responseMsg.setStatusCode(StatusCodeEnum.SUCCESS.getIndex());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return responseMsg;
    }

}
