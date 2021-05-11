package it.polito.ezshop.data;

import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.model.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EZShop implements EZShopInterface {
	
	/* Sale Transactions */
	private HashMap<Integer, SaleTransaction> openedSaleTransactions = new HashMap<Integer, SaleTransaction>();
	private HashMap<Integer, SaleTransaction> closedSaleTransactions = new HashMap<Integer, SaleTransaction>();
	private HashMap<Integer, SaleTransaction> paidSaleTransactions = new HashMap<Integer, SaleTransaction>();
	/* Return Transactions */
    private HashMap<Integer, ReturnTransactionImpl> openedReturnTransactions = new HashMap<Integer, ReturnTransactionImpl>();
    private HashMap<Integer, ReturnTransactionImpl> closedReturnTransactions = new HashMap<Integer, ReturnTransactionImpl>();
    private HashMap<Integer, ReturnTransactionImpl> paidReturnTransactions = new HashMap<Integer, ReturnTransactionImpl>();
    /* Orders and products */
	private HashMap<Integer, Order> orders = new HashMap<Integer, Order>();
	private HashMap<String, ProductType> products = new HashMap<String, ProductType>();
    /* Balance operation */
    private HashMap<Integer, BalanceOperation> balanceOperations = new HashMap<Integer, BalanceOperation>();
    /* Users and customers */
    private HashMap<Integer, User> users = new HashMap<Integer, User>();
	private HashMap<Integer, Customer> customers = new HashMap<Integer, Customer>();

	
	/*
     Questa variabile rappresenta il bilancio corrente del sistema (=/= balanceOperation che invece rappresenta una singola operazione)
     Va inizializzata (=0) dentro il metodo reset() 
     */
    private double balance = 0;
    private User loggedUser=null;


    public void reset() {
    	
    	this.balance = 0;
    	openedSaleTransactions.clear();
    	closedSaleTransactions.clear();
    	paidSaleTransactions.clear();
    	openedReturnTransactions.clear();
    	closedReturnTransactions.clear();
    	paidReturnTransactions.clear();
    	balanceOperations.clear();
    	users.clear();
    	customers.clear();
    	orders.clear();
    	products.clear();

    }

	@Override
	public Integer createUser(String username, String password, String role) throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
	
		if(username == null ||username.isEmpty())
			throw new InvalidUsernameException();
	
	
		if(password == null || password.isEmpty())
			throw new InvalidPasswordException();
	 
		if((role == null ||role.isEmpty()) && !role.equals("Administrator") && !role.equals("Cashier") && !role.equals("ShopManager") )
			throw new InvalidRoleException();
	
	
		//L'username deve essere univoco
		for(User user : users.values())
		{
			if(user.getUsername().equals(username))
			{
				return -1;
			}
		}
		
		User us = new UserImpl();
		us.setUsername(username);
		us.setPassword(password);
		us.setRole(role);
		users.put(us.getId(),us);
	
		return us.getId();
	}
	
	@Override
	public boolean deleteUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
		
		if(this.loggedUser==null ||!this.loggedUser.getRole().equals("Administrator"))
			throw new UnauthorizedException();
		
		if(id == null || id <= 0) 
			throw new InvalidUserIdException();
		
		if(users.containsKey(id)) {
			users.remove(id);
			return true;
		} else {
			return false;
		}
		
	}

    @Override
    public List<User> getAllUsers() throws UnauthorizedException {

    	if(this.loggedUser==null || !this.loggedUser.getRole().equals("Administrator"))
    		throw new UnauthorizedException();
    	
    	return new ArrayList<User>(users.values());
    }

    @Override
    public User getUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
  
    	if(this.loggedUser==null || !this.loggedUser.getRole().equals("Administrator") )
    		throw new UnauthorizedException();
    	
    	if(id == null || id <= 0)
    		throw new InvalidUserIdException();
    	
    	for(User user : users.values()) {
    		
    		if (user.getId() == id) {
    	    	return user;
    		}
    	}
    	
    	return null;
    	
	}

    @Override
    public boolean updateUserRights(Integer id, String role) throws InvalidUserIdException, InvalidRoleException, UnauthorizedException {
    	
    	if(this.loggedUser==null || !this.loggedUser.getRole().equals("Administrator"))
    		throw new UnauthorizedException();
    	
    	if(id==null ||  id <= 0)
        	throw new InvalidUserIdException();
       
        if ((role == null || role.isEmpty()) && !role.equals("Administrator") && !role.equals("Cashier") && !role.equals("ShopManager") )
        	throw new InvalidRoleException();
        
        for(User user : users.values()) {
        	
        	if(user.getId() == id) {
        		user.setRole(role);
                return true;
        	}
        }

        return false;
    }
    


    @Override
    public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
    	
    	if(username == null || username.isEmpty())
    		throw new InvalidUsernameException();
    	
    	if(password == null || password.isEmpty())
    		throw new InvalidPasswordException();
    	
    	for(User user : users.values())
		{
			if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
				this.loggedUser=user;
				return loggedUser;
			}
		}
    	
    	return null;
    }

    @Override
    public boolean logout() {
    	
    	if(this.loggedUser == null)
    		return false;
    	else {
    		this.loggedUser = null;
    		return true;
    	}
    }

    @Override
    public Integer createProductType(String description, String productCode, double pricePerUnit, String note) throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
    	if(this.loggedUser==null || this.loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
        if (description == null || description.isEmpty())
        	throw new InvalidProductDescriptionException();
        
        if (productCode == null || productCode.isEmpty() || barCodeIsValid(productCode) == false)
        	throw new InvalidProductCodeException();

        if (pricePerUnit <= 0)
        	throw new InvalidPricePerUnitException();
        
        //BarCode must be unique        
        if(products.containsKey(productCode))
        	return -1;
		
		ProductType pt = new ProductTypeImpl();
		pt.setBarCode(productCode);
		pt.setProductDescription(description);
		pt.setPricePerUnit(pricePerUnit);
		
		if(note == null) {
			pt.setNote("");
		}else {
			pt.setNote(note);
		}

		products.put(pt.getBarCode(),pt);

        return pt.getId();
    }

    @Override
    public boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote) throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
    	
    	if(this.loggedUser==null || this.loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
        if (newDescription == null || newDescription.isEmpty())
        	throw new InvalidProductDescriptionException();
        
        if (newCode == null || newCode.isEmpty() || barCodeIsValid(newCode) == false)
        	throw new InvalidProductCodeException();

        if (newPrice <= 0)
        	throw new InvalidPricePerUnitException();
        
        if ( id == null || id <= 0)
        	throw new InvalidProductIdException();
        
        if(products.containsKey(newCode))
        	return false;
        
        if(products.size()!=0) 
        {
    	   
        	for ( ProductType product : products.values())
        	{
        		if(product.getId() == id)
        		{
        			product.setBarCode(newCode);
        			product.setProductDescription(newDescription);
        			product.setNote(newNote);
        			product.setPricePerUnit(newPrice);
        			return true;
        		}
        	}
        }
    		
		return false;

    }

    @Override
    public boolean deleteProductType(Integer id) throws InvalidProductIdException, UnauthorizedException {
    	
    	if(this.loggedUser==null || this.loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(id == null || id <= 0) 
    		throw new InvalidProductIdException();
    	
		if(products.size()!=0)
		{
			for(ProductType product : products.values())
			{
				if(product.getId() == id)
				{	
					products.remove(product.getBarCode());
					return true;
				
				}
			}
		}
    	
    	return false;
    }

    @Override
    public List<ProductType> getAllProductTypes() throws UnauthorizedException {
    	
    	if(this.loggedUser==null || this.loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	return new ArrayList<ProductType>(products.values());
    }

    @Override
    public ProductType getProductTypeByBarCode(String barCode) throws InvalidProductCodeException, UnauthorizedException {
        
    	if(this.loggedUser==null || this.loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if((barCode == null) || (barCode.length() == 0) || (!barCodeIsValid(barCode)))
    		throw new InvalidProductCodeException();
    	    	
		return products.get(barCode);

    }

    @Override
    public List<ProductType> getProductTypesByDescription(String description) throws UnauthorizedException {
    	
    	if(this.loggedUser==null || this.loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
        List<ProductType> searchedProducts = new ArrayList<ProductType>();
        if (description == null || description.length()==0) 
        	description = "";
        
        if(products.size()!=0)
        {
        	 for(ProductType product : products.values())
             {
             	if(product.getProductDescription().contains(description))
             	{
             		searchedProducts.add(product);
             	}
             }
        }
       
        return searchedProducts;
    }

    @Override
    public boolean updateQuantity(Integer productId, int toBeAdded) throws InvalidProductIdException, UnauthorizedException {
    	
        int newQuantity = 0;
        
        if(this.loggedUser==null || this.loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
        
        if(productId!=null && productId>0)
        {
        	for(ProductType product : products.values())
        	{
        		if(product.getId()==productId)
        		{
        			
        			if(product.getLocation()==null)
        				return false;
        			
        			if(product.getLocation().isEmpty())
        				return false;
        			
        			
        			if(product.getQuantity()!=null)	
        				newQuantity = product.getQuantity() + toBeAdded;
        			else
        				newQuantity = toBeAdded;
    				
        			if(newQuantity>=0)
        			{
        				ProductType p = products.get(product.getBarCode());
        				p.setQuantity(newQuantity);
        				return true;
        			}
        			else
        			{
        				return false;
        			}
            			
        		}
        	}
        }
        else
        {
        	throw new InvalidProductIdException();
        }
    	
    	//Non ho trovato il prodotto
        //Il prodotto non esiste
    	return false;
    }

    @Override
    public boolean updatePosition(Integer productId, String newPos) throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {
        
    	if(this.loggedUser==null || this.loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(productId>0 && productId!=null)
    	{
    		if(newPos.matches("[0-9]+[-][a-zA-Z]+[-][0-9]+"))
    		{
    			if(products.size()!=0)
    			{
	    			//La location deve essere univoca
					//Scorro tutti i prodotti per vedere se esiste già la locazione
					//Se esiste non è univoca e ritorno false
	    			for(ProductType product : products.values())
	    			{
	    				if(product.getLocation()!=null)
	    				{
	    					if(product.getLocation().equals(newPos))
		    				{
		    					return false;
		    				}
	    				}
	    				
	    			}
	    			
	    			//La location è univoca
	    			//Aggiorno il prodotto alla nuova locazione
	    			for(ProductType product : products.values())
	    			{
	    				if(product.getId()==productId)
	    				{
	    					ProductType p = products.get(product.getBarCode());
	        				p.setLocation(newPos);
	        				return true;
	    				}
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
        
    	if(this.loggedUser==null || this.loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if((productCode != null) && (productCode.length() != 0) && (barCodeIsValid(productCode)))
    	{
    		if(quantity>0)
    		{
    			if(pricePerUnit>0)
    			{
    				//Controllo che il prodotto sia in inventario
    				//Altrimenti torno -1
    				if(!products.containsKey(productCode))
    					return -1;
    				
    				//Creo nuovo ordine e lo aggiungo alla lista
					Order o = new OrderImpl(productCode, pricePerUnit, quantity);
					orders.put(o.getOrderId(), o);
					return o.getOrderId();
    			}
    			else
    			{
    				throw new InvalidPricePerUnitException();
    			}
    		}
    		else
    		{
    			throw new InvalidQuantityException();
    		}
    	}
    	else
    	{
    		throw new InvalidProductCodeException();
    	}
    	
    }

    @Override
    public Integer payOrderFor(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
        
    	if(this.loggedUser==null || this.loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if((productCode != null) && (productCode.length() != 0) && (barCodeIsValid(productCode)))
    	{
    		if(quantity>0)
    		{
    			if(pricePerUnit>0)
    			{
    				if(!products.containsKey(productCode))
    					return -1;
					
					if(this.balance >= (quantity*pricePerUnit))
		    		{
		    			//Creo nuovo ordine e lo aggiungo alla lista
						Order o = new OrderImpl(productCode, pricePerUnit, quantity);
						o.setStatus("PAYED");
						orders.put(o.getOrderId(), o);
		    			//Decremento bilancio
		    			this.balance -= quantity*pricePerUnit;
		    			return o.getOrderId();
		    		}
		    		else
		    		{
		    			return -1;
		    		}
					
    			}
    			else
    			{
    				throw new InvalidPricePerUnitException();
    			}
    		}
    		else
    		{
    			throw new InvalidQuantityException();
    		}
    	}
    	else
    	{
    		throw new InvalidProductCodeException();
    	}
    	
    }

    @Override
    public boolean payOrder(Integer orderId) throws InvalidOrderIdException, UnauthorizedException {
        
    	if(this.loggedUser==null || this.loggedUser.getRole().contains("Cashier"))
    		throw new UnauthorizedException();
    	
    	if((orderId!=null) && (orderId>=0))
    	{
    		if(orders.containsKey(orderId))
    		{
    			//L'ordine è presente
    			if(orders.get(orderId).getStatus().equals("ISSUED"))
    			{
    				    				
    				//L'ordine è in stato ISSUED quindi va pagato
    				double pricePerUnit = orders.get(orderId).getPricePerUnit();
    				int quantity = orders.get(orderId).getQuantity();
    				if(this.balance >= (quantity*pricePerUnit))
    				{
    					orders.get(orderId).setStatus("PAYED");
        				this.balance -= quantity*pricePerUnit;
        				return true;
    				}
    				else
    				{
    					return false;
    				}
    				
    			}
    			else
    			{
    				return false;
    			}
    			
    		}
    		else
    		{
    			return false;
    		}
    	}
    	else
    	{
    		throw new InvalidOrderIdException();
    	}
    	
    }

    @Override
    public boolean recordOrderArrival(Integer orderId) throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
        
    	if(this.loggedUser==null || this.loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if((orderId!=null) && (orderId>0))
    	{
    		if(orders.containsKey(orderId))
    		{
	    		ProductType p = products.get(orders.get(orderId).getProductCode());
	    		Order o = orders.get(orderId);
	    		if((p.getLocation()!=null) && (!p.getLocation().isEmpty()))
	    		{
	    			if(orders.get(orderId).getStatus().equals("PAYED"))
	    			{
	    				//L'ordine è stato pagato ed è arrivato
	    				//Aumento la quantità del prodotto e imposto lo stato dell'ordine a completato
	    				String barCode = p.getBarCode();
	    				int oldQuantity;
	    				
	    				if(p.getQuantity()==null)
	    					oldQuantity = 0;
	    				else 
	    					oldQuantity = p.getQuantity();
	    				
	    				int quantityToAdd = o.getQuantity();
	    				products.get(barCode).setQuantity(oldQuantity + quantityToAdd);
	    				orders.get(orderId).setStatus("COMPLETED");
	    				return true;
	    			}
	    			else if(orders.get(orderId).getStatus().equals("COMPLETED"))
	    			{
	    				//Se l'ordine è stato già completato il metodo non fa nulla
	    				return true;
	    			}
	    			else
	    			{
	    				//L'ordine non è né in stato PAYED né in stato completed
	    				return false;
	    			}
	    		}
	    		else
	    		{
	    			//L'ordine non ha una locazione
	    			throw new InvalidLocationException();
	    		}
    		}
    		else
    		{
    			//L'ordine non esiste
    			return false;
    		}
    	}
    	else
    	{
    		throw new InvalidOrderIdException();
    	}
    	
    }

    @Override
    public List<Order> getAllOrders() throws UnauthorizedException {
    	
    	if(this.loggedUser==null || this.loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();   	
    	
        return new ArrayList<Order>(orders.values());
    }

    @Override
    public Integer defineCustomer(String customerName) throws InvalidCustomerNameException, UnauthorizedException {


    	if(this.loggedUser==null)
    		throw new UnauthorizedException();
    	
    	if(customers.size()!=0)
    	{
    		for(Customer customer : customers.values())
        	{
        		//Devo controllare che il nome che sto inserendo non sia già di un altro customer
        		//deve essere univoco
        		if(customer.getCustomerName().equals(customerName))
        		{
        			return -1;
        		}
        	}
        	//Il nome è univoco
    	}
    	
    	
    	if((customerName!=null) && (!customerName.isEmpty()))
    	{
    		Customer customer = new CustomerImpl(customerName);
    		customers.put(customer.getId(), customer);
    		return customer.getId();
    	}
    	else
    	{
    		throw new InvalidCustomerNameException();
    	}
    }

    @Override
    public boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard) throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
        
    	if(this.loggedUser==null)
    		throw new UnauthorizedException();
    	
    	//Il nome non deve essere nullo o vuoto
    	if((newCustomerName==null) || (newCustomerName.isEmpty()))
    		throw new InvalidCustomerNameException();
    	//Il nome deve essere unico (dovrebbe, dalla documentazione del metodo non si capisce ma nel precedente metodo era così)
    	if(customers.size()!=0)
    	{
    		for(Customer customer : customers.values())
        	{
        		//Devo scorrere tutti i clienti tranne quello attuale, sennò il nome risulterà sempre duplicato (nel caso in cui non venisse modificato)
        		if(customer.getId()!=id)
        		{
            		//Devo controllare che il nome che sto inserendo non sia già di un altro customer
            		//deve essere univoco
        			if(customer.getCustomerName().equals(newCustomerName))
            		{
            			return false;
            		}
        			
        		}
        	}
    	
    	
	    	//Aggiorno il nome del cliente
			customers.get(id).setCustomerName(newCustomerName);
	    	
	    	//Se la nuova carta è nulla si conclude qui l'operazione
	    	if(newCustomerCard==null)
	    		return true;
	    	//Se la nuova carta è una stringa vuota si stacca la carta dal cliente
	    	if(newCustomerCard.isEmpty())
	    	{
	    		customers.get(id).setCustomerCard(null);
	    		return true;
	    	}
	    	//Se la nuova carta è del formato corretto (10 digits) possiamo procedere all'aggiornamento
	    	if(newCustomerCard.matches("[0-9]{10}")) 
	    	{
	    		//controllo che il codice della carta non sia già di qualche utente
	    		for(Customer customer : customers.values())
	    		{
	    			//Se la carta è già in possesso di qualcuno ritorno falso
	    			if(customer.getCustomerCard().equals(newCustomerCard))
	    			{
	    				return false;
	    			}
	    		}
	    		//La carta non è in possesso di nessun cliente
	    		//Aggiorno il cliente
	    		customers.get(id).setCustomerCard(newCustomerCard);
	    		return true;
	        	
	    	}
	    	else
	    	{
	    		throw new InvalidCustomerCardException();
	    	}
    	}
    	
    	return false;
    		
    }

    @Override
    public boolean deleteCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
    	
    	if(this.loggedUser==null)
    		throw new UnauthorizedException();
    	
    	if((id!=null) && (id>0))
    	{
    		if(customers.containsKey(id))
    		{
    			//Rimuovo il customer
    			customers.remove(id);
    			return true;
    		}
    		else
    		{
    			//Il customer non esiste
    			return false;
    		}
    	}
    	else
    	{
    		throw new InvalidCustomerIdException();
    	}
    }

    @Override
    public Customer getCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	if(id == null || id <= 0)
    		throw new InvalidCustomerIdException();

    	return customers.get(id);
    }

    @Override
    public List<Customer> getAllCustomers() throws UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	return new ArrayList<Customer>(customers.values());
    }

    @Override
    public String createCard() throws UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
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
    	// Check for exceptions
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(customerId == null || customerId <= 0)
    		throw new InvalidCustomerIdException();
    	
    	if(customerCard == null || customerCard.isEmpty())
    		throw new InvalidCustomerCardException();
    	
    	List<Customer> customersList = getAllCustomers();
    	// If the db is not reachable, return an empty string
    	if(customersList == null)
    		return false;
    	// Check if no other customer is using the same card and if the customer exists. If true, assign the card
    	if(customersList.stream().filter(c -> customerCard.equals(c.getCustomerCard())).count() == 0 && customers.containsKey(customerId)) {
			customers.get(customerId).setCustomerCard(customerCard);
    		return true;
    	}
    	return false;
    }

    @Override
    public boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded) throws InvalidCustomerCardException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(customerCard == null || customerCard.isEmpty())
    		throw new InvalidCustomerCardException();
    	
    	List<Customer> customersList = getAllCustomers();
    	// If the db is not reachable, return an empty string
    	if(customersList == null)
    		return false;
    	
    	if(customersList.stream().filter(c -> customerCard.equals(c.getCustomerCard())).count() == 1) {
    		Customer tmp = customersList.stream().filter(c -> customerCard.equals(c.getCustomerCard()))
    							.findFirst().get();
    		if(tmp.getPoints() + pointsToBeAdded >= 0) {
    			tmp.setPoints(tmp.getPoints() + pointsToBeAdded);
    			return true;
    		}		
    	}
    	return false;
    }

    @Override
    public Integer startSaleTransaction() throws UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	Integer max;
    	if(openedSaleTransactions.isEmpty()) {
    		if(closedSaleTransactions.isEmpty()) {
    			if(paidSaleTransactions.isEmpty()) {
    				max = 1;
    			} else {
    				max = Collections.max(paidSaleTransactions.keySet());
    			}
    		} else {
    			max = Collections.max(closedSaleTransactions.keySet());
    		}    			
    	} else {
    		max = Collections.max(openedSaleTransactions.keySet());
    	}
    	SaleTransaction sale = new SaleTransactionImpl();
    	sale.setTicketNumber(max+1);
    	openedSaleTransactions.put(max+1, sale);
    	return (max+1);
    }

    @Override
    public boolean addProductToSale(Integer transactionId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(transactionId <= 0 || transactionId == null)
    		throw new InvalidTransactionIdException();
    	
    	if(productCode.isEmpty() || productCode == null || !barCodeIsValid(productCode))
    		throw new InvalidProductCodeException();
    	
    	if(amount < 0)
    		throw new InvalidQuantityException();
    	
		ProductType refProd = products.get(productCode);
		if(refProd == null || refProd.getQuantity() < amount)
			return false;
		SaleTransaction sale = openedSaleTransactions.get(transactionId);
		if(sale == null)
			return false;
		
		List<TicketEntry> entries = sale.getEntries();
		TicketEntry productToInsert = new TicketEntryImpl(productCode, amount);
		productToInsert.setProductDescription(refProd.getProductDescription());
		productToInsert.setPricePerUnit(refProd.getPricePerUnit());
		
		entries.add(productToInsert);
		sale.setPrice(sale.getPrice() + amount * productToInsert.getPricePerUnit());
		
		return true;
    }

    @Override
    public boolean deleteProductFromSale(Integer transactionId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(transactionId == null || transactionId <= 0)
    		throw new InvalidTransactionIdException();
    	
    	if(productCode == null || productCode.isEmpty() || !barCodeIsValid(productCode))
    		throw new InvalidProductCodeException();
    	
    	if(amount < 0)
    		throw new InvalidQuantityException();

    	ProductType refProd = products.get(productCode);
		if(refProd == null || refProd.getQuantity() < amount)
			return false;
		SaleTransaction sale = openedSaleTransactions.get(transactionId);
		if(sale == null)
			return false;
		
		List<TicketEntry> entries = sale.getEntries();
		entries.stream().filter(e -> productCode.equals(e.getBarCode())).findFirst()
				.ifPresent(e -> { if(e.getAmount() - amount > 0){
										e.setAmount(e.getAmount() - amount);
										sale.setPrice(sale.getPrice() - amount * refProd.getPricePerUnit() *(1 - e.getDiscountRate()));
								} else {
										entries.remove(e);
										sale.setPrice(sale.getPrice() - e.getAmount() * refProd.getPricePerUnit() * (1 - e.getDiscountRate()));
								}});
		
    	return true;
    }

    @Override
    public boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(transactionId == null || transactionId <= 0)
    		throw new InvalidTransactionIdException();
    	
    	if(productCode == null || productCode.isEmpty() || !barCodeIsValid(productCode))
    		throw new InvalidProductCodeException();
    	
    	if(discountRate < 0.0 || discountRate > 1.0)
    		throw new InvalidDiscountRateException ();
    	
		if(products.get(productCode) == null)
			return false;
		
		SaleTransaction sale = openedSaleTransactions.get(transactionId);
		if(sale == null)
			return false;
		
		List<TicketEntry> entries = sale.getEntries();
		Optional<TicketEntry> tmp = entries.stream().filter(e -> productCode.equals(e.getBarCode())).findFirst();
    	if(tmp.isPresent()) {
    		tmp.get().setDiscountRate(discountRate);
    		return true;
    	}
    	
    	return false;
    }

    @Override
    public boolean applyDiscountRateToSale(Integer transactionId, double discountRate) throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(transactionId == null || transactionId <= 0 )
    		throw new InvalidTransactionIdException();
    	if(discountRate < 0.0 || discountRate > 1.0)
    		throw new InvalidDiscountRateException ();
    	
    	SaleTransaction sale = openedSaleTransactions.get(transactionId);
		if(sale == null)
			return false;
		
		sale.setDiscountRate(discountRate);
    	
    	return true;
    }

    @Override
    public int computePointsForSale(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(transactionId == null || transactionId <= 0)
    		throw new InvalidTransactionIdException();
    	
    	SaleTransaction sale = openedSaleTransactions.get(transactionId);
		if(sale == null) {
			sale = closedSaleTransactions.get(transactionId);
			if(sale == null) {
				sale = paidSaleTransactions.get(transactionId);
				if(sale == null)
					return -1;
			}
		}
		
		Integer retPoints = sale.getEntries().stream().mapToInt(p -> (int)(p.getAmount() * p.getPricePerUnit())).sum()/10;
    	
        return retPoints;
    }

    @Override
    public boolean endSaleTransaction(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(transactionId == null || transactionId <= 0)
    		throw new InvalidTransactionIdException();
    	
    	SaleTransaction sale = openedSaleTransactions.get(transactionId);
		if(sale == null)
			return false;
		
		if(closedSaleTransactions.putIfAbsent(transactionId, sale) != null)
			return false;
		if(openedSaleTransactions.remove(transactionId) == null)
			return false;
    	
        return true;
    }

    @Override
    public boolean deleteSaleTransaction(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(transactionId == null || transactionId <= 0)
    		throw new InvalidTransactionIdException();
    	
    	SaleTransaction sale = openedSaleTransactions.get(transactionId);
		if(sale == null) {
			sale = closedSaleTransactions.get(transactionId);
			if(sale == null) {
				return false;
			} else {
				closedSaleTransactions.remove(transactionId);
			}				
		} else {
			openedSaleTransactions.remove(transactionId);
		}
		
        return true;
    }

    @Override
    public SaleTransaction getSaleTransaction(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if( transactionId == null|| transactionId <= 0 )
    		throw new InvalidTransactionIdException();

    	return closedSaleTransactions.get(transactionId);
    }

    @Override
    public Integer startReturnTransaction(Integer saleNumber) throws /*InvalidTicketNumberException,*/InvalidTransactionIdException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(saleNumber == null || saleNumber <= 0)
    		throw new InvalidTransactionIdException();
    	
    	if(!paidSaleTransactions.containsKey(saleNumber))
    		return -1;
    	
    	ReturnTransactionImpl rt = new ReturnTransactionImpl();
    	rt.setSaleTransaction(paidSaleTransactions.get(saleNumber));
    	openedReturnTransactions.put(rt.getId(), rt);
    	
    	return rt.getId();
    }

    @Override
    public boolean returnProduct(Integer returnId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(returnId == null || returnId <= 0)
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
    			ote.get().setAmount(te.getAmount());
    		} else {
    			Integer newAmount = ote.get().getAmount() + amount;
    			ote.get().setAmount(newAmount);
    		}
    	}catch(NoSuchElementException e) {
        	rt.addEntry(new TicketEntryImpl(productCode, amount, te.getPricePerUnit(), te.getDiscountRate()));
    	}
    	return true;
    }

    @Override
    public boolean endReturnTransaction(Integer returnId, boolean commit) throws InvalidTransactionIdException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(returnId == null || returnId <= 0)
    		throw new InvalidTransactionIdException();
    	
    	if(!openedReturnTransactions.containsKey(returnId))
    		return false;
    	
    	if(commit == false) {
    		openedReturnTransactions.remove(returnId);
    		return true;
    	}
    	
    	ReturnTransactionImpl rt = openedReturnTransactions.get(returnId);
    	
    	for(TicketEntry te : rt.getEntries()) {
    		ProductType p = products.get(te.getBarCode());
    		p.setQuantity(p.getQuantity() + te.getAmount());
    		
    		SaleTransaction st = rt.getSaleTransaction();
    		TicketEntry tSale = st.getEntries().stream().filter((TicketEntry t) -> t.getBarCode().equals(te.getBarCode())).findFirst().get();
    		
    		st.setPrice(st.getPrice() - te.getAmount()*te.getPricePerUnit()*(1-te.getDiscountRate())*(1-st.getDiscountRate()));
    		rt.setPrice(rt.getPrice() + te.getAmount()*te.getPricePerUnit()*(1-te.getDiscountRate())*(1-st.getDiscountRate()));
    		tSale.setAmount(tSale.getAmount() - te.getAmount());
    		
    	}
    	
    	openedReturnTransactions.remove(returnId);
    	closedReturnTransactions.put(returnId, rt);
    	return true;
    }

    @Override
    public boolean deleteReturnTransaction(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if( returnId == null || returnId <= 0)
        	throw new InvalidTransactionIdException();
        
        if(!closedReturnTransactions.containsKey(returnId))
        	return false;
        
        ReturnTransactionImpl rt = closedReturnTransactions.get(returnId);
        
        if(paidReturnTransactions.containsKey(returnId))
        	return false;
        
    	for(TicketEntry te : rt.getEntries()) {
    		ProductType p = products.get(te.getBarCode());
    		p.setQuantity(p.getQuantity() - te.getAmount());
    		
    		SaleTransaction st = rt.getSaleTransaction();
    		TicketEntry tSale = st.getEntries().stream().filter((TicketEntry t) -> t.getBarCode().equals(te.getBarCode())).findFirst().get();
    		st.setPrice(st.getPrice() + te.getAmount()*te.getPricePerUnit()*(1-te.getDiscountRate())*(1-st.getDiscountRate()));
    		tSale.setAmount(tSale.getAmount() + te.getAmount());
    	}
        closedReturnTransactions.remove(returnId);
        return true;
    }

    @Override
    public double receiveCashPayment(Integer ticketNumber, double cash) throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(ticketNumber == null || ticketNumber <= 0)
    		throw new InvalidTransactionIdException();
    	if(cash <= 0)
    		throw new InvalidPaymentException();
    	
    	if(!closedSaleTransactions.containsKey(ticketNumber))
    		return -1;
    	
    	SaleTransaction st = closedSaleTransactions.get(ticketNumber);
    	
    	if(cash < st.getPrice())
    		return -1;
    	
    	closedSaleTransactions.remove(ticketNumber);
    	paidSaleTransactions.put(ticketNumber, st);
    	this.balance += st.getPrice();
    	return st.getPrice() - cash;
    }

    @Override
    public boolean receiveCreditCardPayment(Integer ticketNumber, String creditCard) throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(ticketNumber == null || ticketNumber <= 0)
        	throw new InvalidTransactionIdException();
    	
        if(!creditCardIsValid(creditCard))
        	throw new InvalidCreditCardException();
        
        /* TODO credit card handling
         * if(!creditsCard.contains(creditCard)
         * 		return false;
         * 
         * here something to check credit card balance and check if it is registered
         *
         */
        
        if(!closedSaleTransactions.containsKey(ticketNumber))
    		return false;
        
        SaleTransaction st = closedSaleTransactions.get(ticketNumber);
    	closedSaleTransactions.remove(ticketNumber);
    	paidSaleTransactions.put(ticketNumber, st);
    	this.balance += st.getPrice();
    	return true;
    }

    @Override
    public double returnCashPayment(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();
    	
    	if(returnId == null || returnId <= 0)
        	throw new InvalidTransactionIdException();
        
    	if(!closedReturnTransactions.containsKey(returnId))
    		return -1;
    	
    	ReturnTransactionImpl rt = closedReturnTransactions.get(returnId);
    	closedReturnTransactions.remove(rt.getId());
    	paidReturnTransactions.put(returnId, rt);
    	this.balance -= rt.getPrice();
    	return rt.getPrice();
    }

    @Override
    public double returnCreditCardPayment(Integer returnId, String creditCard) throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager") && !loggedUser.getRole().equals("Cashier"))
    		throw new UnauthorizedException();

    	if(returnId <= 0)
        	throw new InvalidTransactionIdException();
    	
        if(!creditCardIsValid(creditCard))
        	throw new InvalidCreditCardException();
        
        /* TODO credit card handling
         * if(!creditsCard.contains(creditCard)
         * 		return false;
         * 
         * here something to check credit card balance and check if it is registered
         *
         */
        
        if(openedReturnTransactions.containsKey(returnId) || !closedReturnTransactions.containsKey(returnId) || paidReturnTransactions.containsKey(returnId))
        	return -1;
        
        ReturnTransactionImpl rt = closedReturnTransactions.get(returnId);
    	closedReturnTransactions.remove(rt.getId());
    	paidReturnTransactions.put(returnId, rt);
    	this.balance -= rt.getPrice();
    	return rt.getPrice();
    }

    @Override
    public boolean recordBalanceUpdate(double toBeAdded) throws UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager"))
    		throw new UnauthorizedException();
    	
    	if(toBeAdded + this.balance < 0)
    		return false;
    	
    	BalanceOperation bp = new BalanceOperationImpl();
    	bp.setMoney(toBeAdded);
    	balanceOperations.put(bp.getBalanceId(), bp);
    	
    	this.balance += toBeAdded;
        return true;
    }

    @Override
    public List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to) throws UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager"))
    		throw new UnauthorizedException();
    	
    	final LocalDate fromFinal;
    	final LocalDate toFinal;
    	Collection<BalanceOperation> orders = this.orders.values().stream().filter((Order o) -> o.getStatus().equals("PAYED")).map(OrderImpl.mapToBalanceOperation()).collect(Collectors.toSet());
    	Collection<BalanceOperation> sales = this.paidSaleTransactions.values().stream().map(SaleTransactionImpl.mapToBalanceOperation()).collect(Collectors.toSet());
    	Collection<BalanceOperation> returns = this.paidReturnTransactions.values().stream().map(ReturnTransactionImpl.mapToBalanceOperation()).collect(Collectors.toSet());
    	Collection<BalanceOperation> balanceOperations = this.balanceOperations.values();
    	
    	List<BalanceOperation> creditsAndDebits = Stream.concat(orders.stream(),
    													Stream.concat(sales.stream(),
    													Stream.concat(returns.stream(),
    													balanceOperations.stream()))).collect(Collectors.toList());
    	if((from != null && to != null) && from.isAfter(to)) {
			toFinal = from; 
			fromFinal = to;
    	}else {
    		fromFinal = from;
    		toFinal = to;
    	}
    	
    	creditsAndDebits = creditsAndDebits.stream().filter((BalanceOperation bo) -> {
    		LocalDate date = bo.getDate();
    		if(fromFinal != null && toFinal != null) {
    			return (fromFinal.isBefore(date) || fromFinal.equals(date)) && (toFinal.isAfter(date) || toFinal.equals(date));
    		}
    		
    		if(fromFinal == null) {
    			return toFinal.isAfter(date) || toFinal.equals(date);
    		}
    		
    		return fromFinal.isBefore(date) || toFinal.equals(date);
    	}).collect(Collectors.toList());
    	return creditsAndDebits;
    }

    @Override
    public double computeBalance() throws UnauthorizedException {
    	if(loggedUser != null && !loggedUser.getRole().equals("Administrator") && !loggedUser.getRole().equals("ShopManager"))
    		throw new UnauthorizedException();
        return this.balance;
    }
    
    //Metodo per verificare la validità di un barcode
    // https://www.gs1.org/services/how-calculate-check-digit-manually
    private static boolean barCodeIsValid(String barCode) {
    	int bcSize = barCode.length();
    	boolean r = false;
    	
    	if(barCode.matches("[0-9]{12,14}"))
    	{
    		//Il bar code deve essere una stringa composta da numeri
    	
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
    
    // https://www.geeksforgeeks.org/luhn-algorithm/
    private static boolean creditCardIsValid(String creditCard) {
    	if(creditCard == null || creditCard.isEmpty())
    		return false;
    	
	    int nDigits = creditCard.length();
	 
	    int nSum = 0;
	    boolean isSecond = false;
	    for (int i = nDigits - 1; i >= 0; i--)
	    {
	 
	        int d = creditCard.charAt(i) - '0';
	 
	        if (isSecond == true)
	            d = d * 2;
	 
	        // We add two digits to handle
	        // cases that make two digits
	        // after doubling
	        nSum += d / 10;
	        nSum += d % 10;
	 
	        isSecond = !isSecond;
	    }
	    return (nSum % 10 == 0);
    }
}
