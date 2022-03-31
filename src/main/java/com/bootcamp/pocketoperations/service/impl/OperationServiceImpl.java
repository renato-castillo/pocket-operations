package com.bootcamp.pocketoperations.service.impl;

import com.bootcamp.pocketoperations.entity.Operation;
import com.bootcamp.pocketoperations.repository.IOperationRepository;
import com.bootcamp.pocketoperations.service.IOperationService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OperationServiceImpl implements IOperationService {

    @Autowired
    private IOperationRepository operationRepository;

    @Override
    public Mono<Operation> save(Operation operation) {
        return operationRepository.save(operation);
    }

    @Override
    public Flux<Operation> findAll() {
        return operationRepository.findAll();
    }

    @Override
    public Mono<Operation> findById(String id) {
        return operationRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        operationRepository.deleteById(id);
    }
}
