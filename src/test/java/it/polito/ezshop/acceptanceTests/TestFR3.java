package it.polito.ezshop.acceptanceTests;
import static org.junit.Assert.*;

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

}
