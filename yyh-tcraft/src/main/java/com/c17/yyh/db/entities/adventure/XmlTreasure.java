package com.c17.yyh.db.entities.adventure;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="treasure")
public class XmlTreasure {
	private int id, threshold = 0;

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
