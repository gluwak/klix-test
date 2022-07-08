package app.api.v1.service;

import app.api.v1.request.FastBankCreateApplicationRequest;
import app.api.v1.response.CreateApplicationResponse;
import app.api.v1.domain.Application;
import app.api.v1.domain.UserApplication;
import app.api.v1.domain.Vendor;
import org.springframework.stereotype.Service;

@Service
public class FastBankService implements IVendorApplicationService {

    private final FastBankApiService fastBankApiService;
    private final UtilityService utilityService;

    private static final Vendor VENDOR = Vendor.FAST_BANK;
    private static final String FAST_BANK_PHONE_REGEX = "\\+371[0-9]{8}";

    public FastBankService(FastBankApiService fastBankApiService, UtilityService utilityService) {
        this.fastBankApiService = fastBankApiService;
        this.utilityService = utilityService;
    }

    @Override
    public UserApplication createApplication(Application application) {
        FastBankCreateApplicationRequest request = buildFastBankApplicationRequest(application);
        final CreateApplicationResponse response = fastBankApiService.sendApplication(request);
        return new UserApplication(response.id, response.status, VENDOR, response.offer);
    }

    @Override
    public boolean isApplicationCreationAllowed(Application application) {
        return application.getPhone().matches(FAST_BANK_PHONE_REGEX) &&
                application.getAgreeToDataSharing();
    }

    private FastBankCreateApplicationRequest buildFastBankApplicationRequest(Application application) {
        final float monthlyIncome = utilityService.fromIntAmountToFloatAmount(application.getMonthlyIncome());
        final float monthlyCreditLiabilities = utilityService.fromIntAmountToFloatAmount(application.getMonthlyCreditLiabilities());
        final float amount = utilityService.fromIntAmountToFloatAmount(application.getAmount());

        return new FastBankCreateApplicationRequest.Builder()
                .phoneNumber(application.getPhone())
                .email(application.getEmail())
                .dependents(application.getDependents())
                .monthlyIncomeAmount(monthlyIncome)
                .monthlyCreditLiabilities(monthlyCreditLiabilities)
                .amount(amount)
                .agreeToDataSharing(application.getAgreeToDataSharing())
                .build();
    }

    @Override
    public UserApplication getOfferByApplicationId(String applicationId) {
        final CreateApplicationResponse response = fastBankApiService.getOffer(applicationId);
        return new UserApplication(response.id, response.status, VENDOR, response.offer);
    }

    @Override
    public Vendor getVendorName() {
        return VENDOR;
    }
}
