package com.animesh.ecommerce.api_gateway.Filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalLoginFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("logging from Global Pre: {}",exchange.getRequest().getURI());
        return chain.filter(exchange).then(Mono.fromRunnable(
                () -> {
                   log.info("logging form Global Post : {}",exchange.getResponse().getStatusCode());
                }
        ));
    }

    @Override
    public int getOrder() {
        return 5;
    }
}
