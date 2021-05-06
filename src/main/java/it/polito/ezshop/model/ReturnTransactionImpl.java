package it.polito.ezshop.model;

import it.polito.ezshop.data.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReturnTransactionImpl{
	private static Integer PROGRESSIVE_ID = 1;
	
	private SaleTransaction saleTransaction;
	private Integer id;
	private List<TicketEntry> entries = new ArrayList<TicketEntry>();
	private boolean payed = false;
	
	public ReturnTransactionImpl() {
		this.id = PROGRESSIVE_ID;
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
	
	public void setPayed(boolean payed) {
		this.payed = payed;
	}
	
	public boolean payed() {
		return this.payed;
	}
}
