package com.boot_camp.Boot_Camp.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@PropertySource("file:${elasticsearch.config.path}")
public class ElasticsearchConfiguration {
    @Value("${spring.data.elasticsearch.client.cluster}")
    private String EsClusterName;
    @Value("${spring.data.elasticsearch.client.host}")
    private String host;
    @Value("${spring.data.elasticsearch.client.port}")
    private int port;
//    @Value("${elasticsearch.username}")
//    private String userName;
//    @Value("${elasticsearch.password}")
//    private String password;


    @Bean
    RestHighLevelClient restClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("", ""));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost(host, port)).setHttpClientConfigCallback(
                httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient(builder);
    }

}
