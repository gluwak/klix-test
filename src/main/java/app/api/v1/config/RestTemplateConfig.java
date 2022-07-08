package app.api.v1.config;

import app.api.v1.exceptions.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateConfig {


    @Value("${solid.bank.uri}")
    private String SOLID_BANK_DEFAULT_URI;
    @Value("${fast.bank.uri}")
    private String FAST_BANK_DEFAULT_URI;

    @Bean("solid-bank-rest-template")
    public RestTemplate getSolidBankRestTemplate() {
        return getRestTemplateWithDefaultURI(SOLID_BANK_DEFAULT_URI);
    }

    @Bean("fast-bank-rest-template")
    public RestTemplate getFastBankRestTemplate() {
        return getRestTemplateWithDefaultURI(FAST_BANK_DEFAULT_URI);
    }

    private RestTemplate getRestTemplateWithDefaultURI(String uri) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(uri));
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        return restTemplate;
    }
}
