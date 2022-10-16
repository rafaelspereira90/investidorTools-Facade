package br.com.investidortools.facade.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Map;

@Getter
@AllArgsConstructor
@Slf4j
public class GenericBusinessException extends Exception {

    private final HttpStatus status;
    private final String message;

    public GenericBusinessException(Exception e) {
        super(e);
        var httpStatus = this.getStatus();
        var messageError = this.getMessage();
        if (e instanceof HttpClientErrorException) {
            httpStatus = ((HttpClientErrorException) e).getStatusCode();
            if (httpStatus.equals(HttpStatus.NOT_FOUND)){
                messageError = httpStatus.getReasonPhrase();
            } else {
                messageError = getErrorMessage(((HttpClientErrorException) e).getResponseBodyAsString());
            }
        }
        if (e instanceof HttpServerErrorException) {
            httpStatus = ((HttpServerErrorException) e).getStatusCode();
            messageError = getErrorMessage(((HttpServerErrorException) e).getResponseBodyAsString());
        }
        if (e instanceof ResourceAccessException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            messageError = getErrorMessage(((ResourceAccessException) e).getMessage());
        }
        this.status = httpStatus;
        this.message = messageError;
    }

    private String getErrorMessage(String errorHttp) {
        var json = JsonParserFactory.getJsonParser();
        Map<String, Object> jsonRetornoCliente = null;
        try {
            jsonRetornoCliente = json.parseMap(errorHttp);
            var messageJson = jsonRetornoCliente.get("message");
            if (messageJson == null) {
                messageJson = jsonRetornoCliente.get("messageError");
            }
            return messageJson.toString();
        } catch (JsonParseException e) {
            log.error("Erro: {}", e.getMessage());
            return "Serviço Indisponível";
        }
    }
}
