package it.polito.ezshop.acceptanceTests;

import static org.junit.Assert.assertTrue;

import org.junit.*;
import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.EZShopInterface;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;

/*
 * @Test
 *	public void testFooThrowsIndexOutOfBoundsException() {
 *	    try {
 *	        foo.doStuff();
 *	        fail("expected exception was not occured.");
 *	    } catch(IndexOutOfBoundsException e) {
 *	        //if execution reaches here, 
 *	        //it indicates this exception was occured.
 *	        //so we need not handle it.
 *	    }
 *	}
 */
public class TestFR1 {

		@Test
		public void testCreateUser() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
			EZShopInterface ezShop = new EZShop();
			Integer id = ezShop.createUser("ruggero", "password", "ADMIN");
			assertTrue(id > 0);
			
			ezShop = new EZShop();
		}
}
