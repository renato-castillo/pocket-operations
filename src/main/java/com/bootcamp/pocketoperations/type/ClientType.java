package com.bootcamp.pocketoperations.type;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ClientType {

    BUSINESS(new BigDecimal(1), "BUSINESS"),
    PERSONAL(new BigDecimal(2), "PERSONAL");

    private BigDecimal clientTypeId;
    private String clientTypeDescription;

    private static final Map<BigDecimal, ClientType> lookup = new HashMap<>();

    static {
        EnumSet.allOf(ClientType.class).forEach(x -> lookup.put(x.clientTypeId, x));
    }

    ClientType(BigDecimal key, String value) {
        this.clientTypeId = key;
        this.clientTypeDescription = value;
    }

    public static ClientType get(BigDecimal key) {
        return lookup.get(key);
    }

    public BigDecimal getClientTypeId() {
        return clientTypeId;
    }

    public String getClientTypeDescription() {
        return clientTypeDescription;
    }
}
