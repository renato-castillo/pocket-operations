package com.bootcamp.pocketoperations.service;

import com.bootcamp.pocketoperations.entity.Operation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IOperationService {

    Mono<Operation> save(Operation operation);
    Flux<Operation> findAll();
    Mono<Operation> findById(String id);
    Flux<Operation> findAllByOriginNumberIsOrDestionationNumberIs(String originNumber, String destinationNumber);
    void deleteById(String id);
}
