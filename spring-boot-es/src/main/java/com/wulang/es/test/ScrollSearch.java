package com.wulang.es.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wulang.es.utils.EsClient;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.io.IOException;

/**
 * ES 对from +size时又限制的，from +size 之和 不能大于1W,超过后 效率会十分低下
 * 原理：
 * from+size  ES查询数据的方式，
 * 第一步将用户指定的关键词进行分词，
 * 第二部将词汇去分词库中进行检索，得到多个文档id,
 * 第三步去各个分片中拉去数据， 耗时相对较长
 * 第四步根据score 将数据进行排序， 耗时相对较长
 * 第五步根据from 和size 的值 将部分数据舍弃，
 * 第六步，返回结果。
 * <p>
 * scroll +size ES 查询数据的方式
 * 第一步将用户指定的关键词进行分词，
 * 第二部将词汇去分词库中进行检索，得到多个文档id,
 * 第三步，将文档的id放在一个上下文中
 * 第四步，根据指定的size去ES中检索指定个数数据，拿完数据的文档id,会从上下文中移除
 * 第五步，如果需要下一页的数据，直接去ES的上下文中找后续内容。
 * 第六步，循环第四步和第五步
 * scroll 不适合做实时查询。
 *
 * @author wulang
 * @create 2020/9/19/13:23
 */
public class ScrollSearch {
    ObjectMapper mapper = new ObjectMapper();
    RestHighLevelClient client = EsClient.getClient();
    String index = "sms-logs-index";
    String type = "sms-logs-type";

    @Test
    public void scrollSearch() throws IOException {

        // 1.创建request
        SearchRequest searchRequest = new SearchRequest(index);

        //  2.指定scroll信息,过期时间
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));

        //  3.指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.size(4);
        builder.sort("fee", SortOrder.DESC);
        searchRequest.source(builder);
        // 4.获取返回结果scrollId,获取source
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = response.getScrollId();
        System.out.println("-------------首页数据---------------------");
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }

        while (true) {
            // 5.创建scroll request

            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);

            // 6.指定scroll 有效时间
            scrollRequest.scroll(TimeValue.timeValueMinutes(1L));

            // 7.执行查询，返回查询结果
            SearchResponse scroll = client.scroll(scrollRequest, RequestOptions.DEFAULT);

            // 8.判断是否查询到数据，查询到输出
            SearchHit[] searchHits = scroll.getHits().getHits();
            if (searchHits != null && searchHits.length > 0) {
                System.out.println("-------------下一页数据---------------------");
                for (SearchHit hit : searchHits) {
                    System.out.println(hit.getSourceAsMap());
                }
            } else {
                //  9.没有数据，结束
                System.out.println("-------------结束---------------------");
                break;
            }
        }

        // 10.创建 clearScrollRequest
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();

        // 11.指定scrollId
        clearScrollRequest.addScrollId(scrollId);

        //12.删除scroll
        ClearScrollResponse clearScrollResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);

        // 13.输出结果
        System.out.println("删除scroll:" + clearScrollResponse.isSucceeded());

    }
}
