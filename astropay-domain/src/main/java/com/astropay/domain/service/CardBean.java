package com.astropay.domain.service;

import com.astropay.api.CardDto;
import com.astropay.api.CardSaveResponseDto;
import com.astropay.domain.connector.VaultConnector;
import com.astropay.repository.dao.CardDao;
import com.astropay.repository.entities.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;


@Service
public class CardBean {

    private static final String CARD_ID_PREFIX = "Q-";
    private static final Logger LOGGER = LoggerFactory.getLogger(CardBean.class);

    @Autowired
    private VaultConnector vaultConnector;
    @Autowired
    private CardDao cardDao;


    public CardSaveResponseDto addCard(CardDto cardDto) {

        String cardToken = this.vaultConnector.tokenizeCard(cardDto);

        Card entity = this.toCardEntity(cardDto, cardToken);

        entity.setId(generateCardId());

        this.cardDao.save(entity);
        LOGGER.info("Estoy por contruir la respuesta");

        return buildAddCardResponse(entity);

    }

    private Card toCardEntity(CardDto dto, String token) {

        Card q = new Card();

        q.setCardToken(token);
        q.setCpf(dto.getxCpf());
        q.setEmail(dto.getxEmail());
        q.setName(dto.getxName());
        return q;
    }

    private static String generateCardId() {
        return CARD_ID_PREFIX + UUID.randomUUID().toString();
    }

    private CardSaveResponseDto buildAddCardResponse(Card entity) {
        CardSaveResponseDto crDto = new CardSaveResponseDto();
        crDto.setCardId(entity.getId());
        crDto.setCardToken(entity.getCardToken());
        return crDto;
    }
}
