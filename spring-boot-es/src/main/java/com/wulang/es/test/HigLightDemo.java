package com.wulang.es.test;

import com.wulang.es.utils.EsClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * 高亮查询就是用户输入的关键字，以一定特殊样式展示给用户，让用户知道为什么这个结果被检索出来
 * 高亮展示的数据，本身就是文档中的一个field,单独将field以highlight的形式返回给用户
 * ES提供了一个highlight 属性，他和query 同级别。
 * <p>
 * frament_size: 指定高亮数据展示多少个字符回来
 * pre_tags:指定前缀标签<front color="red">
 * post_tags:指定后缀标签 </font>
 *
 * @author wulang
 * @create 2020/9/19/14:03
 */
public class HigLightDemo {

    RestHighLevelClient client = EsClient.getClient();
    String index = "sms-logs-index";
    String type = "sms-logs-type";

    @Test
    public void highLightQuery() throws IOException {
        // 1.创建request
        SearchRequest request = new SearchRequest(index);
        request.types(type);

        // 2.指定查询条件，指定高亮
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("smsContent", "团队"));
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("smsContent", 10)
            .preTags("<font colr='red'>")
            .postTags("</font>");
        builder.highlighter(highlightBuilder);
        request.source(builder);

        // 3.执行
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        //4. 输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getHighlightFields().get("smsContent"));
        }
    }
}
