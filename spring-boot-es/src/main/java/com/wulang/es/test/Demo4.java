package com.wulang.es.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wulang.es.pojo.SmsLogs;
import com.wulang.es.utils.EsClient;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Demo4 {
    ObjectMapper mapper = new ObjectMapper();
    RestHighLevelClient client =  EsClient.getClient();
    String index = "sms-logs-index";
    String type="sms-logs-type";

    @Test
    public void createIndex() throws  Exception{
        // 1.准备关于索引的setting
        Settings.Builder settings = Settings.builder()
                .put("number_of_shards", 3)
                .put("number_of_replicas", 1);

        // 2.准备关于索引的mapping
        XContentBuilder mappings = JsonXContent.contentBuilder()
                .startObject()
                    .startObject("properties")
                        .startObject("corpName")
                            .field("type", "keyword")
                        .endObject()
                        .startObject("createDate")
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd")
                        .endObject()
                        .startObject("fee")
                            .field("type", "long")
                        .endObject()
                        .startObject("ipAddr")
                            .field("type", "ip")
                        .endObject()
                        .startObject("longCode")
                            .field("type", "keyword")
                        .endObject()
                        .startObject("mobile")
                            .field("type", "keyword")
                        .endObject()
                        .startObject("operatorId")
                            .field("type", "integer")
                        .endObject()
                        .startObject("province")
                            .field("type", "keyword")
                        .endObject()
                        .startObject("replyTotal")
                            .field("type", "integer")
                        .endObject()
                        .startObject("sendDate")
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd")
                        .endObject()
                        .startObject("smsContent")
                            .field("type", "text")
                        .endObject()
                        .startObject("state")
                            .field("type", "integer")
                        .endObject()
                    .endObject()
                .endObject();
        // 3.将settings和mappings 封装到到一个Request对象中
        CreateIndexRequest request = new CreateIndexRequest(index)
                .settings(settings)
                .mapping(type,mappings);
        // 4.使用client 去连接ES
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

        System.out.println("response:"+response.toString());

    }

    @Test
    public void  bulkCreateDoc() throws  Exception{
        // 1.准备多个json 对象
        String longcode = "1008687";
        String mobile ="138340658";
        List<String> companies = new ArrayList<>();
        companies.add("腾讯课堂");
        companies.add("阿里旺旺");
        companies.add("海尔电器");
        companies.add("海尔智家公司");
        companies.add("格力汽车");
        companies.add("苏宁易购");
        List<String> provinces = new ArrayList<>();
        provinces.add("北京");
        provinces.add("重庆");
        provinces.add("上海");
        provinces.add("晋城");

        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 1; i <16 ; i++) {
            Thread.sleep(1000);
            SmsLogs s1 = new SmsLogs();
            s1.setId(i);
            s1.setCreateDate(new Date());
            s1.setSendDate(new Date());
            s1.setLongCode(longcode+i);
            s1.setMobile(mobile+2*i);
            s1.setCorpName(companies.get(i%5));
            s1.setSmsContent(SmsLogs.doc.substring((i-1)*100,i*100));
            s1.setState(i%2);
            s1.setOperatorId(i%3);
            s1.setProvince(provinces.get(i%4));
            s1.setIpAddr("127.0.0."+i);
            s1.setReplyTotal(i*3);
            s1.setFee(i*6+"");
            String json1  = mapper.writeValueAsString(s1);
            bulkRequest.add(new IndexRequest(index,type,s1.getId().toString()).source(json1, XContentType.JSON));
            System.out.println("数据"+i+s1.toString());
        }

        // 3.client 执行
        BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);

        // 4.输出结果
        System.out.println(responses.getItems().toString());
    }
}
