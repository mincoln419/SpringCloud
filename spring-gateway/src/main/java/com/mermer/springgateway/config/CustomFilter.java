package com.mermer.springgateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public static class Config{
        //Put the configuration properties
    }

    public CustomFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(CustomFilter.Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custeom Pre Filter request uri -> {}",request.getId());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Custom Post filter : response code -> {}", response.getStatusCode());

            }));
        });
    }
}
