package com.athena.bank.ejb.util;

import java.time.Year;
import java.util.Random;

public class AccountNumberGenerator {

    private static final Random random = new Random();

    public static String generateCandidateAccountNumber() {
        String prefix = "ACC";
        String year = String.valueOf(Year.now().getValue());
        int randomNum = 10000 + random.nextInt(90000); // 5-digit
        return prefix + year + randomNum;
    }
}