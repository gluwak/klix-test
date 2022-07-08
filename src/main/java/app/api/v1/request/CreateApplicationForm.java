package app.api.v1.request;

public class CreateApplicationForm {
    private String phone;
    private String email;
    private String monthlyIncome;
    private String monthlyExpenses;
    private String monthlyCreditLiabilities;
    private String maritalStatus;
    private Boolean agreeToBeScored;
    private Boolean agreeToDataSharing;
    private String amount;
    private String dependents;

    public CreateApplicationForm(String phone, String email, String monthlyIncome, String monthlyExpenses, String monthlyCreditLiabilities, String maritalStatus, Boolean agreeToBeScored, Boolean agreeToDataSharing, String amount, String dependents){
        this.phone = phone;
        this.email = email;
        this.monthlyIncome = monthlyIncome;
        this.monthlyExpenses = monthlyExpenses;
        this.monthlyCreditLiabilities = monthlyCreditLiabilities;
        this.maritalStatus = maritalStatus;
        this.agreeToBeScored = agreeToBeScored;
        this.agreeToDataSharing = agreeToDataSharing;
        this.amount = amount;
        this.dependents = dependents;
    }
    public CreateApplicationForm(){}


    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public String getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public Boolean getAgreeToBeScored() {
        return agreeToBeScored;
    }

    public String getAmount() {
        return amount;
    }

    public Boolean getAgreeToDataSharing() {
        return agreeToDataSharing;
    }

    public String getDependents() {
        return dependents;
    }

    public String getMonthlyCreditLiabilities() {
        return monthlyCreditLiabilities;
    }
}
