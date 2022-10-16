package br.com.investidortools.facade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.investidortools.facade.dto.SaleRequestDto;
import br.com.investidortools.facade.exception.GenericBusinessException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SaleService {
	
	private static final String VERSION = "/v1";
	private static final String PATH_REGISTER = "/sale";
	
	@Value(value = "${url.service.sale}")
	private String url;
	
	@Autowired
	private RestTemplate restTemplate;

	public String registerSale(SaleRequestDto saleRequest) throws GenericBusinessException {
		try {
			log.info("Chamando serviço register Sale");
			var request = new HttpEntity<>(saleRequest, null);
			var response = restTemplate.postForEntity(url.concat(VERSION).concat(PATH_REGISTER), request, String.class);
			return response.getBody();
		} catch (RestClientException e) {
			throw new GenericBusinessException(e);
		}
	}

}
