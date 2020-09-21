package com.wulang.es.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wulang.es.utils.EsClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.junit.Test;

import java.io.IOException;

/**
 * 根据term,match 等查询方式去删除大量索引
 * PS:如果你要删除的内容，时index下的大部分数据，推荐创建一个新的index,然后把保留的文档内容，添加到全新的索引
 *
 * @author wulang
 * @create 2020/9/19/13:40
 */
public class DeleteByQueryDemo {
    ObjectMapper mapper = new ObjectMapper();
    RestHighLevelClient client = EsClient.getClient();
    String index = "sms-logs-index";
    String type = "sms-logs-type";

    @Test
    public void deleteByQuery() throws IOException {
        // 1.创建DeleteByQueryRequest
        DeleteByQueryRequest request = new DeleteByQueryRequest(index);

        // 2.指定条件
        request.setQuery(QueryBuilders.rangeQuery("fee").lt(20));

        // 3.执行
        BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);

        // 4.输出返回结果
        System.out.println(response.toString());
    }
}
