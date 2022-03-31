package com.bootcamp.pocketoperations.controller.dto;

import com.bootcamp.pocketoperations.type.ClientType;
import com.bootcamp.pocketoperations.type.OperationType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OperationDto {

    private String id;

    private OperationType operationType;

    private BigDecimal amount;

    private ClientType clientType;

    private LocalDateTime transactionDate;

    private String originNumber;

    private String destinationNumber;
}
