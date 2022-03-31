package com.bootcamp.pocketoperations.resource;

import com.bootcamp.pocketoperations.controller.dto.OperationDto;
import com.bootcamp.pocketoperations.entity.Operation;
import com.bootcamp.pocketoperations.service.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class OperationResource {

    @Autowired
    private IOperationService operationService;

    public Mono<Operation> saveOperation(OperationDto operationDto) {

        Operation operation = convert(operationDto);

        switch (operationDto.getOperationType()) {
            case PAYMENT:
                /* PRIMERO OBTENGO EL MONEDERO
                POSTERIORMENTE, OBTENGO EL SALDO Y VALIDO
                SI HAY SALDO REALIZO LA OPERACIÓN Y LA GUARDO,
                DE LO CONTRARIO, RETORNO UN EMPTY
                */
                return operationService.save(operation);
            default:
                return Mono.empty();
        }
    }

    Operation convert(OperationDto operationDto) {
        Operation operation = new Operation();
        operation.setOperationType(operationDto.getOperationType().getOperationTypeDescription());
        operation.setClientType(operationDto.getClientType().getClientTypeDescription());
        operation.setDestinationNumber(operation.getDestinationNumber());
        operation.setOriginNumber(operation.getOriginNumber());
        operation.setDestinationNumber(operationDto.getDestinationNumber());
        operation.setTransactionDate(LocalDateTime.now());

        return operation;
    }

}
