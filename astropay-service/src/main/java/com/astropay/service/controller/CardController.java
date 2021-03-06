package com.astropay.service.controller;

import com.astropay.api.CardSaveResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.astropay.api.CardDto;
import com.astropay.domain.service.CardBean;

@RequestMapping("/cards")
@Controller
public class CardController {

	@Autowired
	private CardBean cardBean;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public CardSaveResponseDto addCard(@RequestBody CardDto cardDto) {

		CardSaveResponseDto crDto = this.cardBean.addCard(cardDto);
		return crDto;
	}
}
