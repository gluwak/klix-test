package app.api.v1.service;

import app.api.v1.domain.Application;
import app.api.v1.domain.UserApplication;
import app.api.v1.domain.Vendor;

public interface IVendorApplicationService {

    UserApplication createApplication(Application application);

    boolean isApplicationCreationAllowed(Application application);

    UserApplication getOfferByApplicationId(String applicationId);

    Vendor getVendorName();

}
