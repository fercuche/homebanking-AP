package com.mindhub.ap.homebanking.controllers;


import com.mindhub.ap.homebanking.models.Card;
import com.mindhub.ap.homebanking.models.CardColor;
import com.mindhub.ap.homebanking.models.CardType;
import com.mindhub.ap.homebanking.models.Client;
import com.mindhub.ap.homebanking.repository.CardRepository;
import com.mindhub.ap.homebanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam String cardType, @RequestParam String cardColor){

        CardType type = CardType.valueOf(cardType);
        CardColor color = CardColor.valueOf(cardColor);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientRepository.findByEmail(authentication.getName());

        Random random = new Random();
        StringBuilder cardNumberBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (i > 0) {
                cardNumberBuilder.append("-");
            }
            int section = random.nextInt(9000-1000+1) + 1000;
            cardNumberBuilder.append(section);
        }
        String cardNumber = cardNumberBuilder.toString();

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


        Card card = new Card(cardNumber,random.nextInt(999-100+1)+100, type,
                color, LocalDate.now(),LocalDate.now().plusYears(5),client);
        client.addCard(card);
        cardRepository.save(card);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
