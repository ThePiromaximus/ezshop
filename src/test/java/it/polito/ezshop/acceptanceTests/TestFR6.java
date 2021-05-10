package it.polito.ezshop.acceptanceTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import it.polito.ezshop.data.*;
import it.polito.ezshop.exceptions.*;

public class TestFR6 {
	
	@Test
	public void testStartSaleTransaction() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException {
		EZShopInterface EzShop = new EZShop();
		Integer id = 0;
		User usr;
		
		try {
			id = EzShop.startSaleTransaction();
			fail();
		} catch (UnauthorizedException e) {
			
		}
		
		id = EzShop.createUser("Marzio", "password", "ADMIN");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
		id = EzShop.startSaleTransaction();
		assertTrue(id > 0);
		
		return;
	}
	
	@Test
	public void testAddProductToSale() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidProductIdException {
		EZShopInterface EzShop = new EZShop();
		Integer id = 0;
		String prodCode = null;
		User usr;
		boolean ret = false;
		
		try {
			ret = EzShop.addProductToSale(id, "122474487139", 1);
			fail();
		} catch (UnauthorizedException e) {
			
		}
		
		id = EzShop.createUser("Marzio", "password", "ADMIN");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
		id = EzShop.createProductType("NieR Piano Collections", "122474487139", 10, "Music CD");
		assertTrue(id != -1);
		
		ret = EzShop.updateQuantity(id, 2);
		assertTrue(ret);
		
		id = EzShop.startSaleTransaction();
		assertTrue(id > 0);
		
		try {
			ret = EzShop.addProductToSale(null, "122474487139", 1);
			fail();
		} catch (InvalidTransactionIdException e) {
			
		}
		
		try {
			ret = EzShop.addProductToSale(-1, "122474487139", 1);
			fail();
		} catch (InvalidTransactionIdException e) {
			
		}
		
		try {
			ret = EzShop.addProductToSale(id, null, 1);
			fail();
		} catch (InvalidProductCodeException e) {
			
		}
		
		try {
			ret = EzShop.addProductToSale(null, "", 1);
			fail();
		} catch (InvalidProductCodeException e) {
			
		}
		
		try {
			ret = EzShop.addProductToSale(null, "notValidBarcode", 1);
			fail();
		} catch (InvalidProductCodeException e) {
			
		}
		
		try {
			ret = EzShop.addProductToSale(id, "122474487139", -2);
			fail();
		} catch (InvalidQuantityException e) {
			
		}
		
		ret = EzShop.addProductToSale(id, "000000000000", 4);
		assertTrue(!ret);
		
		ret = EzShop.addProductToSale(id, "122474487139", 4);
		assertTrue(!ret);
		
		ret = EzShop.addProductToSale(id+1, "122474487139", 4);
		assertTrue(!ret);
		
		ret = EzShop.addProductToSale(id, "122474487139", 1);
		assertTrue(ret);
		
		return;
	}
}
