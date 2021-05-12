# Unit Testing Documentation

Authors: Angela D'Antonio, Gabriele Inzerillo, Ruggero Nocera, Marzio Vallero

Date: 12/05/2021

Version: 1.0

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)




- [White Box Unit Tests](#white-box-unit-tests)


# Black Box Unit Tests

    <Define here criteria, predicates and the combination of predicates for each function of each class.
    Define test cases to cover all equivalence classes and boundary conditions.
    In the table, report the description of the black box test case and (traceability) the correspondence with the JUnit test case writing the 
    class and method name that contains the test case>
    <JUnit test classes must be in src/test/java/it/polito/ezshop   You find here, and you can use,  class TestEzShops.java that is executed  
    to start tests
    >

 ### **Class EzShop - method *barcodeIsValid***

**Criteria for method *barcodeIsValid*:**
 - Validity of input string
 - Check last digit

**Predicates for method *barcodeIsValid*:**
>(1) https://www.gs1.org/services/how-calculate-check-digit-manually

| Criteria | Predicate |
| -------- | --------- |
| Validity of input string | Matches RegEx "[0-9]{12,14}" |
| | Doesn't match RegEx "[0-9]{12,14}" |
| Check digit | Matches algorithm (1) |
|  | Doesn't match algorithm (1) |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| String length | [12, 14] |
|          | [0, 11] or [15, maxint] |

**Combination of predicates**:

| Criteria 1 | Criteria 2 | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
| Matches RegEx "[0-9]{12,14}" | Matches algorithm (1) | Valid | barcodeIsValid("122474487139") -> test passes | void testBarcodeIsValid() |
| Doesn't match RegEx "[0-9]{12,14}" | * | Invalid | barcodeIsValid("123") -> test fails | void testBarcodeIsValid() |
| * | Doesn't match algorithm (1) | Invalid | barcodeIsValid("111111111111") -> test fails | void testBarcodeIsValid() |

### **Class EzShop - method *testCreditCardIsValid***

**Criteria for method *testCreditCardIsValid*:**

 - Validity of input string
 - Check luhn algorithm

**Predicates for method *testCreditCardIsValid*:**

>(2) https://www.geeksforgeeks.org/luhn-algorithm/

| Criteria                 | Predicate                               |
| ------------------------ | --------------------------------------- |
| Validity of input string | creditCard is not null and is not empty |
|                          | creditCard is null or empty             |
| Luhn Algorithm           | Matches algorithm (2)                   |
|                          | Doesn't match algorithm (2)             |

**Boundaries**:

| Criteria      | Boundary values |
| ------------- | --------------- |
| String length | [1, maxint]     |
|               | [0]             |

**Combination of predicates**:

| Criteria 1                              | Criteria 2                  | Valid / Invalid | Description of the test case                         | JUnit test case              |
| --------------------------------------- | --------------------------- | --------------- | ---------------------------------------------------- | ---------------------------- |
| creditCard is not null and is not empty | Matches algorithm (2)       | Valid           | creditCardIsValid("4146262665956358") -> test passes | void testCreditCardIsValid() |
| creditCard is null or empty             | *                           | Invalid         | creditCardIsValid("") -> test fails                  | void testCreditCardIsValid() |
| *                                       | Doesn't match algorithm (2) | Invalid         | creditCardIsValid("11111111") -> test fails          | void testCreditCardIsValid() |

### **Class EzShop - method *testRoundUp***

**Criteria for method *testRoundUp*:**

 - Sign of toRound

**Predicates for method *testRoundUp*:**

| Criteria        | Predicate |
| --------------- | --------- |
| Sign of toRound | >= 0      |
|                 | < 0       |

**Boundaries**:

| Criteria        | Boundary values |
| --------------- | --------------- |
| Sign of toRound | [0, maxint]     |
|                 | [minint, -1]    |

**Combination of predicates**:

| Criteria 1   | Valid / Invalid | Description of the test case | JUnit test case    |
| ------------ | --------------- | ---------------------------- | ------------------ |
| toRound >= 0 | Valid           | RoundUp(0) -> test passes    | void testRoundUp() |
| toRound < 0  | Valid           | RoundUp(-2) -> test passes   | void testRoundUp() |

# White Box Unit Tests

### Test cases definition

    <JUnit test classes must be in src/test/java/it/polito/ezshop>
    <Report here all the created JUnit test cases, and the units/classes under test >
    <For traceability write the class and method name that contains the test case>


| Unit name | JUnit test case |
|--|--|
|||
|||
||||

### Code coverage report

    <Add here the screenshot report of the statement and branch coverage obtained using
    the Eclemma tool. >


### Loop coverage analysis

    <Identify significant loops in the units and reports the test cases
    developed to cover zero, one or multiple iterations >

|Unit name | Loop rows | Number of iterations | JUnit test case |
|---|---|---|---|
|||||
|||||
||||||



