package it.polito.ezshop.acceptanceTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import it.polito.ezshop.data.*;
import it.polito.ezshop.exceptions.*;

public class TestFR5 {
	
	@SuppressWarnings("unused")
	@Test
	public void testGetCustomer() throws InvalidCustomerIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidCustomerNameException {
		EZShopInterface EzShop = new EZShop();
		Customer cus1 = null;
		Integer id = 0;
		User usr;
		
		try {
			cus1 = EzShop.getCustomer(10);
			fail();
		} catch (UnauthorizedException e) {
			
		}		
		
		id = EzShop.createUser("Marzio", "password", "ADMIN");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
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
	
	@SuppressWarnings("unused")
	@Test
	public void testGetAllCustomers() throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		EZShopInterface EzShop = new EZShop();
		Integer id = 0;
		User usr;
		List<Customer> custList = null;
		
		try {
			custList = EzShop.getAllCustomers();
			fail();
		} catch (UnauthorizedException e) {
			
		}
		
		id = EzShop.createUser("Marzio", "password", "ADMIN");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
		try {
			custList = EzShop.getAllCustomers();
		} catch (UnauthorizedException e) {
			fail();
		}
		
		return;
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testCreateCard() throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		EZShopInterface EzShop = new EZShop();
		Integer id = 0;
		String card = "";
		User usr;
		
		try {
			card = EzShop.createCard();
			fail();
		} catch (UnauthorizedException e) {
			
		}
		
		id = EzShop.createUser("Marzio", "password", "ADMIN");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
		try {
			card = EzShop.createCard();
		} catch (UnauthorizedException e) {
			fail();
		}
		assertTrue(!card.equals(""));
		
		return;
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testAttachCardToCustomer() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidCustomerNameException{
		EZShopInterface EzShop = new EZShop();
		Integer id = 0;
		String card = "";
		User usr;
		Customer cus1 = null;
		boolean ret = false;
		
		try {
			EzShop.attachCardToCustomer(card, id);
			fail();
		} catch (UnauthorizedException e) {

		}
		
		id = EzShop.createUser("Marzio", "password", "ADMIN");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
		card = EzShop.createCard();
		assertTrue(!card.equals(""));
		id = EzShop.defineCustomer("Dave Grohl");
		assertTrue(id > 0);
		id = EzShop.defineCustomer("Keanu Reeves");
		assertTrue(id > 0);
		
		try {
			ret = EzShop.attachCardToCustomer(card, null);
			fail();
		} catch(InvalidCustomerIdException e) {
			
		}
		
		try {
			ret = EzShop.attachCardToCustomer(card, 0);
			fail();
		} catch(InvalidCustomerIdException e) {
			
		}
		
		try {
			ret = EzShop.attachCardToCustomer(null, id);
			fail();
		} catch(InvalidCustomerIdException e) {
			
		}
		
		try {
			ret = EzShop.attachCardToCustomer("", id);
			fail();
		} catch(InvalidCustomerIdException e) {
			
		}

		ret = EzShop.attachCardToCustomer(card, id-1);
		assertTrue(!ret);
		
		ret = EzShop.attachCardToCustomer(card, 150);
		assertTrue(!ret);
		
		ret = EzShop.attachCardToCustomer(card, id);
		assertTrue(ret);
		
		return;
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testModifyPointsOnCard() throws InvalidCustomerCardException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException, InvalidCustomerNameException {
		EZShopInterface EzShop = new EZShop();
		Integer id = 0, points = 0;
		String card = "";
		User usr;
		Customer cus1 = null;
		boolean ret = false;
		
		try {
			EzShop.modifyPointsOnCard(card, id);
			fail();
		} catch (UnauthorizedException e) {

		}
		
		id = EzShop.createUser("Marzio", "password", "ADMIN");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
		card = EzShop.createCard();
		assertTrue(!card.equals(""));
		id = EzShop.defineCustomer("Keanu Reeves");
		assertTrue(id > 0);
		
		try {
			ret = EzShop.modifyPointsOnCard(null, 10);
			fail();
		} catch (InvalidCustomerCardException e) {

		}
		
		try {
			ret = EzShop.modifyPointsOnCard("", 10);
			fail();
		} catch (InvalidCustomerCardException e) {

		}
		
		ret = EzShop.modifyPointsOnCard(card + "a", 10);
		assertTrue(!ret);
		
		ret = EzShop.modifyPointsOnCard(card, -10);
		assertTrue(!ret);
		
		ret = EzShop.modifyPointsOnCard(card, 10);
		assertTrue(ret);
		
		ret = EzShop.modifyPointsOnCard(card, -5);
		assertTrue(ret);
		
		return;
	}
}
