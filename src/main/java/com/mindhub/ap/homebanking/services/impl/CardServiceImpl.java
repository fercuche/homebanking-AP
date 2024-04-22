package com.mindhub.ap.homebanking.services.impl;

import com.mindhub.ap.homebanking.models.Card;
import com.mindhub.ap.homebanking.repository.CardRepository;
import com.mindhub.ap.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Card getCardById(Long id) {
        return cardRepository.getCardById(id);
    }

    @Override
    public void deleteCard(Long id) {
        Card card = cardRepository.getCardById(id);
        card.setDeleted(true);
        cardRepository.save(card);
    }
}
