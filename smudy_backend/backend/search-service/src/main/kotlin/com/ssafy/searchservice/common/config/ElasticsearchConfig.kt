package com.ssafy.searchservice.common.config

import co.elastic.clients.elasticsearch.ElasticsearchClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration


@Configuration
class ElasticsearchConfig(
    @Value("\${spring.elasticsearch.uris}")
    private var esUris: String,

    @Value("\${spring.elasticsearch.username}")
    private var esUsername: String,

    @Value("\${spring.elasticsearch.password}")
    private var esPassword: String
): ElasticsearchConfiguration() {

    override fun clientConfiguration(): ClientConfiguration {
        return ClientConfiguration.builder()
            .connectedTo(esUris)
            .usingSsl()
            .withBasicAuth(esUsername, esPassword)
            .build()
    }
}