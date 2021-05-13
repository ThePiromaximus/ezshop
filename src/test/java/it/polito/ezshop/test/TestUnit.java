package it.polito.ezshop.test;
import static org.junit.Assert.*;
import org.junit.Test;
import it.polito.ezshop.data.*;


public class TestUnit {
	
	@Test
	public void testBarcodeIsValid() {
		
		//barCode = null -> return false
		assertFalse(EZShop.barCodeIsValid(null));
		//barCode = "" -> return false
		assertFalse(EZShop.barCodeIsValid(""));
		//barCode = "111111111" (length==9) -> return false
		assertFalse(EZShop.barCodeIsValid("123"));
		//barCode = "111111111111" (length==12) -> return false
		assertFalse(EZShop.barCodeIsValid("111111111111"));
		//barCode = "111111111111" (length==13) -> return false
		assertFalse(EZShop.barCodeIsValid("1111111111111"));
		//barCode = "111111111111" (length==14) -> return false
		assertFalse(EZShop.barCodeIsValid("11111111111111"));
		//barCode = "746299013160" -> return true
		assertTrue(EZShop.barCodeIsValid("746299013160"));
		//barCode = "6291041500213" -> return true
		assertTrue(EZShop.barCodeIsValid("6291041500213"));
		//barCode = "37181020812812" -> return true
		assertTrue(EZShop.barCodeIsValid("37181020812812"));
		
		
	}
	
	@Test
	public void testCreditCardIsValid() {
		//creditCard = "" -> return false
		assertFalse(EZShop.creditCardIsValid(""));
		//creditCard = null -> return false
		assertFalse(EZShop.creditCardIsValid(null));
		//creditCard = "" -> return false
		assertFalse(EZShop.creditCardIsValid("11111111"));
		//creditCard = "4194343567131588" -> return true
		assertTrue(EZShop.creditCardIsValid("4194343567131588"));
	}
	
	@Test
	public void testRoundUp() {
		//toRound = 10 -> return 10
		assertTrue(EZShop.RoundUp(10)==10);
		//toRound = 0 -> return 0
		assertTrue(EZShop.RoundUp(0)==0);
		//toRound = 1 -> return 10
		assertTrue(EZShop.RoundUp(1)==10);
		//toRound = -2 -> return 0
		assertTrue(EZShop.RoundUp(-2)==0);
	}
	
	
}
