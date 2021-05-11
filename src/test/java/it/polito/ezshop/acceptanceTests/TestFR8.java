package it.polito.ezshop.acceptanceTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.EZShopInterface;
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

public class TestFR8 {

		@Test
		public void testRecordBalanceUpdate() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException {
			EZShopInterface ezShop = new EZShop();
			
			try {
				ezShop.recordBalanceUpdate(0);
				fail();
			} catch (UnauthorizedException e) {
				//pass
			}
			
			ezShop.createUser("ruggero", "password", "Administrator");
			ezShop.login("ruggero", "password");
			
			assertTrue(ezShop.computeBalance() == 0);
			
			assertFalse(ezShop.recordBalanceUpdate(-1000));
			
			assertTrue(ezShop.recordBalanceUpdate(500.0));
			assertTrue(ezShop.recordBalanceUpdate(-250.0));
			assertTrue(ezShop.computeBalance() == 250.0);
		}
		
		@Test
		public void testGetCreditsAndDebits() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductIdException, InvalidLocationException, UnauthorizedException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException, InvalidPaymentException {
			EZShopInterface ezShop = new EZShop();
			
			try {
				ezShop.getCreditsAndDebits(null, null);
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
			ezShop.endReturnTransaction(returnId, true);
		}
		
		@Test
		public void testComputeBalance() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidTransactionIdException, InvalidPaymentException, InvalidQuantityException {
			EZShopInterface ezShop = new EZShop();
			
			try {
				ezShop.computeBalance();
				fail();
			} catch (UnauthorizedException e) {
				//pass
			}
			
			ezShop.createUser("ruggero", "password", "Administrator");
			ezShop.login("ruggero", "password");
			
			assertTrue(ezShop.computeBalance() == 0);
			ezShop.recordBalanceUpdate(1000.0);
			assertTrue(ezShop.computeBalance() == 1000.0);
			ezShop.recordBalanceUpdate(500.0);
			assertTrue(ezShop.computeBalance() == 1500.0);
			ezShop.recordBalanceUpdate(-750.0);
			assertTrue(ezShop.computeBalance() == 750.0);
		}
}
