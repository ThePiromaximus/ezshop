package it.polito.ezshop.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import it.polito.ezshop.data.BalanceOperation;
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
			EZShopInterface ezShop = new EZShop(0);
			
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
			EZShopInterface ezShop = new EZShop(0);
			
			try {
				ezShop.getCreditsAndDebits(null, null);
				fail();
			} catch (UnauthorizedException e) {
				//pass
			}
			LocalDate from = LocalDate.now().minusDays(5);
			LocalDate to = LocalDate.now().plusDays(5);
			
			ezShop.createUser("ruggero", "password", "Administrator");
			ezShop.login("ruggero", "password");
			Integer productId = ezShop.createProductType("Lenovo Yoga Slim 7", "1845678901001", 1000.0, "Notebook");
			ezShop.updatePosition(productId, "001-abcd-003");		
			ezShop.updateQuantity(productId, 10);
			
			Integer saleId = ezShop.startSaleTransaction();
			ezShop.addProductToSale(saleId, "1845678901001", 3);
			ezShop.endSaleTransaction(saleId);
			ezShop.receiveCashPayment(saleId, 3000.0);
			
			List<BalanceOperation> bos = ezShop.getCreditsAndDebits(from, to);
			Collections.reverse(bos);
			assertTrue(bos.size() == 1);
			assertTrue(bos.get(0).getMoney() == 3000.0);
			assertTrue(bos.get(0).getType().equals("SALE"));
			
			Integer returnId = ezShop.startReturnTransaction(saleId);
			Collections.reverse(bos);
			ezShop.returnProduct(returnId, "1845678901001", 1);
			ezShop.endReturnTransaction(returnId, true);
			ezShop.returnCashPayment(returnId);
			
			bos = ezShop.getCreditsAndDebits(from, to);
			Collections.reverse(bos);
			assertTrue(bos.size() == 2);
			assertTrue(bos.get(0).getMoney() == 0);
			assertTrue(bos.get(0).getType().equals("RETURN"));
			
			ezShop.payOrderFor("1845678901001", 2, 700.0);
			bos = ezShop.getCreditsAndDebits(from, to);
			Collections.reverse(bos);
			assertTrue(bos.size() == 3);
			assertTrue(bos.get(0).getMoney() == -1400.0);
			assertTrue(bos.get(0).getType().equals("ORDER"));
			
			ezShop.recordBalanceUpdate(500.0);
			bos = ezShop.getCreditsAndDebits(from, to);
			Collections.reverse(bos);
			assertTrue(bos.size() == 4);
			assertTrue(bos.get(0).getMoney() == 500.0);
			assertTrue(bos.get(0).getType().equals("CREDIT"));
			
			ezShop.recordBalanceUpdate(-200.0);
			bos = ezShop.getCreditsAndDebits(from, to);
			Collections.reverse(bos);
			assertTrue(bos.size() == 5);
			assertTrue(bos.get(0).getMoney() == -200.0);
			assertTrue(bos.get(0).getType().equals("DEBIT"));
			
			double balance = 0;
			for(BalanceOperation b : bos) {
				balance += b.getMoney();
			}
			assertTrue(balance == ezShop.computeBalance());
			assertTrue(ezShop.getCreditsAndDebits(LocalDate.now().plusDays(1), null).size() == 0);
			assertTrue(ezShop.getCreditsAndDebits(null, LocalDate.now().minusDays(1)).size() == 0);
			assertTrue(ezShop.getCreditsAndDebits(null, null).size() == 5);
			assertTrue(ezShop.getCreditsAndDebits(to, from).size() == 5);
		}
		
		@Test
		public void testComputeBalance() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, UnauthorizedException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidTransactionIdException, InvalidPaymentException, InvalidQuantityException {
			EZShopInterface ezShop = new EZShop(0);
			
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
