package app.api.v1.service;

import app.api.v1.domain.Application;
import app.api.v1.domain.UserApplication;
import app.api.v1.domain.Vendor;
import app.api.v1.request.SolidBankCreateApplicationRequest;
import app.api.v1.response.CreateApplicationResponse;
import org.springframework.stereotype.Service;


@Service
public class SolidBankService implements IVendorApplicationService {

    private final SolidBankApiService solidBankApiService;
    private final UtilityService utilityService;

    private static final Vendor VENDOR = Vendor.SOLID_BANK;
    private static final String SOLID_BANK_PHONE_REGEX = "\\+[0-9]{11,15}";

    public SolidBankService(SolidBankApiService solidBankApiService, UtilityService utilityService) {
        this.solidBankApiService = solidBankApiService;
        this.utilityService = utilityService;
    }

    @Override
    public UserApplication createApplication(Application application) {
        SolidBankCreateApplicationRequest request = buildSolidBankCreateApplicationRequest(application);
        final CreateApplicationResponse response = solidBankApiService.sendApplication(request);
        return new UserApplication(response.id, response.status, VENDOR, response.offer);
    }

    @Override
    public boolean isApplicationCreationAllowed(Application application) {
        return application.getPhone().matches(SOLID_BANK_PHONE_REGEX) &&
                application.getAgreeToBeScored();
    }

    private SolidBankCreateApplicationRequest buildSolidBankCreateApplicationRequest(Application application) {
        final float monthlyIncome = utilityService.fromIntAmountToFloatAmount(application.getMonthlyIncome());
        final float amount = utilityService.fromIntAmountToFloatAmount(application.getAmount());

        final int monthlyExpensesInt = application.getMonthlyExpensesWithoutCredit() + application.getMonthlyCreditLiabilities();
        final float monthlyExpenses = utilityService.fromIntAmountToFloatAmount(monthlyExpensesInt);

        return new SolidBankCreateApplicationRequest.Builder()
                .phone(application.getPhone())
                .email(application.getEmail())
                .maritalStatus(application.getMaritalStatus())
                .monthlyIncome(monthlyIncome)
                .monthlyExpenses(monthlyExpenses)
                .amount(amount)
                .agreeToBeScored(application.getAgreeToBeScored())
                .build();
    }

    @Override
    public UserApplication getOfferByApplicationId(String applicationId) {
        final CreateApplicationResponse response = solidBankApiService.getOffer(applicationId);
        return new UserApplication(response.id, response.status, VENDOR, response.offer);
    }

    @Override
    public Vendor getVendorName() {
        return VENDOR;
    }
}
