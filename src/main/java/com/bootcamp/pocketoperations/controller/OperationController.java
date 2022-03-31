package com.bootcamp.pocketoperations.controller;

import com.bootcamp.pocketoperations.controller.dto.OperationDto;
import com.bootcamp.pocketoperations.resource.OperationResource;
import com.bootcamp.pocketoperations.service.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/operations")
public class OperationController {

    @Autowired
    private OperationResource operationResource;

    @PostMapping
    public Mono<OperationDto> save(OperationDto operationDto) {
        return operationResource.saveOperation(operationDto);
    }

    @GetMapping("/client/{cellphone}")
    public Flux<OperationDto> findAllByClientCellphoneNumber(@PathVariable("cellphone") String originCellphone) {
        return operationResource.findAllByClient(originCellphone);
    }

}
