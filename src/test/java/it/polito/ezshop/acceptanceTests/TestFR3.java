package it.polito.ezshop.acceptanceTests;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;
import it.polito.ezshop.data.*;
import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.model.*;

public class TestFR3 {
	
	@Test 
	public void testGetProductTypeByBarCode() throws InvalidProductCodeException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidPricePerUnitException
	{
		
		//loggedUser = null -> throws UnauthorizedException
		EZShop ezshop = new EZShop();
		try
		{
			ProductType p = ezshop.getProductTypeByBarCode(null);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//barCode = null -> throws InvalidProductCodeException
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			ProductType p = ezshop.getProductTypeByBarCode(null);
			fail("Expected an InvalidProductCodeException to be thrown");
		}catch(InvalidProductCodeException e)
		{
			assertNotNull(e);
		}
		
		//barCode = "" -> throws InvalidProductCodeException
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			ProductType p = ezshop.getProductTypeByBarCode("");
			fail("Expected an InvalidProductCodeException to be thrown");
		}catch(InvalidProductCodeException e)
		{
			assertNotNull(e);
		}
		
		//barCode = "6291041500213 "  -> return product
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "6291041500213", 1, "");
		ProductType p = ezshop.getProductTypeByBarCode("6291041500213");
		assertNotNull(p);
		
		//barCode = "6291041500213 " BUT product isnt present -> return product
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		p = ezshop.getProductTypeByBarCode("6291041500213");
		assertNull(p);
		
	}
	
	@Test
	public void testGetProductTypeByDescription() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException
	{
		//loggedUser = null -> throws UnauthorizedException
		EZShop ezshop = new EZShop();
		try
		{
			List<ProductType> l = new ArrayList<ProductType>();
			l = ezshop.getProductTypesByDescription(null);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//description = "product"  -> return list of all products which contains the word "product" in theri description
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product with a name", "6291041500213", 1, "");
		ezshop.createProductType("product with another name", "0000000000000", 2.30, "");
		List<ProductType> l = new ArrayList<ProductType>();
		l = ezshop.getProductTypesByDescription("product");
		assertTrue(l.size()==2);
		
		//description = "I can't find anything"  -> return empty list
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product with a name", "6291041500213", 1, "");
		ezshop.createProductType("product with another name", "0000000000000", 2.30, "");
		l = ezshop.getProductTypesByDescription("I can't find anything");
		assertTrue(l.size()==0);
		
		//description = null  -> return full list
		ezshop = new EZShop();
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product with a name", "6291041500213", 1, "");
		ezshop.createProductType("product with another name", "0000000000000", 2.30, "");
		l = ezshop.getProductTypesByDescription(null);
		assertTrue(l.size()==2);
		
		
	}
	
}
