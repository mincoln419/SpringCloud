package com.mermer.springapigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LogginFilter extends AbstractGatewayFilterFactory<LogginFilter.Config> {

	public LogginFilter() {
		super(Config.class);
	}

	@Data
	public static class Config {
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}

	@Override
	public GatewayFilter apply(Config config) {

		/*
		 * return (exchange, chain) ->{ ServerHttpRequest request =
		 * exchange.getRequest(); ServerHttpResponse response = exchange.getResponse();
		 * 
		 * log.info("Global filter baseMessage: {}", config.getBaseMessage());
		 * 
		 * if(config.isPreLogger()) { log.info("Global Filter Start: request id -> {}",
		 * request.getId()); }
		 * 
		 * //Custom Post Filter return chain.filter(exchange).then(Mono.fromRunnable(()
		 * ->{ if(config.isPostLogger()) {
		 * log.info("Global Filter End: responsd code -> {}", response.getStatusCode());
		 * } })); };
		 */
		GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {

			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

			log.info("Logging filter baseMessage: {}", config.getBaseMessage());

			if (config.isPreLogger()) {
				log.info("Logging PRE Filter : request id -> {}", request.getId());
			}

			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				if (config.isPostLogger()) {
					log.info("Logging POST Filter : responsd code -> {}", response.getStatusCode());
				}
			}));
			//실행순서!
		}, Ordered.LOWEST_PRECEDENCE);

		return filter;
	}

}
