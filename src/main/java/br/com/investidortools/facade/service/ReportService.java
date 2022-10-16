package br.com.investidortools.facade.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.investidortools.facade.dto.ReportDto;
import br.com.investidortools.facade.dto.ReportResponseDto;
import br.com.investidortools.facade.dto.ReportsDto;
import br.com.investidortools.facade.dto.ReportsResponseDto;
import br.com.investidortools.facade.exception.GenericBusinessException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReportService {
	
	private static final String VERSION = "/v1";
	private static final String PATH_GET_ALL = "/report";
	private static final String PATH_GET_REPORT = "/report/%d";
	private static final String PATH_DOWNLOAD_REPORT = "/report/%d/download";
	
	@Value(value = "${url.service.report}")
	private String url;
	
	@Autowired
	private RestTemplate restTemplate;

	public ReportsResponseDto getAllReports() throws GenericBusinessException {
		
		try {
			log.info("Chamando método getAll Reports");
			var response = restTemplate.getForEntity(url.concat(VERSION).concat(PATH_GET_ALL), ReportsDto.class);
			return prepareReportsResponse(response.getBody());
		} catch (RestClientException e) {
			throw new GenericBusinessException(e);
		}
	}

	public ReportResponseDto getReport(Integer id) throws GenericBusinessException {
		try {
			log.info("Chamando método get Report");
			var response = restTemplate.getForEntity(url.concat(VERSION).concat(String.format(PATH_GET_REPORT, id)), ReportDto.class);
			return prepareReportResponse(response.getBody());
		} catch (RestClientException e) {
			throw new GenericBusinessException(e);
		}
	}

	public String downloadReport(Integer id) throws GenericBusinessException {
		
		try {
			log.info("Chamando serviço de download do relatório");
			var response = restTemplate.getForEntity(url.concat(VERSION).concat(String.format(PATH_DOWNLOAD_REPORT, id)), byte[].class);
			return Base64.getEncoder().encodeToString(response.getBody());
		} catch (RestClientException e) {
			throw new GenericBusinessException(e);
		}
	}
	
	private ReportsResponseDto prepareReportsResponse(ReportsDto body) {
		
		log.info("montando response");
		ReportsResponseDto response = new ReportsResponseDto();
		response.setReports(new ArrayList<>());
		
		body.getReports().forEach((ReportDto rep) -> {
			var resportResponse = new ReportResponseDto();
			resportResponse.setAuthor(rep.getAuthor());
			resportResponse.setAuthorName(rep.getAuthorName());
			resportResponse.setId(rep.getId());
			resportResponse.setIdCompany(rep.getIdCompany());
			resportResponse.setNameCompany(rep.getNameCompany());
			resportResponse.setPrice(rep.getPrice());
			resportResponse.setTittle(rep.getTittle());
			resportResponse.setReleaseDate(LocalDate.parse(rep.getReleaseDate().toLocalDate().toString()));
			resportResponse.setImgCompany(rep.getImgCompany());
			response.getReports().add(resportResponse);
		});
			   
		return response;
	}
	
	private ReportResponseDto prepareReportResponse(ReportDto rep) {
		
		log.info("montando response");
		
		ReportResponseDto reportResponse = new ReportResponseDto();
		reportResponse.setAuthor(rep.getAuthor());
		reportResponse.setAuthorName(rep.getAuthorName());
		reportResponse.setId(rep.getId());
		reportResponse.setIdCompany(rep.getIdCompany());
		reportResponse.setNameCompany(rep.getNameCompany());
		reportResponse.setPrice(rep.getPrice());
		reportResponse.setTittle(rep.getTittle());
		reportResponse.setReleaseDate(LocalDate.parse(rep.getReleaseDate().toLocalDate().toString()));
		reportResponse.setImgCompany(rep.getImgCompany());
		return reportResponse;
	}
}
