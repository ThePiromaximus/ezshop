package it.polito.ezshop.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import it.polito.ezshop.data.*;
import it.polito.ezshop.exceptions.*;

public class TestFR5 {
	
	@Test
	public void testDefineCustomer() throws InvalidCustomerNameException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException
	{
		EZShopInterface ezshop = new EZShop(0);
		//loggedUser = null -> throw UnauthorizedException
		try 
		{
			ezshop.defineCustomer("Giovanni");
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//customerName = null -> throw InvalidCustomerNameException 
		ezshop = new EZShop(0);
		try 
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.defineCustomer(null);
			fail("Expected an InvalidCustomerNameException to be thrown");
		}catch(InvalidCustomerNameException e)
		{
			assertNotNull(e);
		}
		
		//Insert a customer name which is already present on system -> return -1
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.defineCustomer("Gabriele");
		assertTrue(ezshop.defineCustomer("Gabriele")==-1);
		
		//All is good -> return id of customer
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		assertTrue(ezshop.defineCustomer("Mario")>0);
	}
	
	@Test
	public void testModifyCustomer() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException
	{
		EZShopInterface ezshop = new EZShop(0);
		Integer customerId;
		//loggedUser = null -> throw UnauthorizedException
		try 
		{
			ezshop.modifyCustomer(null, null, null);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//customerName = null -> throw InvalidCustomerNameException 
		ezshop = new EZShop(0);
		try
		{
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.defineCustomer("Mario");
			ezshop.modifyCustomer(1, null, "1111111111");
			fail("Expected an InvalidCustomerNameException to be thrown");
		}catch(InvalidCustomerNameException e)
		{
			assertNotNull(e);
		}
		
		//customerCard = 123456789 (9 digits) -> throw InvalidCustomerCardException 
		ezshop = new EZShop(0);
		try
		{
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			Integer costumerId = ezshop.defineCustomer("Mario");
			ezshop.modifyCustomer(costumerId, "Mario", "123456789");
			fail("Expected an InvalidCustomerCardException to be thrown");
		}catch(InvalidCustomerCardException e)
		{
			assertNotNull(e);
		}
		
		//customerCard = "" -> throw InvalidCustomerCardException 
		ezshop = new EZShop(0);
		try
		{
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		customerId = ezshop.defineCustomer("Mario");
		ezshop.modifyCustomer(customerId, "Mario", "");
		fail("Expected an InvalidCustomerCardException to be thrown");

		}catch(InvalidCustomerCardException e) {
			assertNotNull(e);
		}
		
		//customerCard = null -> throw InvalidCustomerCardException 
		ezshop = new EZShop(0);
		try
		{
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		customerId = ezshop.defineCustomer("Mario");
		ezshop.modifyCustomer(customerId, "Mario", null);
		fail("Expected an InvalidCustomerCardException to be thrown");
		}catch(InvalidCustomerCardException e) {
			assertNotNull(e);
		}
		//customerCard already assigned to another user -> return false
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.defineCustomer("Mario");
		ezshop.modifyCustomer(10, "Mario", "0123456789");
		customerId = ezshop.defineCustomer("Salvatore");
		assertFalse(ezshop.modifyCustomer(customerId, "Salvatore", "0123456789"));
		
		//All is good -> return true
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		assertFalse(ezshop.modifyCustomer(customerId, "Giovanni", "7777777777"));
		customerId = ezshop.defineCustomer("Mario");
		assertTrue(ezshop.modifyCustomer(customerId, "Giovanni", "7777777777"));
		customerId = ezshop.defineCustomer("Luigi");
		assertFalse(ezshop.modifyCustomer(customerId, "Giovanni", "7777777777"));
		
	}
	
	@Test
	public void testDeleteCustomer() throws InvalidCustomerIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidCustomerNameException
	{
		EZShopInterface ezshop = new EZShop(0);
		//loggedUser = null -> throw UnauthorizedException
		try 
		{
			ezshop.deleteCustomer(null);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//loggedUser = null -> throw InvalidCustomerIdException
		ezshop = new EZShop(0);
		try 
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.deleteCustomer(-1);
			fail("Expected an InvalidCustomerIdException to be thrown");
		}catch(InvalidCustomerIdException e)
		{
			assertNotNull(e);
		}
		
		//customerId does not exist -> return false
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		assertFalse(ezshop.deleteCustomer(35171));
		
		//All is good -> return true
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		Integer costumerId = ezshop.defineCustomer("Giovanni");
		assertTrue(ezshop.deleteCustomer(costumerId));
		
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testGetCustomer() throws InvalidCustomerIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidCustomerNameException {
		EZShopInterface EzShop = new EZShop(0);
		Customer cus1 = null;
		Integer id = 0;
		User usr;
		
		try {
			cus1 = EzShop.getCustomer(10);
			fail();
		} catch (UnauthorizedException e) {
			
		}		
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
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
		EZShopInterface EzShop = new EZShop(0);
		Integer id = 0;
		User usr;
		List<Customer> custList = null;
		
		try {
			custList = EzShop.getAllCustomers();
			fail();
		} catch (UnauthorizedException e) {
			
		}
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
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
		EZShopInterface EzShop = new EZShop(0);
		Integer id = 0;
		String card = "";
		User usr;
		
		try {
			card = EzShop.createCard();
			fail();
		} catch (UnauthorizedException e) {
			
		}
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
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
		EZShopInterface EzShop = new EZShop(0);
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
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
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
		} catch(InvalidCustomerCardException e) {
			
		}
		
		try {
			ret = EzShop.attachCardToCustomer("", id);
			fail();
		} catch(InvalidCustomerCardException e) {
			
		}
		
		try {
			ret = EzShop.attachCardToCustomer("12121", id);
			fail();
		} catch(InvalidCustomerCardException e) {
			
		}
		
		ret = EzShop.attachCardToCustomer(card, 150);
		assertTrue(!ret);
		
		ret = EzShop.attachCardToCustomer(card, id);
		assertTrue(ret);
		
		ret = EzShop.attachCardToCustomer(card, id-1);
		assertTrue(!ret);
		
		return;
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testModifyPointsOnCard() throws InvalidCustomerCardException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException, InvalidCustomerNameException, InvalidCustomerIdException, InvalidTransactionIdException, InvalidCreditCardException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidQuantityException {
		EZShopInterface EzShop = new EZShop(0);
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
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
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
		
		ret = EzShop.attachCardToCustomer(card, id);
		assertTrue(ret);
		
		try {
			EzShop.modifyPointsOnCard(card + "a", 10);
			fail("Expected an InvalidCustomerCardException to be thrown");
		} catch (InvalidCustomerCardException e) {
			assertNotNull(e);
		}
		
		ret = EzShop.modifyPointsOnCard(card, -10);
		assertTrue(!ret);
		
		ret = EzShop.modifyPointsOnCard(card, 10);
		assertTrue(ret);
		
		ret = EzShop.modifyPointsOnCard(card, -5);
		assertTrue(ret);
		
		double oldBalance = EzShop.computeBalance();
		Integer productId = EzShop.createProductType("Lenovo Yoga Slim 7", "1845678901001", 1000.0, "Notebook");
		EzShop.updatePosition(productId, "001-abcd-003");		
		EzShop.updateQuantity(productId, 10);
		Integer saleId = EzShop.startSaleTransaction();
		EzShop.addProductToSale(saleId, "1845678901001", 3);
		EzShop.endSaleTransaction(saleId);
		
		assertTrue(EzShop.receiveCreditCardPayment(saleId, "5555555555554444"));
		assertTrue(EzShop.computeBalance() == (oldBalance + 3000.0));
		
		points = EzShop.computePointsForSale(saleId);
		
		ret = EzShop.modifyPointsOnCard(card, points);
		assertTrue(ret);		
		
		return;
	}
}
