# Unit Testing Documentation

Authors: Angela D'Antonio, Gabriele Inzerillo, Ruggero Nocera, Marzio Vallero

Date: 12/05/2021

Version: 1.0

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)




- [White Box Unit Tests](#white-box-unit-tests)


# Black Box Unit Tests

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

| Unit name | JUnit test case |
|--|--|
|TestUnit|testBarcodeIsValid()|
|TestUnit|testCreditCardIsValid()|
|TestUnit|testRoundUp()|

### Code coverage report
![image](./TestReport/unitTestCoverage.png)

### Loop coverage analysis

|Unit name | Loop rows | Number of iterations | JUnit test case |
|---|---|---:|---|
|TestUnit|1378-1384|11|testBarcodeIsValid()|
|TestUnit|1393-1399|12|testBarcodeIsValid()|
|TestUnit|1408-1414|13|testBarcodeIsValid()|
|TestUnit|1447-1462|[0, 15]|testCreditCardIsValid()|
|TestUnit|1447-1462|16|testCreditCardIsValid()|
|TestUnit|1447-1462|[17, maxint]|testCreditCardIsValid()|
|TestUnit|None|Not applicable|testRoundUp()|



