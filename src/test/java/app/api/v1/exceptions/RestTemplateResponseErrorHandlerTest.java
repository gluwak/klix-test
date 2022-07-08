package app.api.v1.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpResponse;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class RestTemplateResponseErrorHandlerTest {

    RestTemplateResponseErrorHandler responseErrorHandler = new RestTemplateResponseErrorHandler();

    @Test
    public void testHasErrors() throws IOException {
        ClientHttpResponse response = new MockClientHttpResponse(new byte[0], HttpStatus.OK);
        assertFalse(responseErrorHandler.hasError(response));

        response = new MockClientHttpResponse(new byte[0], HttpStatus.CONTINUE);
        assertFalse(responseErrorHandler.hasError(response));

        response = new MockClientHttpResponse(new byte[0], HttpStatus.FOUND);
        assertFalse(responseErrorHandler.hasError(response));

        response = new MockClientHttpResponse(new byte[0], HttpStatus.NOT_FOUND);
        assertTrue(responseErrorHandler.hasError(response));

        response = new MockClientHttpResponse(new byte[0], HttpStatus.SERVICE_UNAVAILABLE);
        assertTrue(responseErrorHandler.hasError(response));
    }

    @Test
    public void testHandleError(){
        ClientHttpResponse response = new MockClientHttpResponse(new byte[0], HttpStatus.NOT_FOUND);
        Assertions.assertThrows(VendorApiException.class, () -> {
            responseErrorHandler.handleError(response);
        });
    }
}