Transfer Service
---

This is an example REST API for transferring money between various accounts. It is built using Spring Boot and the following dependencies:
 * spring-web-starter (for the web service)
 * H2 (for an in-memory database)
 * JPA (for the RESTFful service)

---
## Usage

//TODO: Add postman requests

### Accounts

Accounts are simple objects with two properties:
 * *accountNumber* - (Long) a unique ID for each account
 * *balance* - (BigDecimal) the current balance of the account

Upon startup, the application is automatically populated with several example accounts

###Accounts
#### List all accounts
Endpoint: ```/accounts```
Method: ```GET```

Example request:
``` curl --request GET 'localhost:8080/accounts' ```

Example response (JSON):
```json
[
    {
        "accountNumber": 11111111,
        "balance": 1000.00
    },
    {
        "accountNumber": 22222222,
        "balance": 123.45
    },
    {
        "accountNumber": 33333333,
        "balance": -50.00
    },
    {
        "accountNumber": 44444444,
        "balance": 10000.00
    },
    {
        "accountNumber": 55555555,
        "balance": -85.65
    }
]
```

#### Find account by accountNumber
Endpoint: ```/accounts/{accountNumber}```
Method: ```GET```

Example request:
``` curl --request GET 'localhost:8080/accounts/22222222' ```

Example response (JSON):
```json
{
  "accountNumber": 22222222,
  "balance": 123.45
}
```

#### Create new account
Endpoint: ```/accounts```
Method: ```POST```

Example request:
``` curl --location -X POST 'localhost:8080/accounts' --header 'Content-Type: application/json' -d '{"accountNumber": 123456, "balance": 500}'```

Example response (JSON):
```json
{
  "accountNumber": 123456,
  "balance": 500
}
```

#### Update account (balance only)
Endpoint: ```/accounts/{accountNumber}```
Method: ```PUT```

Example request:
``` curl --location -X PUT 'localhost:8080/accounts' --header 'Content-Type: application/json' -d '{"accountNumber": 11111111, "balance": 500}'```

Example response (JSON):
```json
{
  "accountNumber": 11111111,
  "balance": 500
}
```

#### Delete account
Endpoint: ```/accounts/{accountNumber}```
Method: ```DELETE```

Example request:
``` curl --location -X DELETE 'localhost:8080/accounts/11111111'```

Example response: ```111111```

### Transactions
#### List all transactions
Endpoint: ```/transactions```
Method: ```GET```

Example request:
``` curl --request GET 'localhost:8080/transactions' ```

Example response (JSON):
```json
[
  {
    "id": 1,
    "sourceAccountNumber": 22222222,
    "destinationAccountNumber": 33333333,
    "amount": 1.00
  }
]
```

### List all transactions from account number
Endpoint: ```/transactions/sourceAccountNumber/{sourceAccountNumber}```
Method: ```GET```

Example request:
``` curl --request GET 'localhost:8080/transactions/sourceAccountNumber/22222222' ```

Example response (JSON):
```json
[
  {
    "id": 1,
    "sourceAccountNumber": 22222222,
    "destinationAccountNumber": 33333333,
    "amount": 1.00
  }
]
```
### List all transactions to account number
Endpoint: ```/transactions/destinationAccountNumber/{destinationAccountNumber}```
Method: ```GET```

Example request:
``` curl --request GET 'localhost:8080/transactions/destinationAccountNumber/33333333' ```

Example response (JSON):
```json
[
  {
    "id": 1,
    "sourceAccountNumber": 22222222,
    "destinationAccountNumber": 33333333,
    "amount": 1.00
  }
]
```
### Transfer between accounts
Endpoint: ```/transactions```
Method: ```POST```

Example request:
``` curl --location -X POST 'localhost:8080/transactions' --header 'Content-Type: application/json' -d '{"sourceAccountNumber": 22222222, "destinationAccountNumber": 33333333, "amount": 100}'```

Example response (JSON):
```json
{
  "id": 3,
  "sourceAccountNumber": 22222222,
  "destinationAccountNumber": 33333333,
  "amount": 100
}
```
Note: Update/Delete methods not allowed on transactions