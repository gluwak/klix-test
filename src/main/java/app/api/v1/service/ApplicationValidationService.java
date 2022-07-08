package app.api.v1.service;

import app.api.v1.exceptions.FormFieldException;
import app.api.v1.domain.MaritalStatus;
import app.api.v1.request.CreateApplicationForm;
import app.api.v1.domain.Vendor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ApplicationValidationService {

    private static final String PHONE_REGEX = "\\+[0-9]{11,15}";
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String AMOUNT_REGEX = "^(0|[1-9][0-9]{0,})(,\\d{3})*(\\.\\d{1,2})?$";

    private final Map<String, String> errorFields = new HashMap<>();

    public void validateCreateApplicationRequest(CreateApplicationForm form) {
        validatePhone(form.getPhone());
        validateEmail(form.getEmail());
        validateNumbersFromForm(form.getAmount(), "amount");
        validateNumbersFromForm(form.getMonthlyIncome(), "monthlyIncome");
        validateNumbersFromForm(form.getMonthlyExpenses(), "monthlyExpenses");
        validateNumbersFromForm(form.getMonthlyCreditLiabilities(), "monthlyCreditLiabilities");
        validateDependents(form.getDependents());
        validateAgreements(form.getAgreeToBeScored(), false, "agreeToBeScored");
        validateAgreements(form.getAgreeToDataSharing(), false, "agreeToDataSharing");
        validateMaritalStatus(form.getMaritalStatus());

        checkErrorFields();
    }

    private void checkErrorFields(){
        if(!errorFields.isEmpty()){
            throw new FormFieldException(errorFields);
        }
    }

    private void validateNumbersFromForm(String formNumber, String field) {
        if (!formNumber.matches(AMOUNT_REGEX)) {
            errorFields.put(field, "Incorrect type of money");
        }
    }

    private void validatePhone(String phone) {
        if (!phone.matches(PHONE_REGEX)) {
            errorFields.put("phone", "Incorrect type of phone number");
        }
    }

    private void validateEmail(String email) {
        if (!email.matches(EMAIL_REGEX)) {
            errorFields.put("email", "Incorrect type of email number");
        }
    }

    private void validateDependents(String dependents) {
        try {
            int countDependents = Integer.parseInt(dependents);
            if(countDependents<0){
                errorFields.put("dependents", "Value should be 0 or greater");
            }
        } catch (NumberFormatException e) {
            errorFields.put("dependents", "Field should be numeric");
        }
    }

    private void validateAgreements(Boolean agreement, Boolean isMandatory, String field) {
        if (!agreement && isMandatory) {
            errorFields.put(field, "Field is mandatory");
        }
    }

    private void validateMaritalStatus(String maritalStatus) {
        try {
            MaritalStatus.valueOf(maritalStatus);
        } catch (IllegalArgumentException e) {
            errorFields.put("maritalStatus", "Incorrect marital status");
        }
    }

    public Vendor validateVendorName(String vendorName){
        try {
            return Vendor.valueOf(vendorName);
        } catch (IllegalArgumentException e){
            throw new FormFieldException("Invalid vendor name");
        }
    }

}
