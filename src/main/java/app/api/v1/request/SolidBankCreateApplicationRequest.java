package app.api.v1.request;

import app.api.v1.domain.MaritalStatus;

public class SolidBankCreateApplicationRequest extends BaseRequest{
    private final String phone;
    private final Float monthlyIncome;
    private final Float monthlyExpenses;
    private final MaritalStatus maritalStatus;
    private final Boolean agreeToBeScored;

    private SolidBankCreateApplicationRequest(Builder builder){
        super(builder.email, builder.amount);
        this.phone = builder.phone;
        this.monthlyIncome = builder.monthlyIncome;
        this.monthlyExpenses = builder.monthlyExpenses;
        this.maritalStatus = builder.maritalStatus;
        this.agreeToBeScored = builder.agreeToBeScored;
    }

    public String getPhone() {
        return phone;
    }

    public Float getMonthlyIncome() {
        return monthlyIncome;
    }

    public Float getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public Boolean getAgreeToBeScored() {
        return agreeToBeScored;
    }

    public static class Builder{
        private String phone;
        private String email;
        private Float monthlyIncome;
        private Float monthlyExpenses;
        private MaritalStatus maritalStatus;
        private Boolean agreeToBeScored;
        private Float amount;

        public Builder(){}

        public SolidBankCreateApplicationRequest.Builder phone(String phone){
            this.phone = phone;
            return this;
        }

        public SolidBankCreateApplicationRequest.Builder email(String email){
            this.email = email;
            return this;
        }

        public SolidBankCreateApplicationRequest.Builder monthlyIncome(Float monthlyIncome){
            this.monthlyIncome = monthlyIncome;
            return this;
        }

        public SolidBankCreateApplicationRequest.Builder monthlyExpenses(Float monthlyExpenses){
            this.monthlyExpenses = monthlyExpenses;
            return this;
        }

        public SolidBankCreateApplicationRequest.Builder maritalStatus(MaritalStatus maritalStatus){
            this.maritalStatus = maritalStatus;
            return this;
        }

        public SolidBankCreateApplicationRequest.Builder amount(Float amount){
            this.amount = amount;
            return this;
        }

        public SolidBankCreateApplicationRequest.Builder agreeToBeScored(Boolean agreeToBeScored){
            this.agreeToBeScored = agreeToBeScored;
            return this;
        }

        public SolidBankCreateApplicationRequest build(){
            return new SolidBankCreateApplicationRequest(this);
        }
    }
}
