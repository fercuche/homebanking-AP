package com.mindhub.ap.homebanking.services;

import com.mindhub.ap.homebanking.models.Card;

public interface CardService {

    Card getCardById(Long id);

    void deleteCard(Long id);

}
