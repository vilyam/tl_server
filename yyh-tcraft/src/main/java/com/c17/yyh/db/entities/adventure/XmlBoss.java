package com.c17.yyh.db.entities.adventure;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="boss")
public class XmlBoss {
	private int id;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}
}
