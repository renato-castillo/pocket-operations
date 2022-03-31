package com.bootcamp.pocketoperations.repository;

import com.bootcamp.pocketoperations.entity.Operation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IOperationRepository extends ReactiveMongoRepository<Operation, String> {
}
