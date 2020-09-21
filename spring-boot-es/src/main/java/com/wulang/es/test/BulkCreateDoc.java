package com.wulang.es.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wulang.es.pojo.Person;
import com.wulang.es.utils.Constant;
import com.wulang.es.utils.EsClient;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;

import java.util.Date;

/**
 * @author wulang
 * @create 2020/9/19/12:20
 */
public class BulkCreateDoc {
    static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        // 1.准备多个json 对象
        Person p1 = new Person(1, "张三", 23, new Date());
        Person p2 = new Person(2, "里斯", 24, new Date());
        Person p3 = new Person(3, "王武", 24, new Date());

        String json1 = mapper.writeValueAsString(p1);
        String json2 = mapper.writeValueAsString(p2);
        String json3 = mapper.writeValueAsString(p3);

        // 2.创建request

        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new IndexRequest(Constant.index, Constant.type, p1.getId().toString()).source(json1, XContentType.JSON))
            .add(new IndexRequest(Constant.index, Constant.type, p2.getId().toString()).source(json2, XContentType.JSON))
            .add(new IndexRequest(Constant.index, Constant.type, p3.getId().toString()).source(json3, XContentType.JSON));

        // 3.client 执行
        BulkResponse responses = EsClient.getClient().bulk(bulkRequest, RequestOptions.DEFAULT);

        // 4.输出结果
        System.out.println(responses.getItems().toString());
    }
}
