package it.polito.ezshop.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.EZShopInterface;
import it.polito.ezshop.exceptions.InvalidCreditCardException;
import it.polito.ezshop.exceptions.InvalidLocationException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidPaymentException;
import it.polito.ezshop.exceptions.InvalidPricePerUnitException;
import it.polito.ezshop.exceptions.InvalidProductCodeException;
import it.polito.ezshop.exceptions.InvalidProductDescriptionException;
import it.polito.ezshop.exceptions.InvalidProductIdException;
import it.polito.ezshop.exceptions.InvalidQuantityException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class TestFR7 {
	@Test
	public void testReceiveCashPayment() throws InvalidTransactionIdException, InvalidPaymentException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidQuantityException {
		EZShopInterface ezShop = new EZShop(0);
		
		try {
			ezShop.receiveCashPayment(null, 0);
			fail();
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
		// ezShop.receiveCashPayment(saleId, 3000.0);
		
		try {
			ezShop.receiveCashPayment(0, 0);
			fail();
		} catch (InvalidTransactionIdException e) {
			//pass
		}
		
		try {
			ezShop.receiveCashPayment(-1, 0);
			fail();
		} catch (InvalidTransactionIdException e) {
			//pass
		}
		
		try {
			ezShop.receiveCashPayment(null, 0);
			fail();
		} catch (InvalidTransactionIdException e) {
			//pass
		}
		
		try {
			ezShop.receiveCashPayment(saleId, 0);
			fail();
		} catch (InvalidPaymentException e) {
			//pass
		}
		
		try {
			ezShop.receiveCashPayment(saleId, -1);
			fail();
		} catch (InvalidPaymentException e) {
			//pass
		}
		double oldBalance = ezShop.computeBalance();
		assertTrue(ezShop.receiveCashPayment(100, 1) == -1);
		assertTrue(ezShop.receiveCashPayment(saleId, 1) == -1);
		assertTrue(ezShop.receiveCashPayment(saleId, 4000.0) == 1000.0);
		assertTrue(ezShop.computeBalance() == (oldBalance + 3000.0));
	}
	
	@Test
	public void testReceiveCreditCardPayment() throws InvalidTransactionIdException, InvalidCreditCardException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidQuantityException {
		EZShopInterface ezShop = new EZShop(0);
		
		try {
			ezShop.receiveCreditCardPayment(null, null);
			fail();
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
		
		try {
			ezShop.receiveCreditCardPayment(0, null);
			fail();
		} catch (InvalidTransactionIdException e) {
			// pass
		}
		
		try {
			ezShop.receiveCreditCardPayment(-1, null);
			fail();
		} catch (InvalidTransactionIdException e) {
			// pass
		}
		
		try {
			ezShop.receiveCreditCardPayment(null, null);
			fail();
		} catch (InvalidTransactionIdException e) {
			// pass
		}
		
		try {
			ezShop.receiveCreditCardPayment(saleId, "");
			fail();
		} catch (InvalidCreditCardException e) {
			// pass
		}
		
		try {
			ezShop.receiveCreditCardPayment(saleId, null);
			fail();
		} catch (InvalidCreditCardException e) {
			// pass
		}
		
		try {
			ezShop.receiveCreditCardPayment(saleId, "1234 wrong");
			fail();
		} catch (InvalidCreditCardException e) {
			// pass
		}
		double oldBalance = ezShop.computeBalance();
		//non existing id
		assertFalse(ezShop.receiveCreditCardPayment(100, "5555555555554444"));
		//card with not enough money
		assertFalse(ezShop.receiveCreditCardPayment(saleId, "0000000000000000"));
		//card not registered
		assertFalse(ezShop.receiveCreditCardPayment(saleId, "4111111111111111"));
		//ok
		assertTrue(ezShop.receiveCreditCardPayment(saleId, "5555555555554444"));
		assertTrue(ezShop.computeBalance() == (oldBalance + 3000.0));
	}
	
	@Test
	public void testReturnCashPayment() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductIdException, UnauthorizedException, InvalidLocationException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException, InvalidPaymentException {
		EZShopInterface ezShop = new EZShop(0);
		
		try {
			ezShop.returnCashPayment(null);
			fail();
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
		// return transaction is not ended
		assertTrue(ezShop.returnCashPayment(returnId) == -1);
		ezShop.endReturnTransaction(returnId, true);
		
		try {
			ezShop.returnCashPayment(0);
			fail();
		} catch (InvalidTransactionIdException e) {
			//pass
		}
		
		try {
			ezShop.returnCashPayment(-1);
			fail();
		} catch (InvalidTransactionIdException e) {
			//pass
		}
		
		try {
			ezShop.returnCashPayment(null);
			fail();
		} catch (InvalidTransactionIdException e) {
			//pass
		}
		// return transaction doesn't exist
		assertTrue(ezShop.returnCashPayment(100) == -1);
		// correct behavior
		double oldBalance = ezShop.computeBalance();
		assertTrue(ezShop.returnCashPayment(returnId) == 1000.0);
		assertTrue(ezShop.computeBalance() == (oldBalance-1000.0));
	}
	
	@Test
	public void testReturnCreditCardPayment() throws InvalidTransactionIdException, InvalidCreditCardException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidQuantityException, InvalidPaymentException {
		EZShopInterface ezShop = new EZShop(0);
		
		try {
			ezShop.returnCreditCardPayment(null, null);
			fail();
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
		// return transaction is not ended
		assertTrue(ezShop.returnCreditCardPayment(returnId, "5555555555554444") == -1);
		ezShop.endReturnTransaction(returnId, true);
		
		try {
			ezShop.returnCreditCardPayment(0, null);
			fail();
		} catch (InvalidTransactionIdException e) {
			// pass
		}
		
		try {
			ezShop.returnCreditCardPayment(0, null);
			fail();
		} catch (InvalidTransactionIdException e) {
			// pass
		}
		
		try {
			ezShop.returnCreditCardPayment(0, null);
			fail();
		} catch (InvalidTransactionIdException e) {
			// pass
		}
		
		try {
			ezShop.returnCreditCardPayment(returnId, "");
			fail();
		} catch (InvalidCreditCardException e) {
			// pass
		}
		
		try {
			ezShop.returnCreditCardPayment(returnId, null);
			fail();
		} catch (InvalidCreditCardException e) {
			// pass
		}
		
		try {
			ezShop.returnCreditCardPayment(returnId, "1234 wrong");
			fail();
		} catch (InvalidCreditCardException e) {
			// pass
		}
		
		assertTrue(ezShop.returnCreditCardPayment(100, "5555555555554444") == -1);
		// non existing card
		assertFalse(ezShop.receiveCreditCardPayment(saleId, "4111111111111111"));
		// correct behavior
		double oldBalance = ezShop.computeBalance();
		assertTrue(ezShop.returnCreditCardPayment(returnId, "5555555555554444") == 1000.0);
		assertTrue(ezShop.computeBalance() == (oldBalance-1000.0));
	}
}
