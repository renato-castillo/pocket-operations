package com.bootcamp.pocketoperations.resource;

import com.bootcamp.pocketoperations.controller.dto.OperationDto;
import com.bootcamp.pocketoperations.entity.Operation;
import com.bootcamp.pocketoperations.service.IOperationService;
import com.bootcamp.pocketoperations.type.ClientType;
import com.bootcamp.pocketoperations.type.OperationType;
import com.bootcamp.pocketoperations.webclient.PocketbookWebClient;
import com.bootcamp.pocketoperations.webclient.dto.PocketbookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OperationResource {

    @Autowired
    private IOperationService operationService;

    @Autowired
    private PocketbookWebClient pocketbookWebClient;

    public Mono<OperationDto> saveOperation(OperationDto operationDto) {

        Operation operation = convertToEntity(operationDto);

        switch (operationDto.getOperationType()) {
            case PAYMENT:
                /* PRIMERO OBTENGO EL MONEDERO
                POSTERIORMENTE, OBTENGO EL SALDO Y VALIDO
                SI HAY SALDO REALIZO LA OPERACIÓN Y LA GUARDO,
                DE LO CONTRARIO, RETORNO UN EMPTY
                */
                return operationService.save(operation).map(x -> convertToDto(x));
            default:
                return Mono.empty();
        }
    }

    private Mono<PocketbookDto> payment(String originCellphoneNumber, BigDecimal amount) {
        return pocketbookWebClient.findByCellphone(originCellphoneNumber)
                .flatMap(x -> {
                    if (amount.compareTo(x.getBalance()) <= 0) {
                        x.setBalance(x.getBalance().subtract(amount));
                    } else {
                        return Mono.error(new Exception("Insufficient Balance"));
                    }

                    return pocketbookWebClient.update(x);
                });
    }

    public Flux<OperationDto> findAllByClient(String cellphoneNumber) {
        return operationService.findAllByOriginNumberIsOrDestionationNumberIs(cellphoneNumber, cellphoneNumber)
                .map(x -> {
                    if(x.getOriginNumber() == cellphoneNumber) {
                        x.setAmount(x.getAmount().negate());
                    } else {
                        x.setAmount(x.getAmount().abs());
                    }

                    return convertToDto(x);
                });
    }

    private Operation convertToEntity(OperationDto operationDto) {
        Operation operation = new Operation();
        operation.setId(operationDto.getId());
        operation.setOperationType(operationDto.getOperationType().getOperationTypeDescription());
        operation.setClientType(operationDto.getClientType().getClientTypeDescription());
        operation.setDestinationNumber(operation.getDestinationNumber());
        operation.setOriginNumber(operation.getOriginNumber());
        operation.setDestinationNumber(operationDto.getDestinationNumber());
        operation.setTransactionDate(LocalDateTime.now());

        return operation;
    }

    private OperationDto convertToDto(Operation operation) {
        OperationDto operationDto = new OperationDto();
        operationDto.setId(operation.getId());
        operationDto.setOperationType(OperationType.valueOf(operation.getOperationType()));
        operationDto.setClientType(ClientType.valueOf(operation.getClientType()));
        operationDto.setDestinationNumber(operation.getDestinationNumber());
        operationDto.setOriginNumber(operation.getOriginNumber());
        operationDto.setDestinationNumber(operationDto.getDestinationNumber());
        operationDto.setTransactionDate(LocalDateTime.now());

        return operationDto;
    }

}
