package com.wulang.es.test;

import com.wulang.es.utils.EsClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author wulang
 * @create 2020/9/19/9:47
 */
public class Demo1 {
    public static void main(String[] args) {
        RestHighLevelClient client = EsClient.getClient();
        System.out.println("ok");
    }
}
