package com.ssafy.apigatewayservice.filter

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class LoggingFilter : AbstractGatewayFilterFactory<LoggingFilter.Config>(Config::class.java) {

    private val logger = KotlinLogging.logger{ }
    override fun apply(config: Config): GatewayFilter {
        //        return ((exchange, chain) -> {
        //            ServerHttpRequest request = exchange.getRequest();
        //            ServerHttpResponse response = exchange.getResponse();
        //
        //            log.info("Logging Filter baseMessage: {}", config.getBaseMessage());
        //            if (config.isPreLogger()) {
        //                log.info("Logging Filter Start: request id -> {}", request.getId());
        //            }
        //            return chain.filter(exchange).then(Mono.fromRunnable(()->{
        //                if (config.isPostLogger()) {
        //                    log.info("Logging Filter End: response code -> {}", response.getStatusCode());
        //                }
        //            }));
        //        });
        return OrderedGatewayFilter({ exchange: ServerWebExchange, chain: GatewayFilterChain ->
            val request = exchange.request
            val response = exchange.response
            logger.info { "Logging Filter baseMessage: ${config.baseMessage}" }

            if (config.preLogger) {
                logger.info { "Logging PRE Filter: request id ->  ${request.id}" }
            }

            chain.filter(exchange).then(
                    Mono.fromRunnable {
                        if (config.postLogger) {
                            logger.info { "Logging POST Filter: response code -> ${response.statusCode}" }
                        }
                }
            )
        }, Ordered.LOWEST_PRECEDENCE)
    }


    data class Config (
            var baseMessage: String = "",
            var preLogger: Boolean = false,
            var postLogger: Boolean = false
    )
}