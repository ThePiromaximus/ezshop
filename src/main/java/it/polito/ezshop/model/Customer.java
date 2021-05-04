package it.polito.ezshop.model;

public class Customer implements it.polito.ezshop.data.Customer {
	private String customerName;
	private String customerCard;
	private Integer id;
	private Integer points;
	
	@Override
	public String getCustomerName() {
		return this.customerName;
	}

	@Override
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Override
	public String getCustomerCard() {
		return this.customerCard;
	}

	@Override
	public void setCustomerCard(String customerCard) {
		this.customerCard = customerCard;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getPoints() {
		return this.points;
	}

	@Override
	public void setPoints(Integer points) {
		this.points = points;
	}

}
