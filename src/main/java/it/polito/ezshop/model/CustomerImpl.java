package it.polito.ezshop.model;

public class CustomerImpl implements it.polito.ezshop.data.Customer {
	private static int PROGRESSIVE_ID = 1;
	private static int PROGRESSIVE_CARD_ID = 0;
	
	private String customerName;
	private String customerCard;
	private Integer id;
	private Integer points;
	
	public CustomerImpl(String customerName)
	{
		this.id = PROGRESSIVE_ID;
		this.customerName = customerName;
		this.customerCard = null;
		this.points = 0;
		
		PROGRESSIVE_ID++;
	}
	
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
	
	public static int getProgressiveCard() {
		return PROGRESSIVE_CARD_ID++;
	}

}
