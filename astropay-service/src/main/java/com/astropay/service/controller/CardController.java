package com.astropay.service.controller;

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
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public void addCard(@RequestBody CardDto cardDto) {
		this.cardBean.addCard(cardDto);
	}
}
