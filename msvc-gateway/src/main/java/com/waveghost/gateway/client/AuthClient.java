package com.waveghost.gateway.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.waveghost.gateway.model.ErrorResponse;

import reactor.core.publisher.Mono;

@Component
public class AuthClient {

    private final WebClient webClient;

    public AuthClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://MSVC-AUTH").build();
    }

    public Mono<Void> validateToken(String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/jwt/validate-token")
                        .queryParam("token", token)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> 
                        clientResponse
                        .bodyToMono(new ParameterizedTypeReference<ErrorResponse>() {})
                        .flatMap(errorResponse ->                                
                                Mono.error(new RuntimeException(errorResponse.getMessage()))
                        )
                )
                .bodyToMono(Void.class);
    }

    public Mono<String> getUsername(String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/jwt/get-username")
                        .queryParam("token", token)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> 
                        clientResponse
                        .bodyToMono(new ParameterizedTypeReference<ErrorResponse>() {})
                        .flatMap(errorResponse ->                                
                                Mono.error(new RuntimeException(errorResponse.getMessage()))
                        )
                )
                .bodyToMono(String.class);
    }

    public Mono<List<String>> getAuthorities(String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/jwt/get-authorities")
                        .queryParam("token", token)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> 
                        clientResponse
                        .bodyToMono(new ParameterizedTypeReference<ErrorResponse>() {})
                        .flatMap(errorResponse ->                                
                                Mono.error(new RuntimeException(errorResponse.getMessage()))
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {});
    }
}
