package com.waveghost.gateway.filters;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.waveghost.gateway.client.AuthClient;

import reactor.core.publisher.Mono;
@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private AuthClient authClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String token = extractJwtFromRequest(exchange);

        if (token != null) {

            return authClient.validateToken(token).then(
                authClient.getUsername(token)
                .zipWith(authClient.getAuthorities(token))
                .flatMap((tuple) -> {

                    List<SimpleGrantedAuthority> authorities = tuple.getT2()
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

                    UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                            tuple.getT1(), 
                            null,
                            authorities
                        );

                    return authenticationManager.authenticate(authentication)
                        .flatMap(auth -> 
                            chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
                        )
                        .onErrorResume(e -> {
                            return this.onError(exchange, "Authentication error: " + e.getMessage());
                        });
                })
            )
            .onErrorResume( e -> {
                return this.onError(exchange, e.getMessage());
            });
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message){
        System.out.println(message);
        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);

        DataBufferFactory bufferFactory = response.bufferFactory();
        DataBuffer dataBuffer = bufferFactory.wrap(message.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(dataBuffer));
    }

    // private Mono<Void> onError(ServerWebExchange exchange, String message){
    //     System.out.println(message);
    //     exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    //     return exchange.getResponse().setComplete();
    // }

    private String extractJwtFromRequest(ServerWebExchange exchange) {
        String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

