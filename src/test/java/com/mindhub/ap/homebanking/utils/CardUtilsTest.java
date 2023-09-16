package com.mindhub.ap.homebanking.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class CardUtilsTest {

    Random random = new Random();
    StringBuilder cardNumberBuilder = new StringBuilder();

    @Test
    void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber(random, cardNumberBuilder);
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    void cardNumberLength(){
        String cardNumber = CardUtils.getCardNumber(random, cardNumberBuilder);
        assertThat(cardNumber, hasLength(19));
    }

    @Test
    void cvvLength(){
        String cvv = String.valueOf(CardUtils.getCVV(random));
        assertThat(cvv, hasLength(3));
    }

    @Test
    void cvvHigherThanZero(){
        int cvv = CardUtils.getCVV(random);
        assertThat(cvv, greaterThan(0));
    }

    @Test
    void cvvNotNull(){
        String cvv = String.valueOf(CardUtils.getCVV(random));
        assertThat(cvv, is(not(blankOrNullString())));
    }
}