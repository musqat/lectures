package com.muscat.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

  private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

  @Autowired
  FilterUtility filterUtility;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
    if (isCorrelationIdPresent(requestHeaders)) {
      logger.debug("muscathan-correlation-id found int RequestTraceFilter : {} ",
          filterUtility.getCorrelationId(requestHeaders));
    }else {
      String correlationId = generateCorrelationId();
      exchange = filterUtility.setCorrelationId(exchange, correlationId);
      logger.debug("muscathan-correlation-id added in RequestTraceFilter : {} ",
          correlationId);
    }


    return chain.filter(exchange);
  }

  private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
    if (filterUtility.getCorrelationId(requestHeaders) != null) {
      return true;
    }else {
      return false;
    }
  }

  private String generateCorrelationId(){
    return java.util.UUID.randomUUID().toString();
  }

}
