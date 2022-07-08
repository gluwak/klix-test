package app.api.v1.service;

import app.api.v1.response.CreateApplicationResponse;
import app.api.v1.domain.Application;
import app.api.v1.domain.MaritalStatus;
import app.api.v1.domain.UserApplication;
import app.api.v1.domain.Vendor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FastBankServiceTest {

    @InjectMocks
    FastBankService service;
    @Mock
    UtilityService utilityService;
    @Mock
    FastBankApiService fastBankApiService;

    @Test
    public void testCreateApplication() {
        Mockito.when(fastBankApiService.sendApplication(Mockito.any())).thenReturn(generateCreateApplicationResponse());

        Assertions.assertDoesNotThrow(() -> {
            UserApplication userApplication = service.createApplication(generateApplication(null, null));
            assertEquals(service.getVendorName(), userApplication.getVendor());
        });
    }

    @Test
    public void testIsCreationAllowedSuccess() {
        Application application = generateApplication(null, null);
        assertTrue(service.isApplicationCreationAllowed(application));
    }

    @Test
    public void testIsCreationAlloweIncorrectPhone() {
        Application application = generateApplication(null, "+12184154545154465644");
        assertFalse(service.isApplicationCreationAllowed(application));
    }

    @Test
    public void testIsCreationAlloweIncorrectDataSharing() {
        Application application = generateApplication(false, null);
        assertFalse(service.isApplicationCreationAllowed(application));
    }

    @Test
    public void testIsCreationAllowedAllIncorrect() {
        Application application = generateApplication(false, "+12184154545154465644");
        assertFalse(service.isApplicationCreationAllowed(application));
    }

    @Test
    public void testGetVendorNameSuccess() {
        assertEquals(Vendor.FAST_BANK, service.getVendorName());
    }

    @Test
    public void testGetVendorNameIncorrect() {
        assertNotEquals(Vendor.SOLID_BANK, service.getVendorName());
    }

    @Test
    public void testGetOfferByApplicationId(){
        Mockito.when(fastBankApiService.getOffer(Mockito.any(String.class))).thenReturn(generateCreateApplicationResponse());

        Assertions.assertDoesNotThrow(() -> {
            UserApplication userApplication = service.getOfferByApplicationId(UUID.randomUUID().toString());
            assertEquals(service.getVendorName(), userApplication.getVendor());
        });
    }

    private CreateApplicationResponse generateCreateApplicationResponse() {
        CreateApplicationResponse response = new CreateApplicationResponse();
        response.id = UUID.randomUUID().toString();
        response.offer = null;
        response.status = CreateApplicationResponse.Status.DRAFT;
        return response;
    }

    private Application generateApplication(Boolean incorrectDataSharing, String incorrectPhone) {
        return new Application.Builder()
                .dependents(0)
                .agreeToDataSharing(incorrectDataSharing == null || incorrectDataSharing)
                .agreeToBeScored(true)
                .email("abba@abab.lv")
                .maritalStatus(MaritalStatus.SINGLE)
                .phone(incorrectPhone == null ? "+37120000000" : incorrectPhone)
                .amount(12300)
                .monthlyCreditLiabilities(10000)
                .monthlyIncome(100000)
                .monthlyExpensesWithoutCredit(1000)
                .build();
    }
}