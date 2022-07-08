package app.api.v1.service;

import app.api.v1.exceptions.NoAvailableVendorsException;
import app.api.v1.exceptions.VendorApiException;
import app.api.v1.domain.Application;
import app.api.v1.domain.MaritalStatus;
import app.api.v1.domain.UserApplication;
import app.api.v1.domain.Vendor;
import app.api.v1.request.CreateApplicationForm;
import app.api.v1.response.ApplicationResponse;
import app.api.v1.request.OfferRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final static Logger LOG = LoggerFactory.getLogger(ApplicationService.class);

    private final ApplicationValidationService validationService;
    private final UtilityService utilityService;
    private final List<IVendorApplicationService> vendorsList;


    public ApplicationService(ApplicationValidationService validationService, UtilityService utilityService,
                              List<IVendorApplicationService> vendorsList) {

        this.validationService = validationService;
        this.utilityService = utilityService;
        this.vendorsList = vendorsList;
    }

    public ApplicationResponse createApplication(CreateApplicationForm request) {
        validationService.validateCreateApplicationRequest(request);
        final Application application = buildApplicationFromRequest(request);
        List<UserApplication> userApplicationList = createVendorsApplications(application);
        return new ApplicationResponse(userApplicationList);
    }

    public List<UserApplication> getOffers(OfferRequest offerRequest) {
        List<UserApplication> userApplications = new ArrayList<>();
        List<OfferRequest.ApplicationData> applicationDataList = offerRequest.getApplicationDataList();
        Map<Vendor, IVendorApplicationService> vendorByName = createVendorByNameMap();

        for (OfferRequest.ApplicationData data : applicationDataList) {
            final Vendor vendorName = validationService.validateVendorName(data.getVendor());
            IVendorApplicationService vendor = vendorByName.get(vendorName);
            if (vendor != null) {
                final UserApplication userApplication = vendor.getOfferByApplicationId(data.getApplicationId());
                userApplications.add(userApplication);
            }
        }

        return userApplications;
    }

    private List<UserApplication> createVendorsApplications(Application application) {
        final List<UserApplication> userApplications = new ArrayList<>();

        for (IVendorApplicationService vendor : vendorsList) {
            if (vendor.isApplicationCreationAllowed(application)) {
                try {
                    final UserApplication userApplication = vendor.createApplication(application);
                    userApplications.add(userApplication);
                } catch (VendorApiException ignored) {
                    LOG.warn("{} is not available",vendor.getVendorName());
                }
            }
        }

        if(userApplications.isEmpty()){
            throw new NoAvailableVendorsException();
        }

        return userApplications;
    }

    private Map<Vendor, IVendorApplicationService> createVendorByNameMap(){
        return vendorsList.stream()
                .collect(Collectors.toMap(IVendorApplicationService::getVendorName, Function.identity()));
    }

    private Application buildApplicationFromRequest(CreateApplicationForm request) {
        final int monthlyIncome = utilityService.moneyAmountFromStringToInt(request.getMonthlyIncome());
        final int monthlyExpenses = utilityService.moneyAmountFromStringToInt(request.getMonthlyExpenses());
        final int monthlyCreditLiabilities = utilityService.moneyAmountFromStringToInt(request.getMonthlyCreditLiabilities());
        final int amount = utilityService.moneyAmountFromStringToInt(request.getAmount());
        final int dependents = Integer.parseInt(request.getDependents());
        final MaritalStatus maritalStatus = MaritalStatus.valueOf(request.getMaritalStatus());

        return new Application.Builder()
                .phone(request.getPhone())
                .email(request.getEmail())
                .monthlyIncome(monthlyIncome)
                .monthlyExpensesWithoutCredit(monthlyExpenses)
                .monthlyCreditLiabilities(monthlyCreditLiabilities)
                .maritalStatus(maritalStatus)
                .agreeToBeScored(request.getAgreeToBeScored())
                .agreeToDataSharing(request.getAgreeToDataSharing())
                .amount(amount)
                .dependents(dependents)
                .build();
    }


}
