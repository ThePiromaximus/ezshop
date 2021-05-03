package it.polito.ezshop.model;

import java.time.LocalDate;

public class BalanceOperation implements it.polito.ezshop.data.BalanceOperation {
	private int balanceId;
	private LocalDate date;
	private double money;
	private String type;
	
	@Override
	public int getBalanceId() {
		// TODO Auto-generated method stub
		return this.balanceId;
	}

	@Override
	public void setBalanceId(int balanceId) {
		this.balanceId = balanceId;
	}

	@Override
	public LocalDate getDate() {
		return this.date;
	}

	@Override
	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public double getMoney() {
		return this.money;
	}

	@Override
	public void setMoney(double money) {
		this.money = money;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

}
