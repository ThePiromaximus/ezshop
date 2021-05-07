package it.polito.ezshop.model;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import it.polito.ezshop.data.TicketEntry;

public class SaleTransactionImpl implements it.polito.ezshop.data.SaleTransaction {
	private Integer ticketNumber;
	private List<TicketEntry> entries;
	private double discountRate;
	private double price;
	private LocalDate date = LocalDate.now();
	
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

	public boolean containsProduct(String productCode) {
		for(TicketEntry t : this.entries) {
			if(t.getBarCode().equals(productCode))
				return true;
		}
		return false;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public static Function<? super it.polito.ezshop.data.SaleTransaction, BalanceOperationImpl> mapToBalanceOperation() {
		return (T) -> {
			BalanceOperationImpl retBo = new BalanceOperationImpl();
			SaleTransactionImpl F = (SaleTransactionImpl) T;
			retBo.setDate(F.getDate());
			retBo.setMoney(F.getPrice());
			retBo.setType("PAYED");
			return retBo;
		};
	}
}
