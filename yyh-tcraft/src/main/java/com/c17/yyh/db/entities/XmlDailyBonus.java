package com.c17.yyh.db.entities;

import javax.xml.bind.annotation.XmlRootElement;

import com.c17.yyh.models.DailyBonus;

@XmlRootElement(name = "daily_bonus", namespace="")
public class XmlDailyBonus {
	
	private String type;
	private int money;
	private int crystal;
	private int day;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getCrystal() {
		return crystal;
	}

	public void setCrystal(int crystal) {
		this.crystal = crystal;
	}
	
	public DailyBonus getDailyBonus() {
		return new DailyBonus(crystal, money, day, type);
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
}
