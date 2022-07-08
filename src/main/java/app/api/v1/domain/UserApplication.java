package app.api.v1.domain;

import app.api.v1.response.CreateApplicationResponse;

public class UserApplication {
    private String id;
    private CreateApplicationResponse.Status status;
    private Vendor vendor;
    private Offer offer;

    public UserApplication(String id, CreateApplicationResponse.Status status, Vendor vendor, Offer offer) {
        this.id = id;
        this.status = status;
        this.vendor = vendor;
        this.offer = offer;
    }

    public UserApplication(){}

    public String getId() {
        return id;
    }

    public CreateApplicationResponse.Status getStatus() {
        return status;
    }

    public Offer getOffer() {
        return offer;
    }

    public Vendor getVendor() {
        return vendor;
    }
}
