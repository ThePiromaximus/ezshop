package it.polito.ezshop.test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import it.polito.ezshop.data.*;
import it.polito.ezshop.exceptions.*;


public class TestFR3 {
	
	@SuppressWarnings("unused")
	@Test 
	public void testCreateProductType() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		
		//loggedUser = null -> throws UnauthorizedException
		EZShop ezshop = new EZShop(0);
		try
		{
			Integer id = ezshop.createProductType(null, null, 0, null);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//loggedUser = Cashier -> throws UnauthorizedException
		ezshop = new EZShop(0);
		ezshop.createUser("cashier", "cashier", "Cashier");
		ezshop.login("cashier", "cashier");
		try
		{
			Integer id = ezshop.createProductType(null, null, 0, null);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//productCode = "" -> throws InvalidProductCodeException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			Integer id = ezshop.createProductType("Description", "", 0, null);
			fail("Expected an InvalidProductCodeException to be thrown");
		}catch(InvalidProductCodeException e)
		{
			assertNotNull(e);
		}
		
		//productCode = null -> throws InvalidProductCodeException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			Integer id = ezshop.createProductType("Description", null, 0, null);
			fail("Expected an InvalidProductCodeException to be thrown");
		}catch(InvalidProductCodeException e)
		{
			assertNotNull(e);
		}
		
		//productDescription = null -> throws InvalidProductDescriptionException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			Integer id = ezshop.createProductType(null, null, 0, null);
			fail("Expected an InvalidProductDescriptionException to be thrown");
		}catch(InvalidProductDescriptionException e)
		{
			assertNotNull(e);
		}
		
		//productDescription = ""-> throws InvalidProductDescriptionException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			Integer id = ezshop.createProductType(null, "", 0, null);
			fail("Expected an InvalidProductDescriptionException to be thrown");
		}catch(InvalidProductDescriptionException e)
		{
			assertNotNull(e);
		}
		
		//pricePerUnit = 0 -> throws InvalidpricePerUnitException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			Integer id = ezshop.createProductType("Description","6291041500213" ,0, null);
			fail("Expected an InvalidPricePerUnitException to be thrown");
		}catch(InvalidPricePerUnitException e)
		{
			assertNotNull(e);
		}
		
		//barCode = "6291041500213 "  -> return id > 0
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		Integer id = ezshop.createProductType("product", "6291041500213", 1, "");
		assertTrue(id > 0);
		
		//barCode = "6291041500213 "  -> BarCode already exists return -1
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "6291041500213", 1, "");
		id = ezshop.createProductType("product1", "6291041500213", 1, "");
		assertTrue(id < 0);
		

}
	@SuppressWarnings("unused")
	@Test 
	public void testUpdateProduct() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		
		//loggedUser = null -> throws UnauthorizedException
		EZShop ezshop = new EZShop(0);
		try
		{
			Boolean res = ezshop.updateProduct(null, null, null, 0, null);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//loggedUser = Cashier -> throws UnauthorizedException
		ezshop = new EZShop(0);
		ezshop.createUser("cashier", "cashier", "Cashier");
		ezshop.login("cashier", "cashier");
		try
		{
			Boolean res = ezshop.updateProduct(null, null, null, 0, null);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//productCode = "" -> throws InvalidProductCodeException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			Boolean res = ezshop.updateProduct(null, "Description", "", 0, null);
			fail("Expected an InvalidProductCodeException to be thrown");
		}catch(InvalidProductCodeException e)
		{
			assertNotNull(e);
		}
		
		//productCode = null -> throws InvalidProductCodeException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			Boolean res = ezshop.updateProduct(null, "Description", null, 0, null);
			fail("Expected an InvalidProductCodeException to be thrown");
		}catch(InvalidProductCodeException e)
		{
			assertNotNull(e);
		}
		
		//productDescription = null -> throws InvalidProductDescriptionException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			Boolean res = ezshop.updateProduct(null, null, null, 0, null);
			fail("Expected an InvalidProductDescriptionException to be thrown");
		}catch(InvalidProductDescriptionException e)
		{
			assertNotNull(e);
		}
		
		//productDescription = "" -> throws InvalidProductDescriptionException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			Boolean res = ezshop.updateProduct(null, "", null, 0, null);
			fail("Expected an InvalidProductDescriptionException to be thrown");
		}catch(InvalidProductDescriptionException e)
		{
			assertNotNull(e);
		}
		
		//pricePerUnit = 0 -> throws InvalidpricePerUnitException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			Boolean res = ezshop.updateProduct(null,"Description","6291041500213" ,0, null);
			fail("Expected an InvalidpricePerUnitException to be thrown");
		}catch(InvalidPricePerUnitException e)
		{
			assertNotNull(e);
		}
		
		//id = null -> throws InvalidProductIdException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			Boolean res = ezshop.updateProduct(null, "Description", "6291041500213", 1, null);
			fail("Expected an InvalidProductIdException to be thrown");
		}catch(InvalidProductIdException e)
		{
			assertNotNull(e);
		}
		
		//id = "" -> throws InvalidProductIdException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			Boolean res = ezshop.updateProduct(0, "Description", "6291041500213", 1, null);
			fail("Expected an InvalidProductIdException to be thrown");
		}catch(InvalidProductIdException e)
		{
			assertNotNull(e);
		}
		
		//modify product with id=2 and valid barCode return true 
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		Integer id = ezshop.createProductType("product", "6291041500213", 1, "");
		boolean res = ezshop.updateProduct(id,"product A","0123456789012", 2, "note");
		assertTrue(res);
		
		//modify product with id=3 and barCode that already exists return false 
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		id = ezshop.createProductType("product", "6291041500213", 1, "");
		ezshop.createProductType("product", "0123456789012", 1, "");
		res = ezshop.updateProduct(id,"product A","0123456789012", 2, "note");
		assertFalse(res);
		
		//modify product with id=100 that doesn't exists return false 
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		res = ezshop.updateProduct(100,"product A","0123456789012", 2, "note");
		assertFalse(res);

	}
	
	@SuppressWarnings("unused")
	@Test 
	public void testDeleteProductType() throws InvalidProductIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException {
		
		//loggedUser = null -> throws UnauthorizedException
		EZShop ezshop = new EZShop(0);
		try
		{
			boolean id = ezshop.deleteProductType(null);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//loggedUser = Cashier -> throws UnauthorizedException
		ezshop = new EZShop(0);
		ezshop.createUser("cashier", "cashier", "Cashier");
		ezshop.login("cashier", "cashier");
		try
		{
			boolean id = ezshop.deleteProductType(null);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//id = null -> throws InvalidProductIdException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			boolean id = ezshop.deleteProductType(null);
			fail("Expected an InvalidProductIdException to be thrown");
		}catch(InvalidProductIdException e)
		{
			assertNotNull(e);
		}
		
		//id = "" -> throws InvalidProductIdException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			boolean id = ezshop.deleteProductType(0);
			fail("Expected an InvalidProductIdException to be thrown");
		}catch(InvalidProductIdException e)
		{
			assertNotNull(e);
		}
		
		//id = negative number -> throws InvalidUserIdException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			boolean res = ezshop.deleteProductType(-3);
			fail("Expected an UserIdException to be thrown");
		}catch(InvalidProductIdException e)
		{
			assertNotNull(e);
		}
		
		//id = "100"  -> id isn't present return false
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		boolean res = ezshop.deleteProductType(100);
		assertFalse(res);
		
		//id = "5"  -> id present return true 
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		Integer id = (ezshop.createProductType("product", "6291041500213", 1, ""));
		res = ezshop.deleteProductType(id);
		assertTrue(res);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testGetAllProductTypes() throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		//loggedUser = null -> throws UnauthorizedException
		EZShop ezshop = new EZShop(0);
		try
		{
			List<ProductType> res = ezshop.getAllProductTypes();
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//loggedUser = Cashier -> throws UnauthorizedException
		ezshop = new EZShop(0);
		ezshop.createUser("cashierB", "cashier", "Cashier");
		ezshop.login("cashierB", "cashier");
		try
		{
			List<ProductType> res = ezshop.getAllProductTypes();
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		ezshop.createUser("A", "A", "Administrator");
		ezshop.login("A", "A");
		List<ProductType> res = ezshop.getAllProductTypes();
		assertNotNull(res);
		
	}
	
	@Test 
	public void testGetProductTypeByBarCode() throws InvalidProductCodeException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidPricePerUnitException
	{
		
		//loggedUser = null -> throws UnauthorizedException
		EZShop ezshop = new EZShop(0);
		try
		{
			ezshop.getProductTypeByBarCode(null);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//barCode = null -> throws InvalidProductCodeException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			ezshop.getProductTypeByBarCode(null);
			fail("Expected an InvalidProductCodeException to be thrown");
		}catch(InvalidProductCodeException e)
		{
			assertNotNull(e);
		}
		
		//barCode = "" -> throws InvalidProductCodeException
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		try
		{
			ezshop.getProductTypeByBarCode("");
			fail("Expected an InvalidProductCodeException to be thrown");
		}catch(InvalidProductCodeException e)
		{
			assertNotNull(e);
		}
		
		//barCode = "6291041500213 "  -> return product
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product", "6291041500213", 1, "");
		ProductType p = ezshop.getProductTypeByBarCode("6291041500213");
		assertNotNull(p);
		
		//barCode = "6291041500213 " BUT product isnt present -> return product
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		p = ezshop.getProductTypeByBarCode("6291041500213");
		assertNull(p);
		
	}
	
	@Test
	public void testGetProductTypeByDescription() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException
	{
		//loggedUser = null -> throws UnauthorizedException
		EZShop ezshop = new EZShop(0);
		try
		{
			ezshop.getProductTypesByDescription(null);
			fail("Expected an UnauthorizedException to be thrown");
		}catch(UnauthorizedException e)
		{
			assertNotNull(e);
		}
		
		//description = "product"  -> return list of all products which contains the word "product" in theri description
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product with a name", "6291041500213", 1, "");
		ezshop.createProductType("product with another name", "0000000000000", 2.30, "");
		List<ProductType> l = new ArrayList<ProductType>();
		l = ezshop.getProductTypesByDescription("product");
		assertTrue(l.size()==2);
		
		//description = "I can't find anything"  -> return empty list
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product with a name", "6291041500213", 1, "");
		ezshop.createProductType("product with another name", "0000000000000", 2.30, "");
		l = ezshop.getProductTypesByDescription("I can't find anything");
		assertTrue(l.size()==0);
		
		//description = null  -> return full list
		ezshop = new EZShop(0);
		ezshop.createUser("admin", "admin", "Administrator");
		ezshop.login("admin", "admin");
		ezshop.createProductType("product with a name", "6291041500213", 1, "");
		ezshop.createProductType("product with another name", "0000000000000", 2.30, "");
		l = ezshop.getProductTypesByDescription(null);
		assertTrue(l.size()==2);
		
		
	}
	
}
