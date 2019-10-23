package br.com.consumingapirest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import br.com.api.ApiApplication;
import br.com.consumingapirest.entity.Quote;

public class ConsumingApiRest {
	
	private static final Logger log = LoggerFactory.getLogger(ApiApplication.class);
	private static final String url = "https://gturnquist-quoters.cfapps.io/api/random"; 
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Quote quote = restTemplate.getForObject(
					url, Quote.class);
			log.info(quote.toString());
		};
	}

}
