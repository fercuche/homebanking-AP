package com.mindhub.ap.homebanking.dtos;

import com.mindhub.ap.homebanking.models.Card;
import com.mindhub.ap.homebanking.models.CardColor;
import com.mindhub.ap.homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {

    private Long id;
    private String cardHolder;
    private String number;
    private Integer cvv;
    private CardType type;
    private CardColor color;
    private LocalDate fromDate;
    private LocalDate thruDate;

    public CardDTO(Card card){
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.type = card.getType();
        this.color = card.getColor();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }
}
