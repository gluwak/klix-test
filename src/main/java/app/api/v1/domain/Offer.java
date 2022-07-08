package app.api.v1.domain;

public class Offer {
    public Integer monthlyPaymentAmount;
    public Integer totalRepaymentAmount;
    public Integer numberOfPayments;
    public Integer annualPercentageRate;
    public String firstRepaymentDate;

    public Offer() {
    }

    public Offer(Integer monthlyPaymentAmount, Integer totalRepaymentAmount, Integer numberOfPayments,
                 Integer annualPercentageRate, String firstRepaymentDate) {
        this.monthlyPaymentAmount = monthlyPaymentAmount;
        this.totalRepaymentAmount = totalRepaymentAmount;
        this.numberOfPayments = numberOfPayments;
        this.annualPercentageRate = annualPercentageRate;
        this.firstRepaymentDate = firstRepaymentDate;
    }
}
