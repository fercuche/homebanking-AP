package com.mindhub.ap.homebanking.controllers;


import com.mindhub.ap.homebanking.models.Card;
import com.mindhub.ap.homebanking.models.CardColor;
import com.mindhub.ap.homebanking.models.CardType;
import com.mindhub.ap.homebanking.models.Client;
import com.mindhub.ap.homebanking.repository.CardRepository;
import com.mindhub.ap.homebanking.repository.ClientRepository;
import com.mindhub.ap.homebanking.services.CardService;
import com.mindhub.ap.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam String cardType, @RequestParam String cardColor){

        if (cardType.isEmpty()) {
            return new ResponseEntity<>("Card type is required", HttpStatus.FORBIDDEN);
        } else if (cardColor.isEmpty()) {
            return new ResponseEntity<>("Card color is required", HttpStatus.FORBIDDEN);
        }

        CardType type = CardType.valueOf(cardType);
        CardColor color = CardColor.valueOf(cardColor);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientRepository.findByEmail(authentication.getName());

        Set<Card> cards = client.getCards();
        int countCredit = 0;
        int countDebit = 0;

        for (Card card : cards){
            if ("CREDIT".equals(card.getType().toString())){
                countCredit++;
            }else if ("DEBIT".equals(card.getType().toString())){
                countDebit++;
            }
        }

        for (Card card : cards){
            if (type.equals(card.getType()) && color.equals(card.getColor())){
                return new ResponseEntity<>("The card already exists", HttpStatus.FORBIDDEN);
            }
        }

        if ("CREDIT".equals(type.toString()) && countCredit >= 3) {
                return new ResponseEntity<>("Client already have 3 CREDIT cards", HttpStatus.FORBIDDEN);
        } else if ("DEBIT".equals(type.toString()) && countDebit >= 3) {
                return new ResponseEntity<>("Client already have 3 DEBIT cards", HttpStatus.FORBIDDEN);
        }

        Random random = new Random();
        StringBuilder cardNumberBuilder = new StringBuilder();
        String cardNumber;
        do {
            cardNumber = CardUtils.getCardNumber(random, cardNumberBuilder);
        }while (cardRepository.existsByNumber(cardNumber));

        Card card = new Card(cardNumber, CardUtils.getCVV(random), type, color, LocalDate.now(),LocalDate.now().plusYears(5),client, false);
        client.addCard(card);
        cardRepository.save(card);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PatchMapping("/clients/current/cards/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable Long id){
        cardService.deleteCard(id);
        return new ResponseEntity<>("Card successfully deleted", HttpStatus.OK);
    }


}
