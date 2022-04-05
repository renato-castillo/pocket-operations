package com.bootcamp.pocketoperations.webclient.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDto {

    private Integer accountNumber;
    private LocalDateTime associateDebitCardDate;
    private Boolean flagPrincipal;
}
