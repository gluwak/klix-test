package app.api.v1.exceptions;

import app.api.v1.config.RestTemplateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    private final static Logger LOG = LoggerFactory.getLogger(RestTemplateConfig.class);

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().is4xxClientError()
                || httpResponse.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        LOG.error("Received non-ok response from vendor with status code = {} ", httpResponse.getStatusCode().value());
        LOG.error("Response = {}", httpResponse.getBody());
        throw new VendorApiException("Not successful response from vendor");
    }
}
