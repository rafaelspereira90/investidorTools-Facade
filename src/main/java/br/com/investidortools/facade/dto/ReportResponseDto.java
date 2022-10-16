package br.com.investidortools.facade.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ReportResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Integer idCompany;
	
	private String nameCompany;
	
	private LocalDate releaseDate;
	
	private String author;
	
	private String authorName;
	
	private String tittle;
	
	private BigDecimal price;
	
	private String imgCompany;
}
