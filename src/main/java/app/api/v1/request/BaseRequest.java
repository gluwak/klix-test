package app.api.v1.request;

public abstract class BaseRequest {
    private final String email;
    private final Float amount;

    public BaseRequest(String email, Float amount){
        this.email = email;
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public Float getAmount() {
        return amount;
    }
}
