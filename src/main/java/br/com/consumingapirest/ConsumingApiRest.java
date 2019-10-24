package br.com.consumingapirest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import br.com.api.ApiApplication;
import br.com.consumingapirest.entity.Quote;

public class ConsumingApiRest {

	private static final Logger log = LoggerFactory.getLogger(ApiApplication.class);
	private static final String url = "https://gturnquist-quoters.cfapps.io/api/random";

	@Bean
	public Quote consumingRestApi() throws Exception {
		RestTemplate restTemplate = new RestTemplate();

		Quote quote = restTemplate.getForObject(url, Quote.class);
		log.info(quote.toString());
		
		return quote;

	}

}
