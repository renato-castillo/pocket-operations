package com.bootcamp.pocketoperations.type;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum OperationType {

    PAYMENT(new BigDecimal(1), "PAYMENT"),
    TRANSFER(new BigDecimal(2), "TRANSFER");

    private BigDecimal operationTypeId;
    private String operationTypeDescription;

    private static final Map<BigDecimal, OperationType> lookup = new HashMap<>();

    static {
        EnumSet.allOf(OperationType.class).forEach(x -> lookup.put(x.operationTypeId, x));
    }

    OperationType(BigDecimal key, String value) {
        this.operationTypeId = key;
        this.operationTypeDescription = value;
    }

    public static OperationType get(BigDecimal key) {
        return lookup.get(key);
    }

    public BigDecimal getOperationTypeId() {
        return operationTypeId;
    }

    public String getOperationTypeDescription() {
        return operationTypeDescription;
    }
}
