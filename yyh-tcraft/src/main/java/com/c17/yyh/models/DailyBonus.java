package com.c17.yyh.models;

public class DailyBonus {
	public DailyBonus(int crystal, int money, int day, String type) {
		super();
		this.crystal = crystal;
		this.money = money;
		this.day = day;
		this.type = type;
	}
	
	public DailyBonus() {}
	
	private int crystal;
	private int money;
	private int day;

	private String type;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCrystal() {
		return crystal;
	}
	public void setCrystal(int crystal) {
		this.crystal = crystal;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
}
