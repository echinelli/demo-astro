package com.astropay.repository.entities.generic;

import java.io.Serializable;

public abstract class AbstractEntity<ID extends Serializable>{

	public abstract ID getId();
	public abstract void setId(ID id);

}
