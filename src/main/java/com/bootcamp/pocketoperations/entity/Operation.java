package com.bootcamp.pocketoperations.entity;

import com.bootcamp.pocketoperations.type.OperationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Document
public class Operation {

    @Id
    private String id;

    private String operationType;

    private BigDecimal amount;

    private String clientType;

    private LocalDateTime transactionDate;

    private String originNumber;

    private String destinationNumber;


}
