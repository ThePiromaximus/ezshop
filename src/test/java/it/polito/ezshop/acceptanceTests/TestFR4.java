package it.polito.ezshop.acceptanceTests;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;
import it.polito.ezshop.data.*;
import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.model.*;

public class TestFR4 {

	@Test
	public void testUpdateQuantity() throws InvalidProductIdException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidLocationException
	{
		//loggedUser = Cashier -> throws UnauthorizedException
		EZShop ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Cashier");
			ezshop.login("admin", "admin");
			ezshop.updateQuantity(3, 2);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//productId = null -> throws InvalidProductIdException
		ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.createProductType("product", "6291041500213", 1, "");
			ezshop.updateQuantity(null, 2);
			fail("Expected an InvalidProductIdException to be thrown");
		}catch(InvalidProductIdException e)
		{
			assertNotNull(e);
		}
		
		//final quantity is negative -> return false
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "6291041500213", 1, "");
		ezshop.updatePosition(2, "1-a-1");
		assertFalse(ezshop.updateQuantity(1, -2));
		
		//product is not present -> return false
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "6291041500213", 1, "");
		ezshop.updatePosition(3, "1-a-1");
		assertFalse(ezshop.updateQuantity(1000, -2));
		
		//product has not a location -> return false
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "6291041500213", 1, "");
		assertFalse(ezshop.updateQuantity(4, 1));
		
		//all is good -> return true
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		System.out.println(ezshop.createProductType("product", "6291041500213", 1, ""));
		System.out.println(ezshop.updatePosition(25, "1-a-1"));
		assertTrue(ezshop.updateQuantity(25, 1));
		
		//location = "notWorkingLocation" -> throws InvalidLocationException
		ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.createProductType("product", "6291041500213", 1, "");
			ezshop.updatePosition(6, "notWorkingLocation");
			ezshop.updateQuantity(6, 2);
			fail("Expected an InvalidLocationException to be thrown");
		}catch(InvalidLocationException e)
		{
			assertNotNull(e);
		}
		
	}
	
	@Test
	public void testUpdatePosition() throws InvalidProductIdException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidLocationException
	{
		//loggedUser = Cashier -> throws UnauthorizedException
		EZShop ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Cashier");
			ezshop.login("admin", "admin");
			ezshop.createProductType("product", "6291041500213", 1, "");
			ezshop.updatePosition(1, "1-a-1");
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//productId = null -> throws InvalidProductIdException
		ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.createProductType("product", "6291041500213", 1, "");
			ezshop.updatePosition(null, "1-a-1");
			fail("Expected an InvalidProductIdException to be thrown");
		}catch(InvalidProductIdException e)
		{
			assertNotNull(e);
		}
		
		//location = "notWorkingLocation" -> throws InvalidLocationException
		ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.createProductType("product", "6291041500213", 1, "");
			ezshop.updatePosition(3, "notWorkingLocation");
			fail("Expected an InvalidLocationException to be thrown");
		}catch(InvalidLocationException e)
		{
			assertNotNull(e);
		}
		
		//location is already assigned to another product -> return false
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "6291041500213", 1, "");
		ezshop.updatePosition(4, "1-a-1");
		ezshop.createProductType("another product", "0000000000000", 1, "");
		assertFalse(ezshop.updatePosition(5, "1-a-1"));
		
		//product does not exist -> return false
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "6291041500213", 1, "");
		assertFalse(ezshop.updatePosition(100, "1-a-1"));
		
	}
	
	@Test
	public void testIssueOrder() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, InvalidProductDescriptionException, UnauthorizedException
	{
		//loggedUser = Cashier -> throws UnauthorizedException
		EZShop ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Cashier");
			ezshop.login("admin", "admin");
			ezshop.issueOrder(null, 20, 1);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//quantity = -3 -> throws InvalidQuantityException
		ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.createProductType("product", "6291041500213", 1, "");
			ezshop.issueOrder("6291041500213", -3, 1);
			fail("Expected an InvalidQuantityException to be thrown");
		}catch(InvalidQuantityException e)
		{
			assertNotNull(e);
		}
		
		//pricePerUnit = -3 -> throws InvalidPricePerUnitException
		ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.createProductType("product", "6291041500213", 1, "");
			ezshop.issueOrder("6291041500213", 20, -3);
			fail("Expected an InvalidPricePerUnitException to be thrown");
		}catch(InvalidPricePerUnitException e)
		{
			assertNotNull(e);
		}
		
		//productCode = "" -> throws InvalidProductCodeException
		ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.createProductType("product", "6291041500213", 1, "");
			ezshop.issueOrder("", 20, 1);
			fail("Expected an InvalidProductCodeException to be thrown");
		}catch(InvalidProductCodeException e)
		{
			assertNotNull(e);
		}
		
		//the product does not exists -> return -1
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "6291041500213", 1, "");
		Integer i = ezshop.issueOrder("0000000000000", 20, 1);
		assertTrue(i==-1);
		
		//All is good -> return id of the product
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "6291041500213", 1, "");
		Integer j = ezshop.issueOrder("6291041500213", 20, 1);
		assertTrue(j>0);
	}
	
	@Test
	public void testPayOrderFor() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, InvalidProductDescriptionException, UnauthorizedException
	{
		//loggedUser = Cashier -> throws UnauthorizedException
		EZShop ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Cashier");
			ezshop.login("admin", "admin");
			ezshop.payOrderFor(null, 20, 1);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//quantity = -3 -> throws InvalidQuantityException
		ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.createProductType("product", "6291041500213", 1, "");
			ezshop.payOrderFor("6291041500213", -3, 1);
			fail("Expected an InvalidQuantityException to be thrown");
		}catch(InvalidQuantityException e)
		{
			assertNotNull(e);
		}
		
		//pricePerUnit = -3 -> throws InvalidPricePerUnitException
		ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.createProductType("product", "6291041500213", 1, "");
			ezshop.payOrderFor("6291041500213", 20, -3);
			fail("Expected an InvalidPricePerUnitException to be thrown");
		}catch(InvalidPricePerUnitException e)
		{
			assertNotNull(e);
		}
		
		//productCode = "" -> throws InvalidProductCodeException
		ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.createProductType("product", "6291041500213", 1, "");
			ezshop.payOrderFor("", 20, 1);
			fail("Expected an InvalidProductCodeException to be thrown");
		}catch(InvalidProductCodeException e)
		{
			assertNotNull(e);
		}
		
		//the product does not exists -> return -1
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "6291041500213", 1, "");
		Integer i = ezshop.payOrderFor("0000000000000", 20, 1);
		assertTrue(i==-1);
		
		//All is good -> return id of the product
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.recordBalanceUpdate(100);
		ezshop.createProductType("product", "6291041500213", 1, "");
		Integer j = ezshop.payOrderFor("6291041500213", 20, 1);
		assertTrue(j>0);
		
		//Balance is not enough to pay the order -> return -1
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "6291041500213", 1, "");
		assertTrue(ezshop.payOrderFor("6291041500213", 20, 1) == -1);
	}
	
	@Test
	public void testPayOrder() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, InvalidProductDescriptionException, UnauthorizedException, InvalidOrderIdException
	{
		//loggedUser = Cashier -> throws UnauthorizedException
		EZShop ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Cashier");
			ezshop.login("admin", "admin");
			ezshop.payOrder(1);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//orderId = null -> throws InvalidOrderIdException
		ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.payOrder(null);
			fail("Expected an InvalidOrderIdException to be thrown");
		}catch(InvalidOrderIdException e)
		{
			assertNotNull(e);
		}
		
		//The order does not exist -> return false
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		assertFalse(ezshop.payOrder(1));
		
		//The order is not in ISSUED state -> return false
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "6291041500213", 1, "");
		ezshop.recordBalanceUpdate(10);
		ezshop.issueOrder("6291041500213", 1, 1);
		ezshop.payOrder(3);
		assertFalse(ezshop.payOrder(3));
		
		//All is good -> return true
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "6291041500213", 1, "");
		ezshop.recordBalanceUpdate(10);
		ezshop.issueOrder("6291041500213", 1, 1);
		assertTrue(ezshop.payOrder(8));
	}
	
	@Test
	public void testRecordOrderArrival() throws InvalidOrderIdException, InvalidLocationException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException, InvalidProductIdException 
	{
		//loggedUser = Cashier -> throws UnauthorizedException
		EZShop ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Cashier");
			ezshop.login("admin", "admin");
			ezshop.recordOrderArrival(1);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//location = null -> throws InvalidLocationException
		ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.createProductType("product", "000000000000", 1, "");
			ezshop.recordBalanceUpdate(10);
			ezshop.payOrderFor("000000000000", 1, 1);
			ezshop.recordOrderArrival(2);
			fail("Expected an InvalidLocationException to be thrown");
		}catch(InvalidLocationException e)
		{
			assertNotNull(e);
		}
		
		//orderId = 0 -> throws InvalidOrderIdException
		ezshop = new EZShop();
		try
		{
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			ezshop.recordOrderArrival(0);
			fail("Expected an InvalidOrderIdException to be thrown");
		}catch(InvalidOrderIdException e)
		{
			assertNotNull(e);
		}
		
		//order does not exist -> return false
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		assertFalse(ezshop.recordOrderArrival(67000));
		
		//All is good -> return true
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "000000000000", 1, "");
		ezshop.updatePosition(34, "1-a-1");
		ezshop.recordBalanceUpdate(10);
		System.out.println(ezshop.payOrderFor("000000000000", 1, 1));
		assertTrue(ezshop.recordOrderArrival(3));	
		
		
	}
	
	@Test
	public void testGetAllOrders() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, InvalidProductDescriptionException
	{
		//loggedUser = null -> throw UnauthorizedException
		EZShop ezshop = new EZShop();
		try 
		{
			ezshop.getAllOrders();
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//All is good -> return List of all orders
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "000000000000", 1, "");
		ezshop.createProductType("product", "6291041500213", 13, "");
		ezshop.recordBalanceUpdate(10);
		ezshop.issueOrder("000000000000", 1, 1);
		ezshop.issueOrder("6291041500213", 20, 1);
		List<Order> list = new ArrayList<Order>();
		assertNotNull(list);
		
	}
	
}
