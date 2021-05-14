package it.polito.ezshop.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.model.*;

public class TestClasses {

	@Test
	public void testClasses() {
		BalanceOperationImpl boi = new BalanceOperationImpl();
		boi = new BalanceOperationImpl(0, null);		
		boi.setBalanceId(0);
		boi.setDate(null);
		boi.setMoney(0);
		boi.setType(null);
		
		assertTrue(boi.getBalanceId() == 0);
		assertTrue(boi.getDate() == null);
		assertTrue(boi.getMoney() == 0);
		assertTrue(boi.getType() == null);
		
		CustomerImpl ci = new CustomerImpl("test");
		ci.setCustomerCard(null);
		ci.setCustomerName(null);
		ci.setId(0);
		ci.setPoints(null);
		
		assertTrue(ci.getCustomerCard() == null);
		assertTrue(ci.getCustomerName() == null);
		assertTrue(ci.getId() == 0);
		assertTrue(ci.getPoints() == null);

		OrderImpl oi = new OrderImpl("test", 0, 0);
		oi.setBalanceId(null);
		oi.setOrderId(null);
		oi.setPricePerUnit(0);
		oi.setProductCode(null);
		oi.setQuantity(0);
		oi.setStatus("PAYED");
		
		assertTrue(oi.getBalanceId() == null);
		assertTrue(oi.getOrderId() == null);
		assertTrue(oi.getPricePerUnit() == 0);
		assertTrue(oi.getProductCode() == null);
		assertTrue(oi.getQuantity() == 0);
		assertTrue(oi.getStatus().equals("PAYED"));
		assertTrue(LocalDate.class.isInstance(oi.getDate()));
		assertTrue(BalanceOperation.class.isInstance(oi.getBalanceOperation()));

		ProductTypeImpl pti = new ProductTypeImpl();
		pti.setBarCode(null);
		pti.setId(null);
		pti.setLocation(null);
		pti.setNote(null);
		pti.setPricePerUnit(null);
		pti.setProductDescription(null);
		pti.setQuantity(null);
		
		assertTrue(pti.getBarCode() == null);
		assertTrue(pti.getId() == null);
		assertTrue(pti.getLocation() == null);
		assertTrue(pti.getNote() == null);
		assertTrue(pti.getPricePerUnit() == null);
		assertTrue(pti.getProductDescription() == null);
		assertTrue(pti.getQuantity() == null);
		
		ReturnTransactionImpl rti = new ReturnTransactionImpl();
		rti.setId(null);
		rti.setPrice(0);
		rti.setSaleTransaction(null);
		rti.addEntry(new TicketEntryImpl("00000000000000", 1));
		
		assertTrue(rti.getId() == null);
		assertTrue(rti.getPrice() == 0);
		assertTrue(rti.getSaleTransaction() == null);
		assertTrue(rti.getEntries().size() == 1);
		assertTrue(rti.getEntry("00000000000000").isPresent());
		assertTrue(LocalDate.class.isInstance(rti.getDate()));
		assertTrue(BalanceOperation.class.isInstance(rti.getBalanceOperation()));
		
		SaleTransactionImpl sti = new SaleTransactionImpl();
		sti.setDiscountRate(0);

		sti.setPrice(0);
		sti.setTicketNumber(null);
		
		List<TicketEntry> entries = new ArrayList<TicketEntry>();
		entries.add(new TicketEntryImpl("00000000000000", 1));
		sti.setEntries(entries);
		assertFalse(sti.containsProduct("00000000000001"));
		assertTrue(sti.containsProduct("00000000000000"));
		assertTrue(sti.getDiscountRate() == 0);
		assertTrue(sti.getEntries() == entries);
		assertTrue(sti.getPrice() == 0);
		assertTrue(sti.getTicketNumber() == null);
		assertTrue(LocalDate.class.isInstance(sti.getDate()));
		assertTrue(BalanceOperation.class.isInstance(sti.getBalanceOperation()));
		
		TicketEntryImpl tei = new TicketEntryImpl(null, null);
		tei = new TicketEntryImpl(null, null, 0, 0);
		tei.setAmount(0);
		tei.setBarCode(null);
		tei.setDiscountRate(0);
		tei.setPricePerUnit(0);
		tei.setProductDescription(null);
		
		assertTrue(tei.getAmount() == 0);
		assertTrue(tei.getBarCode() == null);
		assertTrue(tei.getDiscountRate() == 0);
		assertTrue(tei.getPricePerUnit() == 0);
		assertTrue(tei.getProductDescription() == null);
		
		UserImpl ui = new UserImpl();
		ui.setId(null);
		ui.setPassword(null);
		ui.setRole(null);
		ui.setUsername(null);
		
		assertTrue(ui.getId() == null);
		assertTrue(ui.getPassword() == null);
		assertTrue(ui.getRole() == null);
		assertTrue(ui.getUsername() == null);
		
		CreditCardImpl cci = new CreditCardImpl(null, null);
		cci.setBalance(null);
		cci.setCode(null);
		
		assertTrue(cci.getBalance() == null);
		assertTrue(cci.getCode() == null);
	}
}
