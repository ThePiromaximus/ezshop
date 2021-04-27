# Design Document 


Authors: Angela D'Antonio, Gabriele Inzerillo, Ruggero Nocera, Marzio Vallero

Date: 26/04/2021

Version: 1.0


# Contents

- [High level design](#package-diagram)
- [Low level design](#class-diagram)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)

# Instructions

The design must satisfy the Official Requirements document, notably functional and non functional requirements

# High level design 

<discuss architectural styles used, if any>
<report package diagram>

```plantuml
@startuml
package EZShop as EZS
package EZShopData as Data
package EZShopExceptions as Exc
package EZShopGUI as GUI

Data <-- EZS
Exc <-- EZS
GUI <--> EZS
@enduml
```


# Low level design

<for each package, report class diagram>

EZShop:

User
Role
ProductType
Order
Customer
Ticket
BalanceOperation

```
@startuml
interface "EZShopInterface" as API{
 void reset()
 -- User Management --
 Integer createUser(String username, String password, String role) 
 boolean deleteUser(Integer id) 
 List<User> getAllUsers() 
 User getUser(Integer id) 
 boolean updateUserRights(Integer id, String role)
 User login(String username, String password) 
 boolean logout()
 -- Product Management --
 Integer createProductType(String description, String productCode, double pricePerUnit, String note)
 boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
 boolean deleteProductType(Integer id) 
 List<ProductType> getAllProductTypes() 
 ProductType getProductTypeByBarCode(String barCode) 
 List<ProductType> getProductTypesByDescription(String description) 
 boolean updateQuantity(Integer productId, int toBeAdded) 
 boolean updatePosition(Integer productId, String newPos) 
 -- Order Management --
 Integer issueOrder(String productCode, int quantity, double pricePerUnit)
 Integer payOrderFor(String productCode, int quantity, double pricePerUnit)
 boolean payOrder(Integer orderId) 
 boolean recordOrderArrival(Integer orderId) 
 List<Order> getAllOrders() 
 -- Customer Management --
 Integer defineCustomer(String customerName) 
 boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard) 
 boolean deleteCustomer(Integer id) 
 Customer getCustomer(Integer id) 
 List<Customer> getAllCustomers() 
 String createCard() 
 boolean attachCardToCustomer(String customerCard, Integer customerId) 
 boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded) 
 -- Payment Management --
 Integer startSaleTransaction() 
 boolean addProductToSale(Integer transactionId, String productCode, int amount) 
 boolean deleteProductFromSale(Integer transactionId, String productCode, int amount) 
 boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate) 
 boolean applyDiscountRateToSale(Integer transactionId, double discountRate) 
 int computePointsForSale(Integer transactionId) 
 boolean endSaleTransaction(Integer transactionId) 
 boolean deleteSaleTransaction(Integer transactionId) 
 SaleTransaction getSaleTransaction(Integer transactionId) 
 Integer startReturnTransaction(Integer transactionId) 
 boolean returnProduct(Integer returnId, String productCode, int amount) 
 boolean endReturnTransaction(Integer returnId, boolean commit) 
 boolean deleteReturnTransaction(Integer returnId) 
 double receiveCashPayment(Integer transactionId, double cash) 
 boolean receiveCreditCardPayment(Integer transactionId, String creditCard) 
 double returnCashPayment(Integer returnId) 
 double returnCreditCardPayment(Integer returnId, String creditCard) 
 boolean recordBalanceUpdate(double toBeAdded) 
 List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to) 
 double computeBalance()

}
class EZShop {

}

API <|-- EZShop
@enduml
```

### Exception Package
```plantuml
@startuml
class "Exception" as E
InvalidCreditCardException -down-|> E
InvalidCustomerCardException -down--|> E
InvalidCustomerIdException -down-|> E
InvalidCustomerNameException -down--|> E
InvalidDiscountRateException -down-|> E

InvalidLocationException -up-|> E
InvalidOrderIdException -up--|> E
InvalidPasswordException -up-|> E
InvalidPaymentException -up--|> E
InvalidPricePerUnitException -up-|> E

InvalidProductCodeException -up-|> E
InvalidProductDescriptionException -up--|> E
InvalidProductIdException -up-|> E
InvalidQuantityException -up--|> E
InvalidRoleException -up-|> E

InvalidTicketNumberException -down-|> E
InvalidTransactionIdException -down--|> E
InvalidUserIdException -down-|> E
InvalidUsernameException -down--|> E
UnauthorizedException -down-|> E
@enduml
```

# Verification traceability matrix

\<for each functional requirement from the requirement document, list which classes concur to implement it>











# Verification sequence diagrams 
\<select key scenarios from the requirement document. For each of them define a sequence diagram showing that the scenario can be implemented by the classes and methods in the design>

