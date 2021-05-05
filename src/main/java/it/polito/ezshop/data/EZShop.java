package it.polito.ezshop.data;

import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.model.*;
import java.time.LocalDate;
import java.util.*;

public class EZShop implements EZShopInterface {
	

	private HashMap<String,ProductType> products = new HashMap<String,ProductType>();
	private HashMap<Integer, SaleTransaction> closedSaleTransactions = new HashMap<Integer, SaleTransaction>();
    private HashMap<Integer, ReturnTransactionImpl> openedReturnTransactions = new HashMap<Integer, ReturnTransactionImpl>();
    private HashMap<Integer, UserImpl> users = new HashMap<Integer, UserImpl>();
	private HashMap<Integer, Customer> customers = new HashMap<Integer, Customer>();
    

    public void reset() {

    }

    @Override
    public Integer createUser(String username, String password, String role) throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		
    	UserImpl us = new UserImpl();
    	
        if(!username.isEmpty() || username!=null)
    	{	
        	//L'username deve essere univoco
        	for(Map.Entry<Integer,UserImpl> entry: users.entrySet())
			{
				if(entry.getValue().getUsername().equals(username))
				{
					return -1;
				}
			}
				
    		if(!password.isEmpty() || password!=null)
    		
    		{
    			if(!role.isEmpty() || role!=null) {

    				us.setUsername(username);
    				us.setPassword(password);
    				us.setRole(role);
    				users.put(us.getId(),us);
    			
    			}
    			else {
    					throw new InvalidRoleException();
    			 	  }
    		}	
    		 else {
    			throw new InvalidPasswordException();
    				}
    	}
        else {
    		throw new InvalidUsernameException();
    		  }
    				
    			return us.getId();
    }

    @Override
    public boolean deleteUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
    	//TODO: gestire eccezione per utente non autorizzato
    	
    	if(id == 0 || id == null) 
    		throw new InvalidUserIdException();
    	
    	users.remove(id);
    	
    	//checking if user has really been removed
    	if(users.containsKey(id)) {
    		return false;
    	}
    	else {
    		return true;
    	}

    }

    @Override
    public List<User> getAllUsers() throws UnauthorizedException {
    	//TODO: gestire eccezione per utente non autorizzato
    	return new ArrayList<User>(users.values());
    }

    @Override
    public User getUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
    	//TODO gestire eccezione per utente non autorizzato
    	
    	if(id <= 0 || id == null)
    		throw new InvalidUserIdException();
    	
    	User us = null;
    	
    	if(users.containsKey(id))
    		us = users.get(id);

    	return us;

    }

    @Override
    public boolean updateUserRights(Integer id, String role) throws InvalidUserIdException, InvalidRoleException, UnauthorizedException {
    	//TODO gestire eccezione per utente non autorizzato
    	if( id <= 0 || id==null)
        	throw new InvalidUserIdException();
       
        if (role.isEmpty() || role == null)
        	throw new InvalidRoleException();
        
        for(UserImpl user : users.values())
        	
        	if(user.getId() == id) {
        		user.setRole(role);
        		}
        	else {
        		return false;
        	}
        
        return true;
    }

    @Override
    public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
        return null;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public Integer createProductType(String description, String productCode, double pricePerUnit, String note) throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
    	//TODO gestire eccezione per utente non autorizzate
    	
        if (description.isEmpty() || description == null)
        	throw new InvalidProductDescriptionException();
        
        if (productCode.isEmpty() || productCode == null)
        	throw new InvalidProductCodeException();

        if (pricePerUnit <= 0 || pricePerUnit == 0)
        	throw new InvalidPricePerUnitException();
        
        //BarCode must be unique
        
		for(ProductType product : products.values())
		{
			if(product.getBarCode().equals(productCode))
			{
				return -1;
			}
		}
		
		ProductType pt = new ProductTypeImpl();
		pt.setBarCode(productCode);
		pt.setProductDescription(description);
		pt.setNote(note);
		pt.setPricePerUnit(pricePerUnit);
		products.put(pt.getBarCode(),pt);

        
        return pt.getId();
    }

    @Override
    public boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote) throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
    	//TODO gestire eccezione per utente non autorizzate
        if (newDescription.isEmpty() || newDescription == null)
        	throw new InvalidProductDescriptionException();
        
        if (newCode.isEmpty() || newCode == null)
        	throw new InvalidProductCodeException();

        if (newPrice <= 0 || newPrice == 0)
        	throw new InvalidPricePerUnitException();
        if (id <= 0 || id == 0)
        	throw new InvalidPricePerUnitException();
        
       for ( ProductType product : products.values())
       {
    	   if(product.getBarCode().equals(newCode))
    	   {
    		   return false;
    	   } 
       }
    	   
    	for ( ProductType product : products.values())
    	{
    		if(product.getId()==id)
    		{
    			product.setBarCode(newCode);
    			product.setProductDescription(newDescription);
    			product.setNote(newNote);
    			product.setPricePerUnit(newPrice);
    		 }
    	 }

    	   return true;
    }

    @Override
    public boolean deleteProductType(Integer id) throws InvalidProductIdException, UnauthorizedException {
    	if(id == 0 || id == null) 
    		throw new InvalidProductIdException();
    	
    	for(Map.Entry<String,ProductType> entry: products.entrySet())
		{
			if(entry.getValue().getId().equals(id))
			{
				products.remove(entry.getKey());
				}
			else {
				return false;
			}
			
		}
    	return true;

    }

    @Override
    public List<ProductType> getAllProductTypes() throws UnauthorizedException {
    	//TODO gestire eccezione per utente non autorizzate
    	return new ArrayList<ProductType>(products.values());
    }

    @Override
    public ProductType getProductTypeByBarCode(String barCode) throws InvalidProductCodeException, UnauthorizedException {
        
    	//TODO:Gestire eccezione per utente non autorizzato
    	if((barCode == null) || (barCode.length() == 0) || (this.barCodeIsValid(barCode)))
    	{
    		for(ProductType product : products.values()) 
    		{
    	        if (product.getBarCode().equals(barCode)) 
    	        {
    	            return product;
    	        }
    	    }
    	}
    	else
    	{
    		throw new InvalidProductCodeException();
    	}
    	
    	return null;
    }

    @Override
    public List<ProductType> getProductTypesByDescription(String description) throws UnauthorizedException {
    	//TODO: gestire eccezione per utente non autorizzato
    	
        List<ProductType> searchedProducts = new ArrayList<ProductType>();
        if (description.length()==0 || description == null) 
        	description = "";
        
        for(ProductType product : products.values())
        {
        	if(product.getProductDescription().contains(description))
        	{
        		searchedProducts.add(product);
        	}
        }
        
        return searchedProducts;
    }

    @Override
    public boolean updateQuantity(Integer productId, int toBeAdded) throws InvalidProductIdException, UnauthorizedException {
        int newQuantity = 0;
    	//TODO: gestire eccezione per utente non autorizzato
        if(productId>0 && productId!=null)
        {
        	for(ProductType product : products.values())
        	{
        		if(product.getId()==productId)
        		{
        			if(product.getLocation().length()!=0 && product.getLocation()!=null)
        			{
        				newQuantity = product.getQuantity() + toBeAdded;
            			if(newQuantity>=0)
            			{
            				product.setQuantity(newQuantity);
            				return true;
            			}
            			
            			return false;
        			}
        			else 
        			{
        				return false;
        			}
        		}
        	}
        }
    	
    	//Non ho trovato il prodotto
    	return false;
    }

    @Override
    public boolean updatePosition(Integer productId, String newPos) throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {
        
    	//TODO: gestire eccezione per utente non autorizzato
    	if(productId>0 && productId!=null)
    	{
    		if(newPos.matches("[0-9]+[-][a-zA-Z]+[-][0-9]+"))
    		{
    			//La location deve essere univoca
				//Scorro tutti i prodotti per vedere se esiste già la locazione
				//Se esiste non è univoca e ritorno false
    			for(ProductType product : products.values())
    			{
    				if(product.getLocation().equals(newPos))
    				{
    					return false;
    				}
    			}
    			
    			//La location è univoca
    			//Aggiorno il prodotto alla nuova locazione
    			for(ProductType product : products.values())
    			{
    				if(product.getId()==productId)
    				{
    					product.setLocation(newPos);
    					return true;
    				}
    			}
    			
    		}
    		else
    		{
    			throw new InvalidLocationException();
    		}
    	}
    	else
    	{
    		throw new InvalidProductIdException();
    	}
    	
    	return false;
    }

    @Override
    public Integer issueOrder(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
        return null;
    }

    @Override
    public Integer payOrderFor(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
        return null;
    }

    @Override
    public boolean payOrder(Integer orderId) throws InvalidOrderIdException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean recordOrderArrival(Integer orderId) throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
        return false;
    }

    @Override
    public List<Order> getAllOrders() throws UnauthorizedException {
        return null;
    }

    @Override
    public Integer defineCustomer(String customerName) throws InvalidCustomerNameException, UnauthorizedException {
        return null;
    }

    @Override
    public boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard) throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean deleteCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
        return false;
    }

    @Override
    public Customer getCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() throws UnauthorizedException {
    	// TODO add check for logged user
        return new ArrayList<Customer>(customers.values());
    }

    @Override
    public String createCard() throws UnauthorizedException {
    	// TODO add check for logged user
    	List<Customer> customersList = getAllCustomers();
    	// If the db is not reachable, return an empty string
    	if(customersList == null)
    		return "";
    	// Else loop until you generate a unique CardId and return it
    	while(true) {
    	String retCard = UUID.randomUUID().toString();
    	if(customersList.stream().filter(c -> !retCard.equals(c.getCustomerCard())).count() == 0)
    		return retCard;
    	}
    }

    @Override
    public boolean attachCardToCustomer(String customerCard, Integer customerId) throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
    	// TODO add check for logged user
    	// Check for exceptions
    	if(customerId == null || customerId <= 0)
    		throw new InvalidCustomerIdException();
    	if(customerCard == null || customerCard.isEmpty())
    		throw new InvalidCustomerCardException();
    	List<Customer> customersList = getAllCustomers();
    	// If the db is not reachable, return an empty string
    	if(customersList == null)
    		return false;
    	// Check if no other customer is using the same card and if the customer exists. If true, assign the card
    	if(customersList.stream().filter(c -> !customerCard.equals(c.getCustomerCard())).count() == 0 && customers.containsKey(customerId)) {
			customers.get(customerId).setCustomerCard(customerCard);
    		return true;
    	}
    	return false;
    }

    @Override
    public boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded) throws InvalidCustomerCardException, UnauthorizedException {
    	// TODO add check for logged user
    	// TODO mars: to be implemented
    	return false;
    }

    @Override
    public Integer startSaleTransaction() throws UnauthorizedException {
    	// TODO add check for logged user
    	// TODO mars: to be implemented
    	return null;
    }

    @Override
    public boolean addProductToSale(Integer transactionId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
    	// TODO add check for logged user
    	// TODO mars: to be implemented
    	return false;
    }

    @Override
    public boolean deleteProductFromSale(Integer transactionId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
    	// TODO add check for logged user
    	// TODO mars: to be implemented
    	return false;
    }

    @Override
    public boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException, UnauthorizedException {
    	// TODO add check for logged user
    	// TODO mars: to be implemented
    	return false;
    }

    @Override
    public boolean applyDiscountRateToSale(Integer transactionId, double discountRate) throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException {
    	// TODO add check for logged user
    	// TODO mars: to be implemented
    	return false;
    }

    @Override
    public int computePointsForSale(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
    	// TODO add check for logged user
    	// TODO mars: to be implemented
        return 0;
    }

    @Override
    public boolean endSaleTransaction(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
    	// TODO add check for logged user
    	// TODO mars: to be implemented
        return false;
    }

    @Override
    public boolean deleteSaleTransaction(Integer saleNumber) throws InvalidTransactionIdException, UnauthorizedException {
    	// TODO add check for logged user
    	// TODO mars: to be implemented
        return false;
    }

    @Override
    public SaleTransaction getSaleTransaction(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
        //TODO Ruggero
    	//TODO gestire eccezione per utente non autorizzato
    	if(transactionId <= 0 || transactionId == null)
    		throw new InvalidTransactionIdException();
    	
    	SaleTransaction st = null;
    	
    	if(closedSaleTransactions.containsKey(transactionId))
    		st = closedSaleTransactions.get(transactionId);

    	return st;
    }

    @Override
    public Integer startReturnTransaction(Integer saleNumber) throws /*InvalidTicketNumberException,*/InvalidTransactionIdException, UnauthorizedException {
        //TODO gestire eccezione per utente non autorizzato
    	if(saleNumber <= 0 || saleNumber == null)
    		throw new InvalidTransactionIdException();
    	
    	if(!closedSaleTransactions.containsKey(saleNumber))
    		return -1;
    	
    	ReturnTransactionImpl rt = new ReturnTransactionImpl();
    	rt.setSaleTransaction(closedSaleTransactions.get(saleNumber));
    	openedReturnTransactions.put(rt.getId(), rt);
    	
    	return rt.getId();
    }

    @Override
    public boolean returnProduct(Integer returnId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
        //TODO gestire eccezione per utente non autorizzato
    	if(returnId <= 0 || returnId == null)
    		throw new InvalidTransactionIdException();
    	
    	if(productCode == null || productCode.isEmpty() || !barCodeIsValid(productCode))
			throw new InvalidProductCodeException();
    	
    	if(amount <= 0)
    		throw new InvalidQuantityException();
    	
    	if(!products.containsKey(productCode) || !openedReturnTransactions.containsKey(returnId))
    		return false;
    	
    	ReturnTransactionImpl rt = openedReturnTransactions.get(returnId);
    	SaleTransaction st = rt.getSaleTransaction();
    	TicketEntry te;
    	
    	try {
    		 te = st.getEntries().stream().filter((TicketEntry t) -> {return t.getBarCode().equals(productCode);}).findFirst().get();
    	}catch(NoSuchElementException e) {
    		return false;
    	}
    	
    	if(te.getAmount() < amount)
    		return false;
    	
    	try {
    		Optional<TicketEntry> ote = rt.getEntry(productCode);
    		if(ote.get().getAmount() + amount > te.getAmount()) {
    			return false;
    		} else {
    			ote.get().setAmount(ote.get().getAmount() + amount);
    		}
    	}catch(NoSuchElementException e) {
        	rt.addEntry(new TicketEntryImpl(productCode, amount));
    	}
    	return true;
    }

    @Override
    public boolean endReturnTransaction(Integer returnId, boolean commit) throws InvalidTransactionIdException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean deleteReturnTransaction(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {
        return false;
    }

    @Override
    public double receiveCashPayment(Integer ticketNumber, double cash) throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException {
        return 0;
    }

    @Override
    public boolean receiveCreditCardPayment(Integer ticketNumber, String creditCard) throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
        return false;
    }

    @Override
    public double returnCashPayment(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {
        return 0;
    }

    @Override
    public double returnCreditCardPayment(Integer returnId, String creditCard) throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
        return 0;
    }

    @Override
    public boolean recordBalanceUpdate(double toBeAdded) throws UnauthorizedException {
        return false;
    }

    @Override
    public List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to) throws UnauthorizedException {
        return null;
    }

    @Override
    public double computeBalance() throws UnauthorizedException {
        return 0;
    }
    
    //Metodo per verificare la validità di un barcode
    // https://www.gs1.org/services/how-calculate-check-digit-manually
    private boolean barCodeIsValid(String barCode) {
    	int bcSize = barCode.length();
    	boolean r = false;
    	if( (bcSize == 12) || (bcSize == 13) || (bcSize == 14) )
    	{
    		int sum = 0;
    		int mul; //Può essere 1 o 3
    		int digit; 
    		switch(bcSize)
    		{
	    		case 12:
	    			for(int i = 0; i < 11; i++)
	    			{
	    				mul = (i%2 == 0) ? 3 : 1;
	    				digit = Integer.parseInt(Character.toString(barCode.charAt(i))); //Estraggo i-esima cifra
	    				digit *= mul; //Moltiplico per 1 o 3
	    				sum += digit; //Sommo
	    			}
	    			sum = RoundUp(sum) - sum;
	    			if(sum == Integer.parseInt(Character.toString(barCode.charAt(11)))){
	    				r = true;
	    			}else {
	    				r = false;
	    			}
	    			break;
	    		case 13:
	    			for(int i = 0; i < 12; i++)
	    			{
	    				mul = (i%2 == 0) ? 1 : 3;
	    				digit = Integer.parseInt(Character.toString(barCode.charAt(i))); //Estraggo i-esima cifra
	    				digit *= mul; //Moltiplico per 1 o 3
	    				sum += digit; //Sommo
	    			}
	    			sum = RoundUp(sum) - sum;
	    			if(sum == Integer.parseInt(Character.toString(barCode.charAt(12)))){
	    				r = true;
	    			}else {
	    				r = false;
	    			}
	    			break;
	    		case 14:
	    			for(int i = 0; i < 13; i++)
	    			{
	    				mul = (i%2 == 0) ? 3 : 1;
	    				digit = Integer.parseInt(Character.toString(barCode.charAt(i))); //Estraggo i-esima cifra
	    				digit *= mul; //Moltiplico per 1 o 3
	    				sum += digit; //Sommo
	    			}
	    			sum = RoundUp(sum) - sum;
	    			if(sum == Integer.parseInt(Character.toString(barCode.charAt(13)))){
	    				r = true;
	    			}else {
	    				r = false;
	    			}
	    			break;
    		}
    		
    	}
    	else
    	{
    		r = false;
    	}
    	
    	return r;
    }
    
    //Metodo per arrotondare al multiplo di 10 successivo
    //Necessario per la validazione del barcode
    private static int RoundUp(int toRound)
    {
        if (toRound % 10 == 0) return toRound;
        return (10 - toRound % 10) + toRound;
    }
}
