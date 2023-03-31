package com.example.solrfilmsdemo;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SolrConfig {
    @Bean
    SolrClient solrClient(
            @Value("${solr.hosts}") List<String> hosts,
            @Value("${solr.collection.default}") String collection) {
        var client = new CloudSolrClient.Builder(hosts).build();
        client.setDefaultCollection(collection);
        return client;
    }
}
