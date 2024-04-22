package com.mindhub.ap.homebanking.utils;

import java.util.Random;

public final class CardUtils {

    private CardUtils() {
    }

    public static String getCardNumber(Random random, StringBuilder cardNumberBuilder) {
        String cardNumber;
        for (int i = 0; i < 4; i++) {
            if (i > 0) {
                cardNumberBuilder.append("-");
            }
            int section = random.nextInt(9000-1000+1) + 1000;
            cardNumberBuilder.append(section);
        }
        cardNumber = cardNumberBuilder.toString();
        return cardNumber;
    }

    public static int getCVV(Random random) {
        return random.nextInt(999-100+1)+100;
    }
}
