package com.bootcamp.pocketoperations.webclient;

import com.bootcamp.pocketoperations.webclient.dto.PocketbookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class PocketbookWebClient {

    @Value("${base.url.pocketbook}")
    private String baseUrl;

    @Autowired
    private WebClient.Builder webClient;

    public Mono<PocketbookDto> findByCellphone(String cellphone) {
        return webClient.baseUrl(baseUrl).build().get()
                .uri("/pocketbook/".concat(cellphone))
                .retrieve()
                .bodyToMono(PocketbookDto.class)
                .onErrorResume(error -> {
                    WebClientResponseException response = (WebClientResponseException) error;
                    if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new Exception("Pocket not found"));
                    }
                    return Mono.error(error);
                });
    }

    public Mono<PocketbookDto> update(PocketbookDto pocketbookDto) {
        return webClient.baseUrl(baseUrl).build().put()
                .uri("/pocketbook")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(pocketbookDto).retrieve()
                .bodyToMono(PocketbookDto.class)
                .onErrorResume(error -> {
                    WebClientResponseException response = (WebClientResponseException) error;
                    if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new Exception("Error saving pocketbook..."));
                    }
                    return Mono.error(error);
                });
    }

}
