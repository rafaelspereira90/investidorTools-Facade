package br.com.investidortools.facade.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
	
	@Value(value = "${resttemplate.timeout}")
	private int timeout;
	@Bean
	public RestTemplate restTemplate() {
		
		HttpComponentsClientHttpRequestFactory clientFactory = new HttpComponentsClientHttpRequestFactory();
		clientFactory.setConnectionRequestTimeout(timeout);
		clientFactory.setConnectTimeout(timeout);
		clientFactory.setReadTimeout(timeout);
		
		RestTemplate restTemplate = new RestTemplate(clientFactory);

		List<ClientHttpRequestInterceptor> interceptors
				= restTemplate.getInterceptors();
		if (CollectionUtils.isEmpty(interceptors)) {
			interceptors = new ArrayList<>();
		}
		interceptors.add(new RestTemplateHeaderModifierInterceptor());
		restTemplate.setInterceptors(interceptors);

		return restTemplate;
	}

	public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {
		private static final String CORRELATION_ID = "X-Correlation-Id";
		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
			request.getHeaders().set(CORRELATION_ID, UUID.randomUUID().toString().toUpperCase());
			return execution.execute(request, body);
		}
	}
}
