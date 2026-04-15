package com.backend_torch.API.integration.and.Data.Processing.helper;

import com.backend_torch.API.integration.and.Data.Processing.exceptions.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class HttpShooter {

    private final WebClient.Builder webClientBuilder;

    /**
     * Generic GET request
     */
    public <T> ResponseEntity<T> getRequest(String uri, Class<T> responseType) {

        T body = webClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(errorBody -> new ApiException(
                                        "Error",  clientResponse.statusCode() + " -> " + errorBody
                                ))
                )
                .bodyToMono(responseType)
                .block();

        if (body == null) {
            throw new ApiException("error", "Empty response from external service");
        }

        return ResponseEntity.ok(body);
    }
}