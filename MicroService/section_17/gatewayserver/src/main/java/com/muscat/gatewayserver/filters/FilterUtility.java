package com.muscat.gatewayserver.filters;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class FilterUtility {

  public static final String CORRELATION_ID = "muscathan-correlation-id";

  public String getCorrelationId(HttpHeaders requestHeaders) {
    if (requestHeaders.get(CORRELATION_ID) != null) {
      List<String> requestHeadersList = requestHeaders.get(CORRELATION_ID);
      return requestHeadersList.stream().findFirst().get();
    }else {
      return null;
    }
  }


  public ServerWebExchange setRequestHeaders(ServerWebExchange exchange, String name, String value){
    return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
  }

  public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
    return this.setRequestHeaders(exchange, CORRELATION_ID, correlationId);
  }
}
