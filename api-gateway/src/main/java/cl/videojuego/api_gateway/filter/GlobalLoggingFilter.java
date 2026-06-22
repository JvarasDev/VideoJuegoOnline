package cl.videojuego.api_gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class GlobalLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(GlobalLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();
        URI requestUrl = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String routeId = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR) != null
                ? exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR).toString()
                : "unknown";

        log.info("→ REQ: method={} path={} routeId={} targetUrl={}",
                method, path, routeId, requestUrl);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long duration = System.currentTimeMillis() - startTime;
            org.springframework.http.HttpStatusCode status = exchange.getResponse().getStatusCode();
            int statusCode = status != null ? status.value() : 500;
            URI resolvedUrl = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);

            log.info("← RES: method={} path={} status={} duration={}ms resolvedUrl={}",
                    method, path, statusCode, duration, resolvedUrl);
        }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
