package app.api.v1.service;

import app.api.v1.exceptions.FormFieldException;
import app.api.v1.request.CreateApplicationForm;
import app.api.v1.domain.Vendor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplicationValidationServiceTest {

    @InjectMocks
    ApplicationValidationService service;

    @Test
    public void testValidateVendorNameAllRight() {
        for (Vendor v : Vendor.values()) {
            Vendor vendor = service.validateVendorName(v.name());
            assertEquals(v, vendor);
        }
    }

    @Test
    public void testValidateVendorNameException() {
        Assertions.assertThrows(FormFieldException.class, () -> {
            service.validateVendorName("INVALID_ENUM");
        });
    }

    @Test
    public void validateCreateApplicationRequestAllRight(){
        CreateApplicationForm form = generateApplicationForm(null,null,null,null,null);
        Assertions.assertDoesNotThrow(() -> service.validateCreateApplicationRequest(form));
    }

    @Test
    public void validateCreateApplicationRequestIncorrectPhone(){
        CreateApplicationForm form = generateApplicationForm("+124845454987156454",null,null,null,null);
        Assertions.assertThrows(FormFieldException.class, () -> {
            service.validateCreateApplicationRequest(form);
        });
    }

    @Test
    public void validateCreateApplicationRequestIncorrectEmail(){
        CreateApplicationForm form = generateApplicationForm(null,"ijjsad@ijijsad.asd23asda",null,null,null);
        Assertions.assertThrows(FormFieldException.class, () -> {
            service.validateCreateApplicationRequest(form);
        });
    }

    @Test
    public void validateCreateApplicationRequestIncorrectAmount(){
        CreateApplicationForm form = generateApplicationForm(null,null, "1,213.0.",null,null);
        Assertions.assertThrows(FormFieldException.class, () -> {
            service.validateCreateApplicationRequest(form);
        });
    }

    @Test
    public void validateCreateApplicationRequestIncorrectMaritalStatus(){
        CreateApplicationForm form = generateApplicationForm(null,null, null,"EASTER_EGG",null);
        Assertions.assertThrows(FormFieldException.class, () -> {
            service.validateCreateApplicationRequest(form);
        });
    }

    @Test
    public void validateCreateApplicationRequestIncorrectDependentsFormat(){
        CreateApplicationForm form = generateApplicationForm(null,null, null,null, "huhu");
        Assertions.assertThrows(FormFieldException.class, () -> {
            service.validateCreateApplicationRequest(form);
        });
    }

    @Test
    public void validateCreateApplicationRequestIncorrectDependentsCount(){
        CreateApplicationForm form = generateApplicationForm(null,null, null,null, "-1");
        Assertions.assertThrows(FormFieldException.class, () -> {
            service.validateCreateApplicationRequest(form);
        });
    }

    @Test
    public void validateCreateApplicationRequestAllIncorrect(){
        CreateApplicationForm form = new CreateApplicationForm("as","12","000.21021",
                "sadas","200,,000.00","JUST_ALONE",false,
                false,"-213","-41sad");
        Assertions.assertThrows(FormFieldException.class, () -> {
            service.validateCreateApplicationRequest(form);
        });
    }

    private CreateApplicationForm generateApplicationForm(String incorrectPhone, String incorrectEmail,String incorrectAmount,
                                                          String incorrectMaritalStatus, String incorrectDependents) {
        final String phone = incorrectPhone == null? "+37120000000": incorrectPhone;
        final String email = incorrectEmail == null?"test@test.lv": incorrectEmail;
        final String monthlyIncome = "1000.99";
        final String monthlyExpenses = "100.12";
        final String monthlyCreditLiabilities = "50.1";
        final String amount = incorrectAmount == null? "120.85" : incorrectAmount;
        final String maritalStatus = incorrectMaritalStatus == null ? "SINGLE" : incorrectMaritalStatus;
        final String dependents = incorrectDependents == null ? "2" : incorrectDependents;
        final Boolean agreeToBeScored = true;
        final Boolean agreeToDataSharing = true;
        return new CreateApplicationForm(phone, email, monthlyIncome, monthlyExpenses, monthlyCreditLiabilities,
                maritalStatus, agreeToBeScored, agreeToDataSharing, amount, dependents);
    }


}