package com.astropay.api;

/**
 * Created by luis on 22/04/16.
 */
public class CardSaveResponseDto {

    private String cardId;
    private String cardToken;

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
