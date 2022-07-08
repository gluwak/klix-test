package app.api.v1.service;

import org.springframework.stereotype.Service;

@Service
public class UtilityService {

    public float fromIntAmountToFloatAmount(Integer amount) {
        return (float) amount / 100;
    }

    public int moneyAmountFromStringToInt(String amount) {
        final String amountWithoutCommaSeparators = amount.replace(",", "");
        final String resultAmountString = getResultAmountString(amountWithoutCommaSeparators);

        return Integer.parseInt(resultAmountString);
    }

    private String getResultAmountString(String amount) {
        int digitsAfterDot = amount.contains(".") ? amount.split("\\.")[1].length() : 0;
        amount = amount.replace(".", "");
        return amount + "0".repeat(2 - digitsAfterDot);
    }
}
