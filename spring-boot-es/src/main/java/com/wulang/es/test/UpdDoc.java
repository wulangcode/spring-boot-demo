package com.wulang.es.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wulang.es.utils.EsClient;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wulang
 * @create 2020/9/19/11:16
 */
public class UpdDoc {
    static ObjectMapper mapper = new ObjectMapper();
    static RestHighLevelClient client = EsClient.getClient();
    static String index = "person";
    static String type = "man";

    public static void main(String[] args) throws IOException {
        // 1.创建要跟新的Map
        Map<String, Object> doc = new HashMap<>();
        doc.put("name", "张三三");

        // 2.创建request, 将doc 封装进去
        UpdateRequest request = new UpdateRequest(index, type, "1");
        request.doc(doc);

        // 3. client 去操作 request
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        // 4.输出 更新结果
        System.out.println(response.getResult());
    }
}
