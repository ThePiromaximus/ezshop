package it.polito.ezshop.test;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import it.polito.ezshop.data.*;
import it.polito.ezshop.exceptions.*;


public class TestFR1 {
		@Test
		public void testLogout() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
			EZShopInterface EzShop = new EZShop(0);

			Integer id = 0;
			User usr;
			boolean ret;

			ret = EzShop.logout();
			assertTrue(!ret);
			
			id = EzShop.createUser("Marzio", "password", "Administrator");
			assertTrue(id > 0);
			usr = EzShop.login("Marzio", "password");
			assertTrue(usr != null);

			ret = EzShop.logout();
			assertTrue(ret);
			return;
		}
		
		@Test
		public void testReset() {
			EZShopInterface EzShop = new EZShop(0);
			
			EzShop.reset();
			
			return;
		}

		@SuppressWarnings("unused")
		@Test
		public void testCreateUser() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
			
			//username=null -> throws InvalidUsernameException
			EZShop ezshop = new EZShop(0);
			try
			{
				Integer id = ezshop.createUser(null, "admin", "Administrator");
				fail("Expected an UsernameException to be thrown");
			}catch(InvalidUsernameException e)
			{
				assertNotNull(e);
			}
			
			//username="" -> throws InvalidUsernameException
			ezshop = new EZShop(0);
			try
			{
				Integer id = ezshop.createUser("", "admin", "Administrator");
				fail("Expected an UsernameException to be thrown");
			}catch(InvalidUsernameException e)
			{
				assertNotNull(e);
			}
			
			//password=null -> throws InvalidPasswordException
			ezshop = new EZShop(0);
			try
			{
				Integer id = ezshop.createUser("admin", null, "Administrator");
				fail("Expected an PAsswordException to be thrown");
			}catch(InvalidPasswordException e)
			{
				assertNotNull(e);
			}
			
			//password="" -> throws InvalidPasswordException
			ezshop = new EZShop(0);
			try
			{
				Integer id = ezshop.createUser("admin", "" , "Administrator");
				fail("Expected an PAsswordException to be thrown");
			}catch(InvalidPasswordException e)
			{
				assertNotNull(e);
			}
			
			//role=null -> throws InvalidRoleException
			ezshop = new EZShop(0);
			try
			{
				Integer id = ezshop.createUser("admin", "admin", null);
				fail("Expected an RoleException to be thrown");
			}catch(InvalidRoleException e)
			{
				assertNotNull(e);
			}
			
			//role="" -> throws InvalidRoleException
			ezshop = new EZShop(0);
			try
			{
				Integer id = ezshop.createUser("admin", "admin", "");
				fail("Expected an RoleException to be thrown");
			}catch(InvalidRoleException e)
			{
				assertNotNull(e);
			}
			
			//role="Admin" -> throws InvalidRoleException
			ezshop = new EZShop(0);
			try
			{
				Integer id = ezshop.createUser("admin", "admin", "Admin");
				fail("Expected an RoleException to be thrown");
			}catch(InvalidRoleException e)
			{
				assertNotNull(e);
			}
			
			//Username that already exists returns -1  
			ezshop = new EZShop(0);
			ezshop.createUser("CashierA","MyPW","Cashier");
			Integer id = ezshop.createUser("CashierA", "MyPW", "Cashier");
			assertTrue(id < 0);
			
			//New User returns id > 0 
			ezshop = new EZShop(0);
			id = ezshop.createUser("ShopManagerA", "MyPW", "ShopManager");
			assertTrue(id > 0);
			
		}
		
		@SuppressWarnings("unused")
		@Test
		public void testDeleteUser() throws InvalidUserIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
			
			//loggedUser = null -> throws UnauthorizedException
			EZShop ezshop = new EZShop(0);
			try
			{
				boolean res = ezshop.deleteUser(null);
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
				boolean res = ezshop.deleteUser(null);
				fail("Expected an UnauthorizedException to be thrown");
			}catch(UnauthorizedException e)
			{
				assertNotNull(e);
			}
			
			//id=null -> throws InvalidUserIdException
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			try
			{
				boolean res = ezshop.deleteUser(null);
				fail("Expected an UserIdException to be thrown");
			}catch(InvalidUserIdException e)
			{
				assertNotNull(e);
			}
			
			//id = negative number -> throws InvalidUserIdException
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			try
			{
				boolean res = ezshop.deleteUser(-3);
				fail("Expected an UserIdException to be thrown");
			}catch(InvalidUserIdException e)
			{
				assertNotNull(e);
			}
			
			//id = "100"  -> id isn't present return false
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			boolean res = ezshop.deleteUser(100);
			assertFalse(res);
			
			//id exists  -> return true 
			ezshop = new EZShop(0);
			Integer id = ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			res = ezshop.deleteUser(id);
			assertTrue(res);
		}
		
		@SuppressWarnings("unused")
		@Test
		public void testGetAllUsers() throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
			
			//loggedUser = null -> throws UnauthorizedException
			EZShop ezshop = new EZShop(0);
			try
			{
				List<User> res = ezshop.getAllUsers();
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
				List<User> res = ezshop.getAllUsers();
				fail("Expected an UnauthorizedException to be thrown");
			}catch(UnauthorizedException e)
			{
				assertNotNull(e);
			}
			
			ezshop.createUser("A", "A", "Administrator");
			ezshop.login("A", "A");
			List<User> res = ezshop.getAllUsers();
			assertNotNull(res);
			
	}
		
		@SuppressWarnings("unused")
		@Test
		public void testGetUser() throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidUserIdException{
			//loggedUser = null -> throws UnauthorizedException
			EZShop ezshop = new EZShop(0);
			try
			{
				User u = ezshop.getUser(null);
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
				User u = ezshop.getUser(null);
				fail("Expected an UnauthorizedException to be thrown");
			}catch(UnauthorizedException e)
			{
				assertNotNull(e);
			}
			
			//id=null -> throws InvalidUserIdException
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			try
			{
				User u = ezshop.getUser(null);
				fail("Expected an UserIdException to be thrown");
			}catch(InvalidUserIdException e)
			{
				assertNotNull(e);
			}
			
			//id=negative number -> throws InvalidUserIdException
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			try
			{
				User u = ezshop.getUser(-1);
				fail("Expected an UserIdException to be thrown");
			}catch(InvalidUserIdException e)
			{
				assertNotNull(e);
			}
			
			//Id exists  -> return user
			ezshop = new EZShop(0);
			Integer id = ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			User u = ezshop.getUser(id);
			assertNotNull(u);
			
			//Id = "100"  -> return null, user doesn't exists 
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			u = ezshop.getUser(100);
			assertNull(u);
			
		}
		
		@SuppressWarnings("unused")
		@Test
		public void testUpdateUserRights() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
			
			//loggedUser = null -> throws UnauthorizedException
			EZShop ezshop = new EZShop(0);
			try
			{
				boolean res = ezshop.updateUserRights(null, null);
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
				boolean u = ezshop.updateUserRights(null, null);
				fail("Expected an UnauthorizedException to be thrown");
			}catch(UnauthorizedException e)
			{
				assertNotNull(e);
			}
			
			//id=null -> throws InvalidUserIdException
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			try
			{
				boolean u = ezshop.updateUserRights(null, null);
				fail("Expected an UserIdException to be thrown");
			}catch(InvalidUserIdException e)
			{
				assertNotNull(e);
			}
			
			//id=negative number -> throws InvalidUserIdException
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			try
			{
				boolean u = ezshop.updateUserRights(-1, null);
				fail("Expected an UserIdException to be thrown");
			}catch(InvalidUserIdException e)
			{
				assertNotNull(e);
			}

			//role=null -> throws InvalidRoleException
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			try
			{
				boolean u = ezshop.updateUserRights(5, null);
				fail("Expected an RoleException to be thrown");
			}catch(InvalidRoleException e)
			{
				assertNotNull(e);
			}
			
			//role="" -> throws InvalidRoleException
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			try
			{
				boolean u = ezshop.updateUserRights(6, "");
				fail("Expected an RoleException to be thrown");
			}catch(InvalidRoleException e)
			{
				assertNotNull(e);
			}
			
			//role="Admin" -> throws InvalidRoleException 
			//infact should be Administrator or Cashier or ShopManager
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			try
			{
				boolean u = ezshop.updateUserRights(7, "Admin");
				fail("Expected an RoleException to be thrown");
			}catch(InvalidRoleException e)
			{
				assertNotNull(e);
			}
			
			//id = "100"  -> id isn't present return false
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			boolean u = ezshop.updateUserRights(100, "Administrator");
			assertFalse(u);
			
			//id exists  -> id  present return true
			ezshop = new EZShop(0);
			Integer id = ezshop.createUser("admin", "admin", "Administrator");
			ezshop.login("admin", "admin");
			u = ezshop.updateUserRights(id, "Administrator");
			assertTrue(u);
		}

		
		@SuppressWarnings("unused")
		@Test
		public void testLogin() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
			
			//username=null -> throws InvalidUsernameException
			EZShop ezshop = new EZShop(0);
			try
			{
				User u = ezshop.login(null, "psw");
				fail("Expected an UsernameException to be thrown");
			}catch(InvalidUsernameException e)
			{
				assertNotNull(e);
			}
			
			//username="" -> throws InvalidUsernameException
			ezshop = new EZShop(0);
			try
			{
				User u = ezshop.login("", "psw");
				fail("Expected an UsernameException to be thrown");
			}catch(InvalidUsernameException e)
			{
				assertNotNull(e);
			}
			
			//password=null -> throws InvalidPasswordException
			ezshop = new EZShop(0);
			try
			{
				User u = ezshop.login("user", null);
				fail("Expected an PasswordException to be thrown");
			}catch(InvalidPasswordException e)
			{
				assertNotNull(e);
			}
			
			//password="" -> throws InvalidPasswordException
			ezshop = new EZShop(0);
			try
			{
				User u = ezshop.login("user", "");
				fail("Expected an PasswordException to be thrown");
			}catch(InvalidPasswordException e)
			{
				assertNotNull(e);
			}
			
			//Correct login return loggeduser
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			User u = ezshop.login("admin","admin");
			assertNotNull(u);
			
			//wrong login return null
			ezshop = new EZShop(0);
			ezshop.createUser("admin", "admin", "Administrator");
			u = ezshop.login("cashier","pws");
			assertNull(u);
			
		}
		
		
}
