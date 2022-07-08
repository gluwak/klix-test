package app.api.v1.exceptions;

import java.util.Map;

public class FormFieldException extends RuntimeException {

    private Map<String, String> errorFields;
    private String errorMessage;

    public FormFieldException(Map<String, String> errorFields) {
        super();
        this.errorFields = errorFields;
    }

    public FormFieldException(String errorMessage){
        super();
        this.errorMessage = errorMessage;
    }

    public Map<String, String> getErrorFields() {
        return errorFields;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
