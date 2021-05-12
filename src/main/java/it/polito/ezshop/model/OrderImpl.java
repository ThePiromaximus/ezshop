package it.polito.ezshop.model;

import java.time.LocalDate;

import it.polito.ezshop.data.BalanceOperation;

public class OrderImpl implements it.polito.ezshop.data.Order {
	
	private static Integer PROGRESSIVE_ID = 1;
	
	private Integer balanceId;
	private String productCode;
	private double pricePerUnit;
	private int quantity;
	private String status;
	private Integer orderId;
	private LocalDate date = LocalDate.now();
	private BalanceOperationImpl balanceOperation = new BalanceOperationImpl();
	
	public OrderImpl(String productCode, double pricePerUnit, Integer quantity)
	{
		/*
			Alla creazione l'ordine è sempre in stato issue e senza balanceId perché ancora non
			influise sul bilancio.
			L'ID è progressivo e si autoincrementa alla creazione di ogni ordine. 
		*/
		this.balanceId = null;
		this.orderId = PROGRESSIVE_ID;
		this.status = "ISSUED";
		this.productCode = productCode;
		this.pricePerUnit = pricePerUnit;
		this.quantity = quantity;
		this.balanceOperation.setType("ORDER");
		PROGRESSIVE_ID++;
	}
	
	@Override
	public Integer getBalanceId() {
		return this.balanceId;
	}

	@Override
	public void setBalanceId(Integer balanceId) {
		this.balanceId = balanceId;
	}

	@Override
	public String getProductCode() {
		return this.productCode;
	}

	@Override
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Override
	public double getPricePerUnit() {
		return this.pricePerUnit;
	}

	@Override
	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	@Override
	public int getQuantity() {
		return this.quantity;
	}

	@Override
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String getStatus() {
		return this.status;
	}

	@Override
	public void setStatus(String status) {
		if(status != null && status.equals("PAYED")) {
			this.balanceOperation.setDate(this.getDate());
			this.balanceOperation.setMoney(-this.quantity*this.getPricePerUnit());
		}
		this.status = status;
	}

	@Override
	public Integer getOrderId() {
		return this.orderId;
	}

	@Override
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public LocalDate getDate() {
		return this.date;
	}

	public BalanceOperation getBalanceOperation() {
		return this.balanceOperation;
	}

}
