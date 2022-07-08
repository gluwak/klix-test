package app.api.v1.controller;

import app.api.v1.service.ApplicationService;
import app.api.v1.domain.UserApplication;
import app.api.v1.response.ApplicationResponse;
import app.api.v1.request.CreateApplicationForm;
import app.api.v1.request.OfferRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ApplicationController {

    public static final String CREATE_APPLICATION = "/application";
    public static final String GET_OFFERS = "/offers";

    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @PostMapping(CREATE_APPLICATION)
    public ApplicationResponse createApplication(@RequestBody CreateApplicationForm request) {
        return service.createApplication(request);
    }

    @PostMapping(GET_OFFERS)
    public List<UserApplication> getOffers(@RequestBody OfferRequest offerRequest) {
        return service.getOffers(offerRequest);
    }
}
