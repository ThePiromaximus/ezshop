package it.polito.ezshop.model;

import it.polito.ezshop.data.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReturnTransactionImpl implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5584725631361573214L;

	public static Integer PROGRESSIVE_ID = 1;
	
	private SaleTransaction saleTransaction;
	private Integer id;
	private List<TicketEntry> entries = new ArrayList<TicketEntry>();
	private double price = 0;
	private LocalDate date = LocalDate.now();
	private BalanceOperationImpl balanceOperation = new BalanceOperationImpl();
	
	public ReturnTransactionImpl() {
		this.id = PROGRESSIVE_ID;
		this.balanceOperation.setDate(date);
		this.balanceOperation.setType("RETURN");
		this.balanceOperation.setMoney(0);
		PROGRESSIVE_ID++;
	}

	public SaleTransaction getSaleTransaction() {
		return saleTransaction;
	}

	public void setSaleTransaction(SaleTransaction saleTransaction) {
		this.saleTransaction = saleTransaction;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public void addEntry(TicketEntry t) {
		this.entries.add(t);
	}
	
	public List<TicketEntry> getEntries(){
		return entries;
	}
	
	public Optional<TicketEntry> getEntry(String barCode){
		return entries.stream().filter((TicketEntry t) -> t.getBarCode().equals(barCode)).findFirst();
	}

	public void setPrice(double price) {
		//this.balanceOperation.setMoney(-price);
		this.price = price;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public BalanceOperation getBalanceOperation() {
		return this.balanceOperation;
	}
}
