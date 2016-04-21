package com.astropay.repository.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.astropay.repository.entities.generic.AbstractEntity;

@Entity
@Table(name = "card")
public class Card extends AbstractEntity<String> {

	@Id
	private String id;
	private String name;
	private String email;
	private String cpf;
	private String cardToken;

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCardToken() {
		return this.cardToken;
	}

	public void setCardToken(String cardToken) {
		this.cardToken = cardToken;
	}
}
