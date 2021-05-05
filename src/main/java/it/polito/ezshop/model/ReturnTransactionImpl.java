package it.polito.ezshop.model;

import it.polito.ezshop.data.SaleTransaction;

public class ReturnTransactionImpl{
	private static Integer PROGRESSIVE_ID = 0;
	
	private SaleTransaction saleTransaction;
	private Integer id;
	
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

	
}
