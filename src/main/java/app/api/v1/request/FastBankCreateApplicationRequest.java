package app.api.v1.request;


public class FastBankCreateApplicationRequest extends BaseRequest{
    private final String phoneNumber;
    private final Float monthlyIncomeAmount;
    private final Float monthlyCreditLiabilities;
    private final Integer dependents;
    private final Boolean agreeToDataSharing;


    private FastBankCreateApplicationRequest(Builder builder) {
        super(builder.email, builder.amount);
        this.phoneNumber = builder.phoneNumber;
        this.monthlyIncomeAmount = builder.monthlyIncomeAmount;
        this.monthlyCreditLiabilities = builder.monthlyCreditLiabilities;
        this.dependents = builder.dependents;
        this.agreeToDataSharing = builder.agreeToDataSharing;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Float getMonthlyIncomeAmount() {
        return monthlyIncomeAmount;
    }

    public Float getMonthlyCreditLiabilities() {
        return monthlyCreditLiabilities;
    }

    public Integer getDependents() {
        return dependents;
    }

    public Boolean getAgreeToDataSharing() {
        return agreeToDataSharing;
    }

    public static class Builder{
        private String phoneNumber;
        private String email;
        private Float monthlyIncomeAmount;
        private Float monthlyCreditLiabilities;
        private Integer dependents;
        private Boolean agreeToDataSharing;
        private Float amount;

        public Builder(){}

        public FastBankCreateApplicationRequest.Builder phoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public FastBankCreateApplicationRequest.Builder email(String email){
            this.email = email;
            return this;
        }

        public FastBankCreateApplicationRequest.Builder monthlyIncomeAmount(Float monthlyIncomeAmount){
            this.monthlyIncomeAmount = monthlyIncomeAmount;
            return this;
        }

        public FastBankCreateApplicationRequest.Builder monthlyCreditLiabilities(Float monthlyCreditLiabilities){
            this.monthlyCreditLiabilities = monthlyCreditLiabilities;
            return this;
        }

        public FastBankCreateApplicationRequest.Builder dependents(Integer dependents){
            this.dependents = dependents;
            return this;
        }

        public FastBankCreateApplicationRequest.Builder amount(Float amount){
            this.amount = amount;
            return this;
        }

        public FastBankCreateApplicationRequest.Builder agreeToDataSharing(Boolean agreeToDataSharing){
            this.agreeToDataSharing = agreeToDataSharing;
            return this;
        }

        public FastBankCreateApplicationRequest build(){
            return new FastBankCreateApplicationRequest(this);
        }
    }
}
