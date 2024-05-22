package com.ssafy.apigatewayservice.filter

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class GlobalFilter : AbstractGatewayFilterFactory<GlobalFilter.Config>(Config::class.java) {

    private val logger = KotlinLogging.logger{ }
    override fun apply(config: Config): GatewayFilter {
        //Custom Pre Filter
        return GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            val request = exchange.request
            val response = exchange.response

            logger.info{ "Global Filter baseMessage : request id -> ${config.baseMessage}" }
            if (config.preLogger) {
                logger.info{ "Global Filter start : request id -> ${request.id}" }
            }
            chain.filter(exchange).then(Mono.fromRunnable {
                if (!config.postLogger) {
                    logger.info{ "Global POST Filter : response id -> ${response.statusCode} "}
                }
            })
        }
    }

    data class Config (
        var baseMessage: String = "",
        var preLogger: Boolean = false,
        var postLogger: Boolean = false // Put the configuration properties
    )
}