package com.mermer.springgateway.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    @Data
    public static class Config{
        //Put the configuration properties
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

    public GlobalFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(GlobalFilter.Config config) {
        // Custom Pre Filter
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if(config.isPreLogger()){
                log.info("Global Filer PreLogger Activated - request id : {}" , request.getId());
            }

            //Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if(config.isPostLogger()){
                    log.info("Global Filer PostLogger Activated - request id : {}" , response.getStatusCode());
                }
            }));
        });
    }
}
