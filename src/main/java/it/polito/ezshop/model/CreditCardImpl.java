package it.polito.ezshop.model;

public class CreditCardImpl implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4376710649374658608L;
	private String code;
	private Double balance;
	
	public CreditCardImpl(String code, Double balance) {
		this.code = code;
		this.balance = balance;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
}
