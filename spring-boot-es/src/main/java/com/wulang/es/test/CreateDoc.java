package com.wulang.es.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wulang.es.pojo.Person;
import com.wulang.es.utils.EsClient;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import java.util.Date;

/**
 * @author wulang
 * @create 2020/9/19/11:05
 */
public class CreateDoc {
    static ObjectMapper mapper = new ObjectMapper();
    static RestHighLevelClient client = EsClient.getClient();
    static String index = "person";
    static String type = "man";

    @Test
    public static void main(String[] args) throws Exception {
        //  1.准备一个json数据
        Person person = new Person(1, "张三", 33, new Date());
        String json = mapper.writeValueAsString(person);
        //  2.创建一个request对象(手动指定的方式创建)
        IndexRequest request = new IndexRequest(index, type, person.getId().toString());
        request.source(json, XContentType.JSON);
        // 3.使用client 操作request对象生成doc
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        // 4.输出返回结果
        System.out.println(response.getResult().toString());
    }
}
