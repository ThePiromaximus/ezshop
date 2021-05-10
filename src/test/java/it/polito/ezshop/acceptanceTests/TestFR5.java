package it.polito.ezshop.acceptanceTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
}
