package it.polito.ezshop.acceptanceTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import it.polito.ezshop.data.*;
import it.polito.ezshop.exceptions.*;

public class TestFR5 {
	
	@Test
	public void testGetCustomer() throws InvalidCustomerIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidCustomerNameException {
		EZShopInterface EzShop = new EZShop();
		Customer cus1 = null;
		Integer id = 0;
		
		try {
			cus1 = EzShop.getCustomer(10);
			fail();
		} catch (UnauthorizedException e) {
			
		}		
		
		id = EzShop.createUser("Marzio", "password", "ADMIN");
		assertTrue(id > 0);
		EzShop.login("Marzio", "password");
		
		try {
			cus1 = EzShop.getCustomer(null);
			fail();
		} catch (InvalidCustomerIdException e) {
			
		}
		
		try {
			cus1 = EzShop.getCustomer(0);
			fail();
		} catch (InvalidCustomerIdException e) {
			
		}
		
		id = EzShop.defineCustomer("Keanu Reeves");
		assertTrue(id > 0);
		
		try {
			cus1 = EzShop.getCustomer(id);
		} catch (InvalidCustomerIdException e) {
			fail();
		}
		
		return;
	}
	
	@Test
	public void testGetAllCustomers() throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		EZShopInterface EzShop = new EZShop();
		Integer id = 0;
		List<Customer> custList = null;
		
		try {
			custList = EzShop.getAllCustomers();
			fail();
		} catch (UnauthorizedException e) {
			
		}
		
		id = EzShop.createUser("Marzio", "password", "ADMIN");
		assertTrue(id > 0);
		EzShop.login("Marzio", "password");
		
		try {
			custList = EzShop.getAllCustomers();
		} catch (UnauthorizedException e) {
			fail();
		}
		
		return;
	}
}
