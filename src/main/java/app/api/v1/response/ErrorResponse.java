package app.api.v1.response;

import java.util.Map;

public class ErrorResponse {
    private Map<String, String> fieldErrors;
    private String errorMessage;

    public ErrorResponse() {
    }

    public ErrorResponse(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public ErrorResponse(Map<String, String> fieldErrors, String errorMessage) {
        this.fieldErrors = fieldErrors;
        this.errorMessage = errorMessage;
    }

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
