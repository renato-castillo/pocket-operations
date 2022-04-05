package com.bootcamp.pocketoperations.webclient;

import com.bootcamp.pocketoperations.webclient.dto.BankAccountDto;
import com.bootcamp.pocketoperations.webclient.dto.BankDebitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class PersonalAccountWebclient {

    @Value("${base.url.personalaccount}")
    private String baseUrl;

    @Autowired
    private WebClient.Builder webClient;

    public Mono<BankDebitDto> findByDebitCard(String cardNumber) {
        return webClient.baseUrl(baseUrl).build().get()
                .uri("/debit-card/get-data/card-number/".concat(cardNumber))
                .retrieve()
                .bodyToMono(BankDebitDto.class)
                .onErrorResume(error -> {
                    WebClientResponseException response = (WebClientResponseException) error;
                    if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new Exception("Pocket not found"));
                    }
                    return Mono.error(error);
                });
    }

    public Mono<BankAccountDto> update(BankAccountDto bankAccountDto) {
        return webClient.baseUrl(baseUrl).build().put()
                .uri("/account/update")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bankAccountDto).retrieve()
                .bodyToMono(BankAccountDto.class)
                .onErrorResume(error -> {
                    WebClientResponseException response = (WebClientResponseException) error;
                    if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new Exception("Error saving pocketbook..."));
                    }
                    return Mono.error(error);
                });
    }
}
