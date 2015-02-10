package com.c17.yyh.db.entities.adventure;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="secret")
public class XmlSecret {
	private int stars, monets = 0;

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public int getMonets() {
		return monets;
	}

	public void setMonets(int monets) {
		this.monets = monets;
	}

}
