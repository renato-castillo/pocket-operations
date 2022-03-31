package com.bootcamp.pocketoperations.repository;

import com.bootcamp.pocketoperations.entity.Operation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface IOperationRepository extends ReactiveMongoRepository<Operation, String> {

    Flux<Operation> findAllByOriginNumberIsOrDestinationNumberIs(String originNumber, String destinationNumber);

}
