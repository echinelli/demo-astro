package com.astropay.repository.dao;

import org.springframework.stereotype.Repository;

import com.astropay.repository.dao.generic.AbstractEntityDao;
import com.astropay.repository.entities.Card;

@Repository
public class CardDao extends AbstractEntityDao<Card, String> {

}
