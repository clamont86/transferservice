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

### Find all accounts
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

### Find account by accountNumber
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

### Create new account
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

### Update account (balance only)
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


### Delete account
Endpoint: ```/accounts/{accountNumber}```
Method: ```DELETE```

Example request:
``` curl --location -X DELETE 'localhost:8080/accounts/11111111'```

Example response: ```111111```

---