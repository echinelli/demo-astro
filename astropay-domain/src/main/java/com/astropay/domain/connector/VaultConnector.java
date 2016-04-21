package com.astropay.domain.connector;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.astropay.api.CardDto;

@Component
public class VaultConnector {

	public String tokenizeCard(CardDto cardDto) {
		// connector stuff

		return UUID.randomUUID().toString();
	}
}
