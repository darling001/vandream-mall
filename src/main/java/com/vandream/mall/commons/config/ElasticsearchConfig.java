package com.vandream.mall.commons.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author wangchengli
 * @version 1.0
 * @date 2018-01-12
 */
@Component
public class ElasticsearchConfig implements DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);
    private TransportClient transportClient;

    @Value("${elasticsearch.cluster.name}")
    private String clusterName;
    @Value("${elasticsearch.esHosts}")
    private String esHosts;
    @Value("${elasticsearch.pool}")
    private String poolSize;
    @Value("${elasticsearch.port}")
    private int port;

    @Bean
    public TransportClient  getTransportClient() throws Exception{
        try {
            if (transportClient == null) {
                Settings settings = Settings.settingsBuilder()
                        //设置集群名称
                        .put("cluster.name", clusterName)
                        //自动嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中
                        .put("client.transport.sniff", false)
                        //增加线程池个数，暂时设为5
                        .put("thread_pool.search.size", Integer.parseInt(poolSize))
                        .build();
                transportClient = TransportClient.builder().settings(settings).build();
                String[] hosts = esHosts.split(",");
                for (String host : hosts) {
                        transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));

                    }
                logger.info("elasticsearch transportClient 连接成功");
            }
        } catch (Exception e) {
            logger.error("elasticsearch transportClient 连接失败",e);
            throw e;
        }
        return transportClient;
    }

    @Override
    public void destroy() throws Exception {
        if (transportClient != null) {
            transportClient.close();
        }
    }
}

