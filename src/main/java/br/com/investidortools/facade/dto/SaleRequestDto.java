package br.com.investidortools.facade.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SaleRequestDto {

	private String buyer;
	private String author;
	private Integer company;
	private Integer report;
	private BigDecimal value;
}
