package com.wulang.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.sniff.ElasticsearchNodesSniffer;
import org.elasticsearch.client.sniff.NodesSniffer;
import org.elasticsearch.client.sniff.SniffOnFailureListener;
import org.elasticsearch.client.sniff.Sniffer;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Sniffer：嗅探器，从运行的elasticsearch集群自动发现节点并将它们设置为现有的 RestClient实例
 *
 * @author wulang
 * @create 2020/10/3/21:04
 */
public class SnifferDemo {
    @Test
    public void sniffer() throws IOException {
        RestHighLevelClient client;

        HttpHost[] httpHosts = new HttpHost[3];
        httpHosts[0] = new HttpHost("192.168.1.1", 9200, "http");
        httpHosts[1] = new HttpHost("192.168.1.2", 9200, "http");
        httpHosts[2] = new HttpHost("192.168.1.3", 9200, "http");

        // create RestClientBuilder with httpHosts
        RestClientBuilder restClientBuilder = RestClient.builder(httpHosts);

        // set a SniffOnFailureListener to restClientBuilder
        SniffOnFailureListener sniffOnFailureListener = new SniffOnFailureListener();
        restClientBuilder.setFailureListener(sniffOnFailureListener);

        // create RestHighLevelClient
        client = new RestHighLevelClient(restClientBuilder);

        // configure the node sniffer(optional)
        NodesSniffer nodesSniffer = new ElasticsearchNodesSniffer(
            client.getLowLevelClient(),
            // 自定义sniff节点超时时间，超过的就不等了，默认1秒，这里设为5秒
            TimeUnit.SECONDS.toMillis(5),
            // 协议，默认HTTP
            ElasticsearchNodesSniffer.Scheme.HTTP);

        // build the sniffer
        Sniffer sniffer = Sniffer
            .builder(client.getLowLevelClient())
            .setNodesSniffer(nodesSniffer)
            // 默认每5分钟获取一次集群中的data node，这里改为1分钟
            .setSniffIntervalMillis(60000)
            // sniff failure后额外触发sniff行为的延迟时间，默认1分钟，这里改为30秒
            .setSniffAfterFailureDelayMillis(30000)
            .build();

        // connect the sniffer and the listener
        sniffOnFailureListener.setSniffer(sniffer);

        // finally, remember to close sniffer and client.
        // must close sniffer right before the client
        sniffer.close();
        client.close();

    }
}
