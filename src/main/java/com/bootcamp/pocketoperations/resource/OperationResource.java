package com.bootcamp.pocketoperations.resource;

import com.bootcamp.pocketoperations.controller.dto.OperationDto;
import com.bootcamp.pocketoperations.entity.Operation;
import com.bootcamp.pocketoperations.service.IOperationService;
import com.bootcamp.pocketoperations.type.ClientType;
import com.bootcamp.pocketoperations.type.OperationType;
import com.bootcamp.pocketoperations.webclient.PersonalAccountWebclient;
import com.bootcamp.pocketoperations.webclient.PocketbookWebClient;
import com.bootcamp.pocketoperations.webclient.dto.AccountDto;
import com.bootcamp.pocketoperations.webclient.dto.BankAccountDto;
import com.bootcamp.pocketoperations.webclient.dto.BankDebitDto;
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

    @Autowired
    private PersonalAccountWebclient personalAccountWebclient;

    public Mono<OperationDto> saveOperation(OperationDto operationDto) {

        Operation operation = convertToEntity(operationDto);
        operation.setTransactionDate(LocalDateTime.now());

        switch (operation.getOperationType()) {
            case "TRANSFER":
                return transfer(operation).map(x -> convertToDto(x)).onErrorResume(Mono::error);
            default:
                return Mono.empty();
        }
    }

    private Mono<Operation> transfer(Operation operation) {
        return pocketbookWebClient.findByCellphone(operation.getOriginNumber())
                .flatMap(x -> {
                    if(x.getDebitCard() == null) {
                        if (operation.getAmount().compareTo(x.getBalance()) <= 0) {
                            x.setBalance(x.getBalance().subtract(operation.getAmount()));
                            pocketbookWebClient.update(x);
                        } else {
                            return Mono.error(new Exception("Insufficient Balance"));
                        }
                    } else {
                        personalAccountWebclient.findByDebitCard(x.getDebitCard())
                                .flatMap(account -> {
                                    if (operation.getAmount().compareTo(new BigDecimal(account.getBalance())) <= 0) {
                                        account.setBalance(new BigDecimal(account.getBalance()).subtract(operation.getAmount()).intValue());

                                       return personalAccountWebclient.update(convertToBankAccountDto(account));
                                    } else {
                                        return Mono.error(new Exception("Insufficient Balance"));
                                    }
                                });
                    }


                    return operationService.save(operation);
                });
    }

    private BankAccountDto convertToBankAccountDto(BankDebitDto bankDebitDto) {
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setIdBankAccount(bankDebitDto.getIdBankAccount());
        bankAccountDto.setAccountNumber(bankDebitDto.getAccountNumber());
        bankAccountDto.setBalance(bankDebitDto.getBalance());
        bankAccountDto.setTypeAccount(bankDebitDto.getTypeAccount());
        bankAccountDto.setDniUser(bankDebitDto.getDniUser());
        bankAccountDto.setBenefitStatus(bankDebitDto.getBenefitStatus());
        bankAccountDto.setMaintenanceCharge(bankDebitDto.getMaintenanceCharge());
        bankAccountDto.setMovementNumber(bankDebitDto.getMovementNumber());

        return bankAccountDto;
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
        //operation.setOperationType(operationDto.getOperationType().getOperationTypeDescription());
        //operation.setClientType(operationDto.getClientType().getClientTypeDescription());
        operation.setDestinationNumber(operationDto.getDestinationNumber());
        operation.setOriginNumber(operationDto.getOriginNumber());
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
