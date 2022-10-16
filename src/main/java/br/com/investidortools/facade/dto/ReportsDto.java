package br.com.investidortools.facade.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ReportsDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ReportDto> reports;
}
