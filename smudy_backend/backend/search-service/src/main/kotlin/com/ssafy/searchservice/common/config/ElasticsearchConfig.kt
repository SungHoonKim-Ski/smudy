package com.ssafy.searchservice.common.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*


@Configuration
@EnableElasticsearchRepositories
class ElasticsearchConfig(
    @Value("\${spring.elasticsearch.host}")
    private var host: String,

    @Value("\${spring.elasticsearch.username}")
    private var username: String,

    @Value("\${spring.elasticsearch.password}")
    private var password: String,
): ElasticsearchConfiguration() {

    override fun clientConfiguration(): ClientConfiguration {

        return ClientConfiguration.builder()
            .connectedTo(host)
            .withBasicAuth(username, password)
            .build();
    }
}