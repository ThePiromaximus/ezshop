package it.polito.ezshop.model;

import java.util.List;

import it.polito.ezshop.data.TicketEntry;

public class SaleTransaction extends BalanceOperation implements it.polito.ezshop.data.SaleTransaction {
	private Integer ticketNumber;
	private List<TicketEntry> entries;
	private double discountRate;
	private double price;
	
	@Override
	public Integer getTicketNumber() {
		return this.ticketNumber;
	}

	@Override
	public void setTicketNumber(Integer ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	@Override
	public List<TicketEntry> getEntries() {
		return this.entries;
	}

	@Override
	public void setEntries(List<TicketEntry> entries) {
		this.entries = entries;
	}

	@Override
	public double getDiscountRate() {
		return this.discountRate;
	}

	@Override
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}

	@Override
	public double getPrice() {
		return this.price;
	}

	@Override
	public void setPrice(double price) {
		this.price = price;
	}

}
