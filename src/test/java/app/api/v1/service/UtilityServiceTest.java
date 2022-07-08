package app.api.v1.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilityServiceTest {

    public UtilityService utilityService = new UtilityService();

    @Test
    public void testMoneyAmountFromStringToInt(){
        assertEquals(10012321, utilityService.moneyAmountFromStringToInt("100,123.21"));
        assertEquals(10012300, utilityService.moneyAmountFromStringToInt("100,123.00"));
        assertEquals(10012300, utilityService.moneyAmountFromStringToInt("100,123.0"));
        assertEquals(10012300, utilityService.moneyAmountFromStringToInt("100,123"));
        assertEquals(12300, utilityService.moneyAmountFromStringToInt("123"));
    }

    @Test
    public void fromIntAmountToFloatAmount(){
        assertEquals(100123.21f, utilityService.fromIntAmountToFloatAmount(10012321));
        assertEquals(100123.00f, utilityService.fromIntAmountToFloatAmount(10012300));
        assertEquals(100123.0f, utilityService.fromIntAmountToFloatAmount(10012300));
        assertEquals(100123f, utilityService.fromIntAmountToFloatAmount(10012300));
    }


}