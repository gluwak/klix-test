package app.api.v1.service;

import app.api.v1.exceptions.NoAvailableVendorsException;
import app.api.v1.exceptions.VendorApiException;
import app.api.v1.request.CreateApplicationForm;
import app.api.v1.request.OfferRequest;
import app.api.v1.response.ApplicationResponse;
import app.api.v1.response.CreateApplicationResponse;
import app.api.v1.domain.Offer;
import app.api.v1.domain.UserApplication;
import app.api.v1.domain.Vendor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @InjectMocks
    ApplicationService service;

    @Mock
    ApplicationValidationService validationService;
    @Mock
    UtilityService utilityService;

    @Mock
    SolidBankService solidBankService;
    @Mock
    FastBankService fastBankService;

    @Spy
    List<IVendorApplicationService> vendorsList = new ArrayList<>();


    @Test
    public void testCreateApplicationAllRight() {
        vendorsList.add(solidBankService);
        vendorsList.add(fastBankService);

        Mockito.when(solidBankService.isApplicationCreationAllowed(Mockito.any())).thenReturn(true);
        Mockito.when(solidBankService.createApplication(Mockito.any())).thenReturn(generateUserApplication(Vendor.SOLID_BANK, CreateApplicationResponse.Status.DRAFT));

        Mockito.when(fastBankService.isApplicationCreationAllowed(Mockito.any())).thenReturn(true);
        Mockito.when(fastBankService.createApplication(Mockito.any())).thenReturn(generateUserApplication(Vendor.FAST_BANK, CreateApplicationResponse.Status.DRAFT));
        ApplicationResponse response = service.createApplication(generateApplicationForm());
        assertEquals(2, response.getApplications().size());
    }

    @Test
    public void testCreateApplicationFastBankOff() {
        vendorsList.add(solidBankService);
        vendorsList.add(fastBankService);

        Mockito.when(solidBankService.isApplicationCreationAllowed(Mockito.any())).thenReturn(true);
        Mockito.when(solidBankService.createApplication(Mockito.any())).thenReturn(generateUserApplication(Vendor.SOLID_BANK, CreateApplicationResponse.Status.DRAFT));

        Mockito.when(fastBankService.isApplicationCreationAllowed(Mockito.any())).thenReturn(true);
        Mockito.when(fastBankService.createApplication(Mockito.any())).thenThrow(VendorApiException.class);
        ApplicationResponse response = service.createApplication(generateApplicationForm());
        assertEquals(1, response.getApplications().size());
    }

    @Test
    public void testCreateApplicationSolidBankOff() {
        vendorsList.add(solidBankService);
        vendorsList.add(fastBankService);

        Mockito.when(solidBankService.isApplicationCreationAllowed(Mockito.any())).thenReturn(true);
        Mockito.when(solidBankService.createApplication(Mockito.any())).thenThrow(VendorApiException.class);

        Mockito.when(fastBankService.isApplicationCreationAllowed(Mockito.any())).thenReturn(true);
        Mockito.when(fastBankService.createApplication(Mockito.any())).thenReturn(generateUserApplication(Vendor.FAST_BANK, CreateApplicationResponse.Status.DRAFT));
        ApplicationResponse response = service.createApplication(generateApplicationForm());
        assertEquals(1, response.getApplications().size());
    }

    @Test
    public void testCreateApplicationAllBanksOff() {
        vendorsList.add(solidBankService);
        vendorsList.add(fastBankService);

        Mockito.when(solidBankService.isApplicationCreationAllowed(Mockito.any())).thenReturn(true);
        Mockito.when(solidBankService.createApplication(Mockito.any())).thenThrow(VendorApiException.class);

        Mockito.when(fastBankService.isApplicationCreationAllowed(Mockito.any())).thenReturn(true);
        Mockito.when(fastBankService.createApplication(Mockito.any())).thenThrow(VendorApiException.class);
        Assertions.assertThrows(NoAvailableVendorsException.class, () -> {
            service.createApplication(generateApplicationForm());
        });
    }

    @Test
    public void getOffersAllRight() {
        vendorsList.add(solidBankService);
        vendorsList.add(fastBankService);

        Mockito.when(fastBankService.getVendorName()).thenReturn(Vendor.FAST_BANK);
        Mockito.when(solidBankService.getVendorName()).thenReturn(Vendor.SOLID_BANK);

        Mockito.when(validationService.validateVendorName(Vendor.FAST_BANK.name())).thenReturn(Vendor.FAST_BANK);
        Mockito.when(validationService.validateVendorName(Vendor.SOLID_BANK.name())).thenReturn(Vendor.SOLID_BANK);


        OfferRequest request = generateOffersRequest();

        UserApplication fastBankApplication = generateUserApplication(Vendor.FAST_BANK, CreateApplicationResponse.Status.PROCESSED);
        UserApplication solidBankApplication = generateUserApplication(Vendor.SOLID_BANK, CreateApplicationResponse.Status.PROCESSED);

        Mockito.when(fastBankService.getOfferByApplicationId(Mockito.any(String.class))).thenReturn(fastBankApplication);
        Mockito.when(solidBankService.getOfferByApplicationId(Mockito.any(String.class))).thenReturn(solidBankApplication);


        List<UserApplication> userApplications = service.getOffers(request);
        assertEquals(2,userApplications.size());
        assertTrue(userApplications.contains(solidBankApplication));
        assertTrue(userApplications.contains(fastBankApplication));
    }

    @Test
    public void getOffersFastBankIncorrectName() {
        vendorsList.add(solidBankService);
        vendorsList.add(fastBankService);

        Mockito.when(fastBankService.getVendorName()).thenReturn(Vendor.FAST_BANK);
        Mockito.when(solidBankService.getVendorName()).thenReturn(Vendor.SOLID_BANK);

        Mockito.when(validationService.validateVendorName(Vendor.SOLID_BANK.name())).thenReturn(Vendor.SOLID_BANK);


        OfferRequest request = generateOffersRequest();

        UserApplication fastBankApplication = generateUserApplication(Vendor.FAST_BANK, CreateApplicationResponse.Status.PROCESSED);
        UserApplication solidBankApplication = generateUserApplication(Vendor.SOLID_BANK, CreateApplicationResponse.Status.PROCESSED);

        Mockito.when(solidBankService.getOfferByApplicationId(Mockito.any(String.class))).thenReturn(solidBankApplication);


        List<UserApplication> userApplications = service.getOffers(request);
        assertEquals(1,userApplications.size());
        assertTrue(userApplications.contains(solidBankApplication));
        assertFalse(userApplications.contains(fastBankApplication));
    }


    private OfferRequest generateOffersRequest() {

        final List<OfferRequest.ApplicationData> applicationDataList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            final String applicationId = UUID.randomUUID().toString();
            final Vendor vendor = i == 0 ? Vendor.SOLID_BANK : Vendor.FAST_BANK;
            OfferRequest.ApplicationData applicationData = new OfferRequest.ApplicationData(applicationId, vendor.name());
            applicationDataList.add(applicationData);
        }
        return new OfferRequest(applicationDataList);
    }

    private CreateApplicationForm generateApplicationForm() {
        final String phone = "+37120000000";
        final String email = "test@test.lv";
        final String monthlyIncome = "1000.99";
        final String monthlyExpenses = "100.12";
        final String monthlyCreditLiabilities = "50.1";
        final String amount = "120.85";
        final String maritalStatus = "SINGLE";
        final String dependents = "2";
        final Boolean agreeToBeScored = true;
        final Boolean agreeToDataSharing = true;
        return new CreateApplicationForm(phone, email, monthlyIncome, monthlyExpenses, monthlyCreditLiabilities,
                maritalStatus, agreeToBeScored, agreeToDataSharing, amount, dependents);
    }

    private UserApplication generateUserApplication(Vendor vendor, CreateApplicationResponse.Status status) {
        final String id = UUID.randomUUID().toString();
        final Offer offer = createOffer(vendor == Vendor.SOLID_BANK ? 1 : 2);
        return new UserApplication(id, status, vendor, offer);
    }

    private Offer createOffer(int mul) {
        return new Offer(10 * mul,
                100 * mul,
                10 * mul, mul, "2020-02-01");
    }

}