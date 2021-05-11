package it.polito.ezshop.acceptanceTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
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
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
		id = EzShop.startSaleTransaction();
		assertTrue(id > 0);
		
		return;
	}
	
	@Test
	public void testAddProductToSale() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidProductIdException, InvalidLocationException {
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
		
		ret = EzShop.updatePosition(id, "001-abcd-002");
		assertTrue(ret);
		
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
			ret = EzShop.addProductToSale(id, "", 1);
			fail();
		} catch (InvalidProductCodeException e) {
			
		}
		
		try {
			ret = EzShop.addProductToSale(id, "notValidBarcode", 1);
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
	public void testDeleteProductFromSale() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidTransactionIdException, InvalidQuantityException, InvalidLocationException {
		EZShopInterface EzShop = new EZShop();
		Integer id = 0;
		User usr;
		boolean ret = false;
		
		try {
			ret = EzShop.deleteProductFromSale(1, "122474487139", 1);
			fail();
		} catch (UnauthorizedException e) {
			System.out.println("I got here");
		}
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
		id = EzShop.createProductType("NieR Piano Collections", "122474487139", 10, "Music CD");
		assertTrue(id != -1);
		
		ret = EzShop.updatePosition(id, "001-abcd-002");
		assertTrue(ret);
		
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
	public void testApplyDiscountRateToProduct() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidTransactionIdException, InvalidQuantityException, InvalidDiscountRateException, InvalidLocationException {
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
		
		id = EzShop.createProductType("TwentyOnePilots - Blurryface", "978020137962", 10, "Music CD");
		assertTrue(id != -1);
		
		ret = EzShop.updatePosition(id, "001-abcd-003");
		assertTrue(ret);
		
		ret = EzShop.updateQuantity(id, 2);
		assertTrue(ret);
		
		id = EzShop.createProductType("NieR Piano Collections", "122474487139", 10, "Music CD");
		assertTrue(id != -1);
		
		ret = EzShop.updatePosition(id, "001-abcd-002");
		assertTrue(ret);
		
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
		
		ret = EzShop.applyDiscountRateToProduct(id, "978020137962", 0.1);
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
	public void testComputePointsForSale() throws InvalidTransactionIdException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidQuantityException, InvalidProductIdException, InvalidPaymentException, InvalidLocationException {
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
		
		ret = EzShop.updatePosition(id, "001-abcd-002");
		assertTrue(ret);
		
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
	
	@Test
	public void testEndSaleTransaction() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidProductIdException, InvalidQuantityException, InvalidLocationException {
		EZShopInterface EzShop = new EZShop();
		Integer id = 0;
		User usr;
		boolean ret = false;
		
		try {
			ret = EzShop.endSaleTransaction(id);
			fail();
		} catch (UnauthorizedException e) {
			
		}
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
		id = EzShop.createProductType("NieR Piano Collections", "122474487139", 10.0, "Music CD");
		assertTrue(id != -1);
		
		ret = EzShop.updatePosition(id, "001-abcd-002");
		assertTrue(ret);
		
		ret = EzShop.updateQuantity(id, 2);
		assertTrue(ret);
		
		id = EzShop.startSaleTransaction();
		assertTrue(id > 0);
		
		ret = EzShop.addProductToSale(id, "122474487139", 1);
		assertTrue(ret);
		
		try {
			ret = EzShop.endSaleTransaction(null);
			fail();
		} catch (InvalidTransactionIdException e) {
			
		}
		
		try {
			ret = EzShop.endSaleTransaction(0);
			fail();
		} catch (InvalidTransactionIdException e) {
			
		}
		
		ret = EzShop.endSaleTransaction(id+1);
		assertTrue(!ret);
		
		ret = EzShop.endSaleTransaction(id);
		assertTrue(ret);
		
		return;
	}
	
	@Test
	public void testDeleteSaleTransaction() throws InvalidTransactionIdException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidQuantityException, InvalidLocationException {
		EZShopInterface EzShop = new EZShop();
		Integer id = 0;
		User usr;
		boolean ret = false;
		
		try {
			ret = EzShop.deleteSaleTransaction(id);
			fail();
		} catch (UnauthorizedException e) {
			
		}
		
		id = EzShop.createUser("Marzio", "password", "Administrator");
		assertTrue(id > 0);
		usr = EzShop.login("Marzio", "password");
		assertTrue(usr != null);
		
		id = EzShop.createProductType("NieR Piano Collections", "122474487139", 10.0, "Music CD");
		assertTrue(id != -1);
		
		ret = EzShop.updatePosition(id, "001-abcd-002");
		assertTrue(ret);
		
		ret = EzShop.updateQuantity(id, 2);
		assertTrue(ret);
		
		id = EzShop.startSaleTransaction();
		assertTrue(id > 0);
		
		ret = EzShop.addProductToSale(id, "122474487139", 1);
		assertTrue(ret);
		
		try {
			ret = EzShop.deleteSaleTransaction(null);
			fail();
		} catch (InvalidTransactionIdException e) {
			
		}
		
		try {
			ret = EzShop.deleteSaleTransaction(0);
			fail();
		} catch (InvalidTransactionIdException e) {
			
		}
		
		ret = EzShop.deleteSaleTransaction(id+1);
		assertTrue(!ret);
		
		ret = EzShop.deleteSaleTransaction(id);
		assertTrue(ret);
		
		
		id = EzShop.startSaleTransaction();
		assertTrue(id > 0);
		ret = EzShop.addProductToSale(id, "122474487139", 1);
		assertTrue(ret);
		ret = EzShop.endSaleTransaction(id);
		assertTrue(ret);
		ret = EzShop.deleteSaleTransaction(id);
		assertTrue(ret);
		
		return;
	}
	
	@Test
	public void testGetSaleTransaction() throws InvalidTransactionIdException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidQuantityException {
		EZShopInterface ezShop = new EZShop();

		try {
			ezShop.getSaleTransaction(0);
			fail();
		}catch(UnauthorizedException e) {
			//pass
		}

		
		ezShop.createUser("ruggero", "password", "Administrator");
		ezShop.login("ruggero", "password");
		Integer id = ezShop.createProductType("Lenovo Yoga Slim 7", "1845678901001", 1000.0, "Notebook");
		ezShop.updatePosition(id, "001-abcd-003");		
		ezShop.updateQuantity(id, 2);
		
		id = ezShop.startSaleTransaction();
		ezShop.addProductToSale(id, "1845678901001", 1);
		assertTrue(ezShop.getSaleTransaction(id) == null);
		ezShop.endSaleTransaction(id);
		assertTrue(ezShop.getSaleTransaction(id) != null);
		
		try {
			ezShop.getSaleTransaction(0);
			fail();
		}catch(InvalidTransactionIdException e) {
			//pass
		}
		
		try {
			ezShop.getSaleTransaction(-1);
			fail();
		}catch(InvalidTransactionIdException e) {
			//pass
		}
		
		try {
			ezShop.getSaleTransaction(null);
			fail();
		}catch(InvalidTransactionIdException e) {
			//pass
		}
	}
	
	@Test
	public void testStartReturnTransaction() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidTransactionIdException, InvalidQuantityException, InvalidPaymentException {
		EZShopInterface ezShop = new EZShop();
		
		try {
			ezShop.startReturnTransaction(1);
			fail();
		}catch(UnauthorizedException e) {
			//pass
		}
		
		ezShop.createUser("ruggero", "password", "Administrator");
		ezShop.login("ruggero", "password");
		
		try {
			ezShop.startReturnTransaction(0);
			fail();
		}catch(InvalidTransactionIdException e) {
			//pass;
		}
		
		try {
			ezShop.startReturnTransaction(-1);
			fail();
		}catch(InvalidTransactionIdException e) {
			//pass;
		}
		
		try {
			ezShop.startReturnTransaction(null);
			fail();
		}catch(InvalidTransactionIdException e) {
			//pass;
		}
		
		assertTrue(ezShop.startReturnTransaction(1) == -1);
		
		Integer productId = ezShop.createProductType("Lenovo Yoga Slim 7", "1845678901001", 1000.0, "Notebook");
		ezShop.updatePosition(productId, "001-abcd-003");		
		ezShop.updateQuantity(productId, 10);
		Integer saleId = ezShop.startSaleTransaction();
		ezShop.addProductToSale(saleId, "1845678901001", 2);
		ezShop.endSaleTransaction(saleId);
		ezShop.receiveCashPayment(saleId, 2000.0);
		
		assertTrue(ezShop.startReturnTransaction(saleId) >= 0);
	}
	
	@Test
	public void testReturnProduct() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidPaymentException {
		EZShopInterface ezShop = new EZShop();
		
		try {
			ezShop.returnProduct(0, null, 0);
			fail();
		}catch(UnauthorizedException e) {
			//pass
		}
		
		ezShop.createUser("ruggero", "password", "Administrator");
		ezShop.login("ruggero", "password");
		Integer productId = ezShop.createProductType("Lenovo Yoga Slim 7", "1845678901001", 1000.0, "Notebook");
		ezShop.updatePosition(productId, "001-abcd-003");		
		ezShop.updateQuantity(productId, 10);
		Integer productId2 = ezShop.createProductType("Samsung A52", "8806090987977", 400.0, "Smartphone");
		ezShop.updatePosition(productId, "002-abcd-003");		
		ezShop.updateQuantity(productId, 10);
		
		Integer saleId = ezShop.startSaleTransaction();
		ezShop.addProductToSale(saleId, "1845678901001", 3);
		ezShop.endSaleTransaction(saleId);
		ezShop.receiveCashPayment(saleId, 3000.0);
		
		Integer returnId = ezShop.startReturnTransaction(saleId);
		
		try {
			ezShop.returnProduct(-1, null, 0);
		} catch (InvalidTransactionIdException e){
			//pass
		}
		
		try {
			ezShop.returnProduct(0, null, 0);
		} catch (InvalidTransactionIdException e){
			//pass
		}
		
		try {
			ezShop.returnProduct(null, null, 0);
		} catch (InvalidTransactionIdException e){
			//pass
		}
		
		try {
			ezShop.returnProduct(returnId, "", 1);
		} catch (InvalidProductCodeException e) {
			//pass
		}
		
		try {
			ezShop.returnProduct(returnId, null, 1);
		} catch (InvalidProductCodeException e) {
			//pass
		}
		
		try {
			ezShop.returnProduct(returnId, "123", 1);
		} catch (InvalidProductCodeException e) {
			//pass
		}
		
		try {
			ezShop.returnProduct(returnId, "1845678901001", 0);
		} catch (InvalidQuantityException e) {
			//pass
		}
		
		try {
			ezShop.returnProduct(returnId, "1845678901001", -1);
		} catch (InvalidQuantityException e) {
			//pass
		}
		
		// product does not exists
		assertFalse(ezShop.returnProduct(returnId, "8015696020203", 1));
		// product was not in the transiction
		assertFalse(ezShop.returnProduct(returnId, "8806090987977", 1));
		// amount is higher thant the one in the sale transaction
		assertFalse(ezShop.returnProduct(returnId, "1845678901001", 4));
		// transaction does not exist
		assertFalse(ezShop.returnProduct(100, "1845678901001", 1));
		
		// operation successfull
		assertTrue(ezShop.returnProduct(returnId, "1845678901001", 1));
		// i try to add again the same product with an higher amount
		// should return true but set the amount to return equals to the amount
		// in the sale transaction
		assertTrue(ezShop.returnProduct(returnId, "1845678901001", 1));
		assertTrue(ezShop.returnProduct(returnId, "1845678901001", 3));
	}
	
	@Test
	public void testEndReturnTransaction() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidTransactionIdException, InvalidPaymentException, InvalidQuantityException {
		EZShopInterface ezShop = new EZShop();

		try {
			ezShop.endReturnTransaction(null, false);
		} catch (UnauthorizedException e) {
			// pass
		}
		ezShop.createUser("ruggero", "password", "Administrator");
		ezShop.login("ruggero", "password");
		Integer productId = ezShop.createProductType("Lenovo Yoga Slim 7", "1845678901001", 1000.0, "Notebook");
		ezShop.updatePosition(productId, "001-abcd-003");		
		ezShop.updateQuantity(productId, 10);
		
		Integer saleId = ezShop.startSaleTransaction();
		ezShop.addProductToSale(saleId, "1845678901001", 3);
		ezShop.endSaleTransaction(saleId);
		ezShop.receiveCashPayment(saleId, 3000.0);
		
		Integer returnId = ezShop.startReturnTransaction(saleId);
		ezShop.returnProduct(returnId, "1845678901001", 1);
		
		try {
			ezShop.endReturnTransaction(0, false);
			fail();
		} catch (InvalidTransactionIdException e) {
			//pass
		}
		
		try {
			ezShop.endReturnTransaction(-1, false);
			fail();
		} catch (InvalidTransactionIdException e) {
			//pass
		}
		
		try {
			ezShop.endReturnTransaction(null, false);
			fail();
		} catch (InvalidTransactionIdException e) {
			//pass
		}
		// non existing returnId
		assertFalse(ezShop.endReturnTransaction(100, true));
		assertFalse(ezShop.endReturnTransaction(100, false));
		// don't commit
		assertTrue(ezShop.endReturnTransaction(returnId, false));
		// commit
		returnId = ezShop.startReturnTransaction(saleId);
		ezShop.returnProduct(returnId, "1845678901001", 1);
		assertTrue(ezShop.endReturnTransaction(returnId, true));
	}
	
	@Test
	public void testDeleteReturnTransaction() throws InvalidTransactionIdException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidQuantityException, InvalidPaymentException {
		EZShopInterface ezShop = new EZShop();
		
		try {
			ezShop.deleteReturnTransaction(0);
		} catch (UnauthorizedException e) {
			//pass
		}
		
		ezShop.createUser("ruggero", "password", "Administrator");
		ezShop.login("ruggero", "password");
		Integer productId = ezShop.createProductType("Lenovo Yoga Slim 7", "1845678901001", 1000.0, "Notebook");
		ezShop.updatePosition(productId, "001-abcd-003");		
		ezShop.updateQuantity(productId, 10);
		
		Integer saleId = ezShop.startSaleTransaction();
		ezShop.addProductToSale(saleId, "1845678901001", 3);
		ezShop.endSaleTransaction(saleId);
		ezShop.receiveCashPayment(saleId, 3000.0);
		
		Integer returnId = ezShop.startReturnTransaction(saleId);
		ezShop.returnProduct(returnId, "1845678901001", 1);
		ezShop.endReturnTransaction(returnId, true);
		
		try {
			ezShop.deleteReturnTransaction(0);
		} catch (InvalidTransactionIdException e) {
			// pass
		}
		
		try {
			ezShop.deleteReturnTransaction(-1);
		} catch (InvalidTransactionIdException e) {
			// pass
		}
		
		try {
			ezShop.deleteReturnTransaction(null);
		} catch (InvalidTransactionIdException e) {
			// pass
		}
		
		assertFalse(ezShop.deleteReturnTransaction(100));
		assertTrue(ezShop.deleteReturnTransaction(returnId));

		returnId = ezShop.startReturnTransaction(saleId);
		ezShop.returnProduct(returnId, "1845678901001", 1);
		ezShop.endReturnTransaction(returnId, true);
		ezShop.returnCashPayment(returnId);
		
		assertFalse(ezShop.deleteReturnTransaction(returnId));
	}
}
