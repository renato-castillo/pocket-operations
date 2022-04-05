package com.bootcamp.pocketoperations.webclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DebitCardDto {

    private String idDebitCard;

    private String cardNumber;

    private List<AccountDto> bankAccounts = new ArrayList<>();

    private Integer dniUser;

    private Boolean isActive;

    @JsonIgnore
    public void addBankAccount(AccountDto bankAccount) {
        this.bankAccounts.add(bankAccount);
    }

}