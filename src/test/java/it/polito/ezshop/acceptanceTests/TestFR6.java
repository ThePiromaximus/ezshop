package it.polito.ezshop.acceptanceTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
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
		User usr;
		boolean ret = false;
		
		try {
			ret = EzShop.addProductToSale(id, "122474487139", 1);
			fail();
		} catch (UnauthorizedException e) {
			
		}
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
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
	
	@Test
	public void testDeleteProductFromSale() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidTransactionIdException, InvalidQuantityException {
		EZShopInterface EzShop = new EZShop();
		Integer id = 0;
		User usr;
		boolean ret = false;
		
		try {
			ret = EzShop.deleteProductFromSale(id, "", 1);
			fail();
		} catch (UnauthorizedException e) {
			
		}
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
		id = EzShop.createProductType("NieR Piano Collections", "122474487139", 10, "Music CD");
		assertTrue(id != -1);
		
		ret = EzShop.updateQuantity(id, 2);
		assertTrue(ret);
		
		id = EzShop.startSaleTransaction();
		assertTrue(id > 0);
		
		ret = EzShop.addProductToSale(id, "122474487139", 1);
		assertTrue(ret);

		try {
			ret = EzShop.deleteProductFromSale(null, "122474487139", 1);
			fail();
		} catch (InvalidTransactionIdException e) {
			
		}
		
		try {
			ret = EzShop.deleteProductFromSale(0, "122474487139", 1);
			fail();
		} catch (InvalidTransactionIdException e) {
			
		}
		
		try {
			ret = EzShop.deleteProductFromSale(id, null, 1);
			fail();
		} catch (InvalidProductCodeException e) {
			
		}
		
		try {
			ret = EzShop.deleteProductFromSale(id, "", 1);
			fail();
		} catch (InvalidProductCodeException e) {
			
		}
		
		try {
			ret = EzShop.deleteProductFromSale(id, "notValidBarcode", 1);
			fail();
		} catch (InvalidProductCodeException e) {
			
		}
		
		try {
			ret = EzShop.deleteProductFromSale(id, "122474487139", -2);
			fail();
		} catch (InvalidQuantityException e) {
			
		}
		
		ret = EzShop.deleteProductFromSale(id, "000000000000", 1);
		assertTrue(!ret);
		
		ret = EzShop.deleteProductFromSale(id+1, "122474487139", 4);
		assertTrue(!ret);
		
		ret = EzShop.deleteProductFromSale(id, "122474487139", 5);
		assertTrue(ret);
		
		ret = EzShop.deleteProductFromSale(id, "122474487139", 1);
		assertTrue(ret);
		
		return;
	}
	
	@Test
	public void testApplyDiscountRateToProduct() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidTransactionIdException, InvalidQuantityException, InvalidDiscountRateException {
		EZShopInterface EzShop = new EZShop();
		Integer id = 0;
		User usr;
		boolean ret = false;
		
		try {
			ret = EzShop.applyDiscountRateToProduct(id, "122474487139", 0.1);
			fail();
		} catch (UnauthorizedException e) {
			
		}
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
		id = EzShop.createProductType("TwentyOnePilots - Blurryface", "122548187895", 10, "Music CD");
		assertTrue(id != -1);
		
		ret = EzShop.updateQuantity(id, 2);
		assertTrue(ret);
		
		id = EzShop.createProductType("NieR Piano Collections", "122474487139", 10, "Music CD");
		assertTrue(id != -1);
		
		ret = EzShop.updateQuantity(id, 2);
		assertTrue(ret);
		
		id = EzShop.startSaleTransaction();
		assertTrue(id > 0);
		
		ret = EzShop.addProductToSale(id, "122474487139", 1);
		assertTrue(ret);
		
		try {
			ret = EzShop.applyDiscountRateToProduct(null, "122474487139", 0.1);
			fail();
		} catch (InvalidTransactionIdException e) {
			
		}
		
		try {
			ret = EzShop.applyDiscountRateToProduct(0, "122474487139", 0.1);
			fail();
		} catch (InvalidTransactionIdException e) {
			
		}
		
		try {
			ret = EzShop.applyDiscountRateToProduct(id, null, 0.1);
			fail();
		} catch (InvalidProductCodeException e) {
			
		}
		
		try {
			ret = EzShop.applyDiscountRateToProduct(id, "", 0.1);
			fail();
		} catch (InvalidProductCodeException e) {
			
		}
		
		try {
			ret = EzShop.applyDiscountRateToProduct(id, "notValidBarcode", 0.1);
			fail();
		} catch (InvalidProductCodeException e) {
			
		}
		
		try {
			ret = EzShop.applyDiscountRateToProduct(id, "122474487139", -0.1);
			fail();
		} catch (InvalidDiscountRateException e) {
			
		}
		
		try {
			ret = EzShop.applyDiscountRateToProduct(id, "122474487139", 1.1);
			fail();
		} catch (InvalidDiscountRateException e) {
			
		}
		
		ret = EzShop.applyDiscountRateToProduct(id, "000000000000", 0.1);
		assertTrue(!ret);
		
		ret = EzShop.applyDiscountRateToProduct(id+1, "122474487139", 0.1);
		assertTrue(!ret);
		
		ret = EzShop.applyDiscountRateToProduct(id, "122548187895", 0.1);
		assertTrue(!ret);
		
		ret = EzShop.applyDiscountRateToProduct(id, "122474487139", 0.1);
		assertTrue(ret);
		
		return;
	}
	
	@Test
	public void testApplyDiscountRateToSale() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException {
		EZShopInterface EzShop = new EZShop();
		Integer id = 0;
		User usr;
		boolean ret = false;
		
		try {
			ret = EzShop.applyDiscountRateToSale(id, 0.1);
			fail();
		} catch (UnauthorizedException e) {
			
		}
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
		id = EzShop.startSaleTransaction();
		assertTrue(id > 0);
		
		try {
			ret = EzShop.applyDiscountRateToSale(null, 0.1);
			fail();
		} catch (InvalidTransactionIdException e) {
			
		}
		
		try {
			ret = EzShop.applyDiscountRateToSale(0, 0.1);
			fail();
		} catch (InvalidTransactionIdException e) {
			
		}
		
		try {
			ret = EzShop.applyDiscountRateToSale(id, -0.1);
			fail();
		} catch (InvalidDiscountRateException e) {
			
		}
		
		try {
			ret = EzShop.applyDiscountRateToSale(id, 1.1);
			fail();
		} catch (InvalidDiscountRateException e) {
			
		}
		
		ret = EzShop.applyDiscountRateToSale(id+1, 0.1);
		assertTrue(!ret);
		
		ret = EzShop.applyDiscountRateToSale(id, 0.1);
		assertTrue(ret);
		
		return;
	}
	
	@Test
	public void testComputePointsForSale() throws InvalidTransactionIdException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidQuantityException, InvalidProductIdException, InvalidPaymentException {
		EZShopInterface EzShop = new EZShop();
		Integer id = 0, points = 0;
		double change = 0;
		User usr;
		boolean ret = false;
		
		try {
			points = EzShop.computePointsForSale(id);
			fail();
		} catch (UnauthorizedException e) {
			
		}
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
		id = EzShop.createProductType("NieR Piano Collections", "122474487139", 10.0, "Music CD");
		assertTrue(id != -1);
		
		ret = EzShop.updateQuantity(id, 2);
		assertTrue(ret);
		
		id = EzShop.startSaleTransaction();
		assertTrue(id > 0);
		
		ret = EzShop.addProductToSale(id, "122474487139", 1);
		assertTrue(ret);
		
		try {
			points = EzShop.computePointsForSale(null);
			fail();
		} catch (InvalidTransactionIdException e) {
			
		}
		
		try {
			points = EzShop.computePointsForSale(0);
			fail();
		} catch (InvalidTransactionIdException e) {
			
		}
		
		points = EzShop.computePointsForSale(id+1);
		assertTrue(points == -1);
		
		points = EzShop.computePointsForSale(id);
		assertTrue(points == 1);
		
		ret = EzShop.endSaleTransaction(id);
		assertTrue(ret);
		points = EzShop.computePointsForSale(id);
		assertTrue(points == 1);
		
		change = EzShop.receiveCashPayment(id, 11.0);
		assertTrue(change == 1);
		points = EzShop.computePointsForSale(id);
		assertTrue(points == 1);
		
		return;
	}
	
	
}
