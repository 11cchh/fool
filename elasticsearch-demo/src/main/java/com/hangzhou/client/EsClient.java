package com.hangzhou.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author Faye
 * @Date 2023/1/2 16:53
 */
@Component
public class EsClient {
    @Value("${elasticsearch.rest.hosts}")
    private String hosts;

    @Bean
    public RestHighLevelClient initSimpleClient() {
        // 根据配置文件配置 HttpHost 数组
        HttpHost[] httpHosts = Arrays.stream(hosts.split(",")).map(host -> {
                    String[] hostParts = host.split(":");
                    String hostName = hostParts[0];
                    int port = Integer.parseInt(hostParts[1]);
                    return new HttpHost(hostName, port, HttpHost.DEFAULT_SCHEME_NAME);
                }
        ).filter(Objects::nonNull).toArray(HttpHost[]::new);
        // 构建客户端
        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }
}
