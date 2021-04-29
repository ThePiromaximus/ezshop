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

Layered architeture whit MVC pattern.

```plantuml
@startuml
package it.polito.ezshop.model as EZShopModel
package it.polito.ezshop.data as EZShopData
package it.polito.ezshop.exceptions as EZShopExceptions
package it.polito.ezshop.gui as GUI
package it.polito.ezshop.utils as EZShopUtils

EZShopUtils <-- EZShopModel
EZShopData <-- EZShopModel
EZShopExceptions <-- EZShopModel
GUI <--> EZShopModel
@enduml
```


# Low level design

### it.polito.ezshop.model and it.polito.ezshop.data
```plantuml
@startuml
package it.polito.ezshop.data {
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

    class ShopWriter {
       void writeShop(Shop)
    }
    class ShopReader {
        Shop readShop()
    }
}

package it.polito.ezshop.model{
    class Shop {
        +users: HashMap<Integer, User>
        +cards: HashMap<LoyaltyCard, Customer>
        +products: HashMap<Integer, ProductType>
        +saleTransactions: HashMap<Integer, SaleTransaction>
        +orders: HashMap<Integer, Order>
        +returnTransactions: HashMap<Integer, ReturnTransaction>
    }

    note right of Shop: Permanent class

    class User {
        +userId: Integer
        +username: String
        +password: String
        +Role: String
    }


    class ProductType {
        +ID : Integer
        +barCode : String
        +description : String
        +sellPrice : Float
        +quantity : Interge
        +discountRate : Float
        +notes : String
        +position : Position
    }

    class Position {
        +aisleID : String
        +rackID : String
        +levelID : String
    }

    enum OrderState {
        CREATED
        PAYED
        CLOSED
    }

    class Order {
        +supplier : String
        +pricePerUnit : Float
        +quntity : Integer
        +status : OrderState
        +products : List<Product>
    }

    class Customer {
        + ID : Integer
        + name : String
        + surname : String
        + card : LoyaltyCard
    }

    class LoyaltyCard {
        + ID : Integer
        + points : Integer
        
    }

    class BalanceOperation {
        + ID : Integer
        + description : String
        + amount : double
        + date : LocalDate
        + type : BOType
    }

    enum BOType {
    DEBIT
    CREDIT
    }

    class ReturnTransaction {
        + quantity : Integer
        + returnedValue : double
        + product : Product
        + committed : boolean
    }

    class SaleTransaction {
        +date: LocalDate
        +time: LocalTime
        +cost: double
        +paymentType: String
        +discountRate: double
        +state : TransactionState
        +card : LoyaltyCard
        +products : HashMap<Product, Quantity>
    }

    enum TransactionState {
    CLOSED
    PAID
    CANCELED
    }
}
LoyaltyCard "0..1" -- "1" Customer
Shop -- "1..*" User
Shop --  "*" ReturnTransaction
Shop -- "*" Order
Shop -- "*" SaleTransaction
Shop -- "*" LoyaltyCard
Shop -- "*" ProductType

Position "0..1" -- "1" ProductType
Order "1" --  "1" OrderState
BalanceOperation "1" -- "1" BOType
SaleTransaction "1" -- "1" TransactionState
BalanceOperation <|-- ReturnTransaction
BalanceOperation <|-- SaleTransaction
BalanceOperation <|-- Order
API <|-- Shop : <<implements>>

@enduml
```

### it.polito.ezshop.exceptions
```plantuml
@startuml
package it.polito.ezshop.exceptions {
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
}
@enduml
```

# Verification traceability matrix

| - | Shop | User | ProductType | Position | OrderState | Order | Customer | LoyaltyCard | BalanceOperation | BOType | ReturnTransaction | SaleTransaction | TransactionState |
| :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: |
| FR1 | V | V | | | | | | | | | | | |
| FR3 | V | | V | V | | | | | | | | | |
| FR4 | V | | V | V | V | V | | | | | | | |
| FR5 | V | | | | | | V | V | | | | | |
| FR6 | V | | V | | | | | | V | V | V | V | V |
| FR7 | V | | | | | | | | V | | | | |
| FR8 | V | | | | V | V | | | V | V | V | V | V |









# Verification sequence diagrams 

### Scenario 1.1
```plantuml
@startuml
actor User
User -> GUI: Insert description, productCode, pricePerUnit, note
GUI -> Shop: createProductType()
Shop -> ProductType: new ProductType()
Shop <- ProductType: return productType
Shop -> ProductType: productType.getID()
Shop <- ProductType: return productID
GUI <- Shop: return productID
User <- GUI: successful message
User -> GUI: Insert position of ProductType
GUI -> Shop: updatePosition()
Shop -> ProductType: productType.setPosition()
Shop <- ProductType: return true
GUI <- Shop: return true
User <- GUI: successful message
@enduml
```

### Scenario 1.2
```plantuml
@startuml
actor User
User -> GUI: Insert barcode
GUI -> Shop: getProductTypeByBarCode()
GUI <- Shop: return productType
User <- GUI: successful message
User -> GUI: Insert new position of Product
GUI -> Shop: updatePosition()
Shop -> Position: position.setPosition()
Shop <- Position: return true
GUI <- Shop: return true
User <- GUI: successful message
@enduml
```


### Scenario 2.1
```plantuml
@startuml
actor Admin
Admin -> GUI : Define credentials of new account
Admin -> GUI : Select access rights
Admin -> GUI : Confirms inserted data
GUI -> Shop : createUser()
Shop -> User : new User()
User -> Shop : return user
Shop -> GUI : return userID
GUI -> Admin : Successfull message
@enduml
```

### Scenario 3.1
```plantuml
@startuml
actor User
User -> GUI: Insert productCode, quantity, pricePerUnit
GUI -> Shop: issueOrder()
Shop -> Order: new Order()
Shop <- Order: return order
Shop -> Order: order.getID()
Shop <- Order: return orderID
GUI <- Shop: return orderID
User <- GUI: successful message
@enduml
```

### Scenario 3.2
```plantuml
@startuml
actor User
User -> GUI: Insert orderID
GUI -> Shop: payOrder()
Shop -> Order: order.setStatus()
Shop <- Order: return true
GUI <- Shop: return true
User <- GUI: successful message
@enduml
```

### Scenario 4.1
```plantuml
@startuml
Actor User as U
U -> GUI: Define a new customer
GUI -> Shop: defineCustomer()
Shop -> Customer: new Customer()
Shop <- Customer: return customer
Shop -> Shop : customer.getID()
GUI <- Shop: return customerID
U <- GUI: successful message
@enduml
```

### Scenario 4.2
```plantuml
@startuml
Actor User as U

U -> GUI: Define a new card
GUI -> Shop: createCard()
Shop -> LoyaltyCard: new LoyaltyCard()
Shop <- LoyaltyCard: return loyaltyCard
Shop -> LoyaltyCard: loyaltyCard.getID()
GUI <- Shop: return loyaltyCard
U <- GUI: successful message
U -> GUI: Attach card to customer
GUI -> Shop: attachCardToCustomer()
Shop -> Customer: customer.setLoyaltyCard()
Shop <- Customer: return true
GUI <- Shop: return true
U <- GUI: successful message
@enduml
```

### Scenario 5.1
```plantuml
@startuml
actor User
User -> GUI : Insert username
User -> GUI : Insert password
GUI -> Shop : login()
Shop -> GUI : return user
GUI -> User : Successful message
@enduml
```

### Scenario 6.2
```plantuml
@startuml
actor Cashier
Cashier -> GUI: Start a new sale transaction
GUI  -> Shop: startSaleTransaction()
Shop -> SaleTransaction: new SaleTransaction()
Shop <- SaleTransaction: return saleTransaction
GUI <- Shop: return ID
GUI -> Cashier: Ask for product bar code
GUI -> Cashier: Ask for product units
Cashier -> GUI: Insert product bar code
Cashier -> GUI: Set N units of product P
GUI  -> Shop: addProductToSale()
Shop -> Shop: products.get(productCode)
Shop -> SaleTransaction: saleTransaction.setProductType()
Shop -> ProductType : productType.setQuantity()
Shop -> GUI: return true
GUI -> Cashier : Ask for product discount rate 
Cashier -> GUI: Apply product discount rate  
GUI -> Shop: applyDiscountRateToProduct()
Shop -> Shop : productType.getID()
Shop -> SaleTransaction: setDiscountRate()
Shop -> GUI: return true
Cashier -> GUI: End Sale transaction
GUI  -> Shop: endSaleTransaction()
Shop -> SaleTransaction: saleTransaction.setState()
GUI <- Shop: return true
Cashier -> GUI: Manage payment (UC7)
Cashier <- GUI: Print sale receipt
@enduml
```

### Scenario 6.4
```plantuml
@startuml
actor Cashier
Cashier -> GUI: Start a new sale transaction
GUI  -> Shop: startSaleTransaction()
Shop -> SaleTransaction: new SaleTransaction()
Shop <- SaleTransaction: return saleTransaction
GUI <- Shop: return ID
GUI -> Cashier: Ask for product bar code
GUI -> Cashier: Ask for product units
Cashier -> GUI: Insert product bar code
Cashier -> GUI: Set N units of product P
GUI  -> Shop: addProductToSale()
Shop -> Shop: productType.getID()
Shop -> SaleTransaction: saleTransaction.setProductType()
Shop -> ProductType: productType.setQuantity()
Shop -> GUI: return true
Cashier -> GUI: End Sale transaction
GUI  -> Shop: endSaleTransaction()
Shop -> SaleTransaction: saleTransaction.setState()
GUI <- Shop: return true
Cashier <- GUI: Ask for payment type
Cashier -> GUI: Insert LoyaltyCard number
GUI -> Shop: computePointsForSale() 
Shop -> GUI: return points 
Cashier -> GUI: Manage payment(UC7)
GUI -> Shop: modifyPointsOnCard()
Shop -> LoyaltyCard: loyaltyCard.setPoints()
Shop <- LoyaltyCard: return true
Shop -> GUI: return true
Cashier <- GUI: Print ticket
@enduml
```

### Scenario 7.1
```plantuml
@startuml
Actor User as U
U -> GUI: Manage payment by credit card
GUI -> Shop: receiveCreditCardPayment()
GUI <- Shop: return true
U <- GUI: successful message
@enduml
```

### Scenario 7.4
```plantuml
@startuml
Actor User as U
U -> GUI: Manage payment by cash
GUI -> Shop: receiveCashPayment()
Shop -> SaleTransaction: saleTransaction.getAmount()
Shop <- SaleTransaction: return amount
GUI <- Shop: return change
U <- GUI: successful message (includes change)
@enduml
```
### Scenario 8.1

```plantuml
@startuml
actor Cashier
Cashier -> GUI : Insert transactionID
GUI -> Shop : startReturnTransaction()
Shop -> ReturnTransaction : new ReturnTransaction()
ReturnTransaction -> Shop : return returnTransaction
Shop -> GUI : return ID
GUI -> Cashier : Ask for product bar code
GUI -> Cashier : Ask for product units
Cashier -> GUI : Insert product bar code
Cashier -> GUI : Set N units of product P
GUI -> Shop : returnProduct()
Shop -> Shop : products.get(productID)
Shop -> ReturnTransaction : returnTransaction.setProduct()
Shop -> GUI : Manage  credit card return
GUI -> Cashier : Manage credit card return
Cashier -> GUI : Close return transaction
GUI -> Shop : endReturnTransaction()
Shop -> ProductType : productType.setQuantity()
Shop -> GUI : return true
GUI -> Cashier : Successful message
@enduml
```
### Scenario 9.1

```plantuml
@startuml
actor Manager
Manager -> GUI : Select start date
Manager -> GUI : Select end date
GUI -> Shop : getCreditsAndDebits()
Shop -> GUI : return balanceOperationList
GUI -> Manager : List displayed
@enduml
```

### Scenario 10.1
```plantuml
@startuml
actor Cashier
GUI -> Cashier: ask for credit card number
Cashier -> GUI: Insert credit card number
GUI -> Shop: returnCreditCardPayment()
Shop -> ReturnTransaction:  returnTransaction.getAmount()
GUI <- Shop: return money
Cashier <- GUI: Successful message
@enduml
```