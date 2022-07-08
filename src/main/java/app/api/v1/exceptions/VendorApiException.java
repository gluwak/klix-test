package app.api.v1.exceptions;

public class VendorApiException  extends RuntimeException{

    public VendorApiException(String message){
        super(message);
    }
}
