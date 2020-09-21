package com.wulang.es.test;

import com.wulang.es.utils.Constant;
import com.wulang.es.utils.EsClient;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;

import java.io.IOException;

/**
 * @author wulang
 * @create 2020/9/19/11:02
 */
public class DelIndex {
    public static void main(String[] args) throws IOException {
        // 1.获取request
        DeleteIndexRequest request = new DeleteIndexRequest(Constant.index);
        //  2.使用client 操作request
        AcknowledgedResponse delete = EsClient.getClient().indices().delete(request, RequestOptions.DEFAULT);
        //  3.输出结果
        System.out.println(delete.isAcknowledged());
    }
}
