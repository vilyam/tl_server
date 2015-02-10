package com.c17.yyh.type;

import javax.xml.bind.annotation.XmlEnumValue;

public enum GameType {
	@XmlEnumValue("default")
	DEFAULT, 
	@XmlEnumValue("boss")
	BOSS, 
	@XmlEnumValue("pet")
	PET,
	@XmlEnumValue("secret")
	SECRET;
}
