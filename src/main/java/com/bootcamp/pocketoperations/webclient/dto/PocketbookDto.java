package com.bootcamp.pocketoperations.webclient.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PocketbookDto {

	private String id;

	private String typeDocument;

	private String imei;

	private String email;

	private String debitCard;

	private BigDecimal balance;
	
}
