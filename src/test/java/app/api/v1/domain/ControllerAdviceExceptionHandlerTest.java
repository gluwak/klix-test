package app.api.v1.domain;

import app.api.v1.exceptions.ControllerAdviceExceptionHandler;
import app.api.v1.exceptions.FormFieldException;
import app.api.v1.exceptions.NoAvailableVendorsException;
import app.api.v1.exceptions.VendorApiException;
import app.api.v1.response.ErrorResponse;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ControllerAdviceExceptionHandlerTest {

    ControllerAdviceExceptionHandler exceptionHandler = new ControllerAdviceExceptionHandler();

    @Test
    public void testHandleInvalidInputData(){
        Map<String,String> errorFields = new HashMap<>();
        errorFields.put("phone", "invalid phone pattern");
        errorFields.put("email","invalid email pattern");
        ErrorResponse errorResponse = exceptionHandler.handleInvalidInputData(new FormFieldException(errorFields));
        assertEquals(errorFields,errorResponse.getFieldErrors());
    }

    @Test
    public void testHandleVendorApiExceptionData(){
        ErrorResponse errorResponse = exceptionHandler.handleVendorApiExceptionData(new VendorApiException("some string"));
        assertEquals("Some internal error",errorResponse.getErrorMessage());
    }

    @Test
    public void testHandleNoAvailableVendorException(){
        ErrorResponse errorResponse = exceptionHandler.handleNoAvailableVendorException(new NoAvailableVendorsException());
        assertEquals("All vendors are off right now",errorResponse.getErrorMessage());
    }

}