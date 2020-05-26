package com.igeek.tools.api;

public interface OkHttpConfig {

    /**
     * 连接超时时间
     */
    long CONNECT_TIMEOUT = 10;
    /**
     * 读取数据超时时间
     */
    long READ_TIMEOUT = 10;
    /**
     * 响应超时时间
     */
    long WRITE_TIMEOUT = 10;
    /**
     * 整个请求的超时时间
     */
    long CALL_TIMEOUT = 30;
}
