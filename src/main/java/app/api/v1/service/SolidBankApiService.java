package app.api.v1.service;

import app.api.v1.request.SolidBankCreateApplicationRequest;
import app.api.v1.response.CreateApplicationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;


@Service
public class SolidBankApiService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String CREATE_APPLICATION = "/applications";
    private static final String GET_OFFER = "/applications/{id}";

    public SolidBankApiService(@Qualifier("solid-bank-rest-template") RestTemplate restTemplate, ObjectMapper objectMapper){
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    public CreateApplicationResponse sendApplication(SolidBankCreateApplicationRequest request) {
        return postRequest(CREATE_APPLICATION, request, CreateApplicationResponse.class);
    }

    public CreateApplicationResponse getOffer(String applicationId) {
        Map<String, String> uriVariables = Collections.singletonMap("id", applicationId);
        return getRequest(GET_OFFER, uriVariables, CreateApplicationResponse.class);
    }

    private <T> T postRequest(String url, Object request, Class<T> responseClassType) {
        ResponseEntity<String> rawResponse = restTemplate.postForEntity(url, request, String.class);
        return readResponseFromString(rawResponse, responseClassType);
    }

    private <T> T getRequest(String url, Map<String, ?> uriVariables, Class<T> responseClassType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> rawResponse =
                restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class, uriVariables);
        return readResponseFromString(rawResponse, responseClassType);
    }

    private <T> T readResponseFromString(ResponseEntity<String> rawResponse, Class<T> responseClassType) {
        try {
            final String body = rawResponse.getBody();
            return objectMapper.readValue(body, responseClassType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}
