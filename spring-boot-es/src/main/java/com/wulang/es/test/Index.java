package com.wulang.es.test;

import com.wulang.es.utils.Constant;
import com.wulang.es.utils.EsClient;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.Test;

import java.io.IOException;

/**
 * ES创建索引
 *
 * @author wulang
 * @create 2020/9/19/10:12
 */
public class Index {

    @Test
    public static void main(String[] args) throws IOException {
        // 1. 准备关于索引的settings
        Settings.Builder settings = Settings.builder()
            // 分片数
            .put("number_of_shards", 2)
            // 备份数
            .put("number_of_replicas", 1);
        // 2. 准备关于索引的结构mappings
        XContentBuilder builder = JsonXContent.contentBuilder()
            .startObject()
            .startObject("properties")
            .startObject("name")
            .field("type", "text")
            .endObject()
            .startObject("age")
            .field("type", "integer")
            .endObject()
            .startObject("birthday")
            .field("type", "date")
            .field("format", "yyyy-MM-dd")
            .endObject()
            .endObject()
            .endObject();
        // 3.将settings和mappings 封装到到一个Request对象中
        CreateIndexRequest request = new CreateIndexRequest(Constant.index)
            .settings(settings)
            .mapping(Constant.type,builder);
        // 4.使用client 去连接ES
        CreateIndexResponse response = EsClient.getClient()
            .indices()
            .create(request, RequestOptions.DEFAULT);
        System.out.println("创建索引返回信息为：" + response);
    }
}
