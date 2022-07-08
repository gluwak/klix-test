package app.api.v1.domain;

public class Application {
    private final String phone;
    private final String email;
    private final Integer monthlyIncome;
    private final Integer monthlyExpensesWithoutCredit;
    private final Integer monthlyCreditLiabilities;
    private final MaritalStatus maritalStatus;
    private final Boolean agreeToBeScored;
    private final Boolean agreeToDataSharing;
    private final Integer amount;
    private final Integer dependents;

    private Application(Builder builder){
        this.phone = builder.phone;
        this.email = builder.email;
        this.monthlyIncome = builder.monthlyIncome;
        this.monthlyExpensesWithoutCredit = builder.monthlyExpensesWithoutCredit;
        this.monthlyCreditLiabilities = builder.monthlyCreditLiabilities;
        this.maritalStatus = builder.maritalStatus;
        this.agreeToBeScored = builder.agreeToBeScored;
        this.agreeToDataSharing = builder.agreeToDataSharing;
        this.amount = builder.amount;
        this.dependents = builder.dependents;
    }

    public Integer getDependents() {
        return dependents;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Integer getMonthlyIncome() {
        return monthlyIncome;
    }

    public Integer getMonthlyExpensesWithoutCredit() {
        return monthlyExpensesWithoutCredit;
    }

    public Integer getMonthlyCreditLiabilities() {
        return monthlyCreditLiabilities;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public Boolean getAgreeToBeScored() {
        return agreeToBeScored;
    }

    public Boolean getAgreeToDataSharing() {
        return agreeToDataSharing;
    }

    public Integer getAmount() {
        return amount;
    }

    public static class Builder{
        private String phone;
        private String email;
        private Integer monthlyIncome;
        private Integer monthlyExpensesWithoutCredit;
        private Integer monthlyCreditLiabilities;
        private MaritalStatus maritalStatus;
        private Boolean agreeToBeScored;
        private Boolean agreeToDataSharing;
        private Integer amount;
        private Integer dependents;

        public Builder(){}

        public Builder phone(String phone){
            this.phone = phone;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder monthlyIncome(Integer monthlyIncome){
            this.monthlyIncome = monthlyIncome;
            return this;
        }

        public Builder monthlyExpensesWithoutCredit(Integer monthlyExpensesWithoutCredit){
            this.monthlyExpensesWithoutCredit = monthlyExpensesWithoutCredit;
            return this;
        }

        public Builder monthlyCreditLiabilities(Integer monthlyCreditLiabilities){
            this.monthlyCreditLiabilities = monthlyCreditLiabilities;
            return this;
        }

        public Builder maritalStatus(MaritalStatus maritalStatus){
            this.maritalStatus = maritalStatus;
            return this;
        }

        public Builder amount(Integer amount){
            this.amount = amount;
            return this;
        }

        public Builder dependents(Integer dependents){
            this.dependents = dependents;
            return this;
        }

        public Builder agreeToBeScored(Boolean agreeToBeScored){
            this.agreeToBeScored = agreeToBeScored;
            return this;
        }

        public Builder agreeToDataSharing(Boolean agreeToDataSharing){
            this.agreeToDataSharing = agreeToDataSharing;
            return this;
        }

        public Application build(){
            return new Application(this);
        }
    }
}
