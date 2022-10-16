package br.com.investidortools.facade.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.investidortools.facade.dto.SaleRequestDto;
import br.com.investidortools.facade.exception.GenericBusinessException;
import br.com.investidortools.facade.service.SaleService;

@RestController
@RequestMapping("/v1")
public class SaleResource {

	@Autowired
	private SaleService saleService;
	
	@PostMapping("/sale")
    public ResponseEntity<String> registerSale(@Valid @RequestBody SaleRequestDto saleRequest) throws GenericBusinessException {
		return ResponseEntity.ok(saleService.registerSale(saleRequest));
	}
}
