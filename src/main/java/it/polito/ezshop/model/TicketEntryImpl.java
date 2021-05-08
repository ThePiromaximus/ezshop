package it.polito.ezshop.model;

public class TicketEntryImpl implements it.polito.ezshop.data.TicketEntry {
	
	private String barCode;
	private String productDescription;
	private Integer amount;
	private double pricePerUnit;
	private double discountRate;
	

	public TicketEntryImpl (String barCode, Integer amount, double pricePerUnit, double discountRate) {
		this.barCode = barCode;
		this.amount = amount;
		this.pricePerUnit = pricePerUnit;
		this.discountRate = discountRate;
	}
	
	public TicketEntryImpl (String barCode, Integer amount) {
		this.barCode = barCode;
		this.amount = amount;
		this.discountRate = 0;
	}
	
	@Override
	public String getBarCode() {
		return this.barCode;
	}

	@Override
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Override
	public String getProductDescription() {
		return this.productDescription;
	}

	@Override
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	@Override
	public int getAmount() {
		return this.amount;
	}

	@Override
	public void setAmount(int amount) {
		this.amount = amount;
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
	public double getDiscountRate() {
		return this.discountRate;
	}

	@Override
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}

}
