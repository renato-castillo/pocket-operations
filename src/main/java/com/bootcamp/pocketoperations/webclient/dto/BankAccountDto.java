package com.bootcamp.pocketoperations.webclient.dto;

import lombok.*;

@Builder
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {

    private String idBankAccount;

    private Integer dniUser;
    private Integer accountNumber;
    private Integer balance;
    private String typeAccount;
    private Integer maintenanceCharge;
    private Integer movementNumber;

    @Builder.Default
    private Boolean benefitStatus=false;
}
