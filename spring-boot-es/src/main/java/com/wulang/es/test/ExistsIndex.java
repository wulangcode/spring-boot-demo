package com.wulang.es.test;

import com.wulang.es.utils.Constant;
import com.wulang.es.utils.EsClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.Test;

import java.io.IOException;

/**
 * ES检查索引是否存在
 *
 * @author wulang
 * @create 2020/9/19/10:59
 */
public class ExistsIndex {
    @Test
    public static void main(String[] args) throws IOException {

        //  1.准备request 对象
        GetIndexRequest request = new GetIndexRequest(Constant.index);

        // 2.通过client 去 操作
        boolean exists = EsClient.getClient().indices().exists(request, RequestOptions.DEFAULT);
        // 3输出结果
        System.out.println(exists);
    }
}
