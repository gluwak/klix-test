package app.api.v1.exceptions;

import app.api.v1.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerAdviceExceptionHandler.class);

    @InitBinder
    private void activateDirectFieldAccess(DataBinder dataBinder){
        dataBinder.initDirectFieldAccess();
    }

    @ExceptionHandler(FormFieldException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidInputData(FormFieldException e){
        LOG.info("Exception handling while validating form values");
        return new ErrorResponse(e.getErrorFields());
    }

    @ExceptionHandler(VendorApiException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleVendorApiExceptionData(VendorApiException e){
        LOG.warn("Exception handling while calling vendors api");
        return new ErrorResponse("Some internal error");
    }

    @ExceptionHandler(NoAvailableVendorsException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleNoAvailableVendorException(NoAvailableVendorsException e){
        LOG.warn("No available vendors right now");
        return new ErrorResponse("All vendors are off right now");
    }
}
