package br.com.investidortools.facade.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ReportsResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ReportResponseDto> reports;
}
