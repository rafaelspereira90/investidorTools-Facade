package br.com.investidortools.facade.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.investidortools.facade.dto.ReportResponseDto;
import br.com.investidortools.facade.dto.ReportsResponseDto;
import br.com.investidortools.facade.exception.GenericBusinessException;
import br.com.investidortools.facade.service.ReportService;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1")
public class ReportResource {

	@Autowired
	private ReportService reportService;
	
	@GetMapping("/report")
    public ResponseEntity<ReportsResponseDto> getAllReports() throws GenericBusinessException {
		return ResponseEntity.ok(reportService.getAllReports());
	}
	
	@GetMapping("/report/{id}")
    public ResponseEntity<ReportResponseDto> getReport(@PathVariable(name = "id") Integer id) throws GenericBusinessException {
		return ResponseEntity.ok(reportService.getReport(id));
	}
	
	@GetMapping("/report/{id}/download")
    public ResponseEntity<String> downloadReport(@PathVariable(name = "id") Integer id) throws GenericBusinessException {
		return ResponseEntity.ok(reportService.downloadReport(id));
	}
}
