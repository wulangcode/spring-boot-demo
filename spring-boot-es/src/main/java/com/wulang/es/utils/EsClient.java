package com.wulang.es.utils;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author wulang
 * @create 2020/9/19/9:44
 */
public class EsClient {
    public static RestHighLevelClient getClient() {
        //  创建 HttpHost
        HttpHost httpHost = new HttpHost("101.71.130.14", 19200);

        // 创建 RestClientBuilder
        RestClientBuilder builder = RestClient.builder(httpHost);

        // 创建 RestHighLevelClient
        RestHighLevelClient client = new RestHighLevelClient(builder);

        return client;
    }
}
