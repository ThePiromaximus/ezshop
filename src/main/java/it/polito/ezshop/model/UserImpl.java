package it.polito.ezshop.model;

public class UserImpl implements it.polito.ezshop.data.User {
	private static Integer PROGRESSIVE_ID = 1;
	private Integer id;
	private String username;
	private String password;
	private String role;
	
	public UserImpl()
	{
		this.id = PROGRESSIVE_ID;
		PROGRESSIVE_ID++;
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
	public String getUsername() {
		return this.username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getRole() {
		return this.role;
	}

	@Override
	public void setRole(String role) {
		this.role = role;
	}

}
