package com.bootcamp.pocketoperations.webclient.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BankDebitDto {

    private String idBankAccount;
    private Integer dniUser;
    private Integer accountNumber;
    private Integer balance;
    private String typeAccount;
    private Integer maintenanceCharge;
    private Integer movementNumber;
    private Boolean benefitStatus;
    private String idDebitCard;
    private String cardNumber;
    private List<AccountDto> bankAccountDtos = new ArrayList<>();
    private Boolean isActive;
}
