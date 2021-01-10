package com.elasticsearch.demo.response;

public enum  StatusCodeEnum {

    SUCCESS(200, "请求成功接收并处理"),
    MULTIPLE_CHOICES(300, "多种选择。请求的资源可包括多个位置，相应可返回一个资源特征与地址的列表用于用户终端选择"),
    INVALID_REQUEST(400, "请求有错误（请求语法错误、body 数据格式有误、或者body缺少必须的字段等），导致服务端无法处理"),
    UNAUTHORIZED(401, "请求的资源需要认证，客户端没有提供认证信息或者认证信息不正确（令牌、用户名、密码错误）"),
    GATEWAY_TIME_OUT(504, "网关超时");

    private int index;
    private String message;

    private StatusCodeEnum(int index, String message) {
        this.setIndex(index);
        this.setMessage(message);
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
