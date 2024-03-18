# Currency exchange project

The project was implemented according to the requirements presented in [this course](https://zhukovsd.github.io/java-backend-learning-course/Projects/CurrencyExchange/).

This project is a REST API for describing currencies and exchange rates. Allows you to view and edit lists of currencies and exchange rates, and calculate the conversion of arbitrary amounts from one currency to another.
___
### Technology stack:
+ Java 17
+ + Collections, OOP
+ Java Servlets
+ Maven
+ SQLte
+ Apache Tomcat 10
___
### Getting started

+ Clone repo
```
https://github.com/BondarevM/CurrencyExchange.git
```
+ Use Apache Tomcat 10 with this configurations
![img.png](img.png)

+ Build and run
___
## REST API
 The REST API to the example app is described below.

**Get all currencies**

Request
 ```
GET /currencies
```
Response
 ```
[
    {
        "id": 1,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    {
        "id": 2,
        "name": "Euro",
        "code": "EUR",
        "sign": "€"
    },
    {
        "id": 3,
        "name": "Russian Ruble",
        "code": "RUB",
        "sign": "₽"
    },
    ...
]
```

**Get currency**

Request

```
GET /currency/EUR
```

Response
```
{
    "id": 2,
    "name": "Euro",
    "code": "EUR",
    "sign": "€"
}
```

**Post currency**

Request
```
POST /currencies
```

Response
```
{
    "id": 2,
    "name": "Euro",
    "code": "EUR",
    "sign": "€"
}
```

### Exchange rates

**Get exchange rate**

Request
```
GET /exchangeRates
```

Response

```
[
    {
        "id": 1,
        "baseCurrency": {
            "id": 1,
            "name": "United States dollar",
            "code": "USD",
            "sign": "$"
        },
        "tagretCurrency": {
            "id": 2,
            "name": "Euro",
            "code": "EUR",
            "sign": "€"
        },
        "rate": 0.9
    },
    {
        "id": 2,
        "baseCurrency": {
            "id": 2,
            "name": "Euro",
            "code": "EUR",
            "sign": "€"
        },
        "tagretCurrency": {
            "id": 1,
            "name": "United States dollar",
            "code": "USD",
            "sign": "$"
        },
        "rate": 1.11
    },
    ...
```

**Get exchange rate**

Request
```
GET /exchangeRate/USDRUB
```

Response
```
{
    "id": 3,
    "baseCurrency": {
        "id": 1,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "tagretCurrency": {
        "id": 3,
        "name": "Russian Ruble",
        "code": "RUB",
        "sign": "₽"
    },
    "rate": 100.0
}
```

**Post exchange rate**

Request
```
POST /exchangeRates
```

Response
```
{
    "id": 3,
    "baseCurrency": {
        "id": 1,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "tagretCurrency": {
        "id": 3,
        "name": "Russian Ruble",
        "code": "RUB",
        "sign": "₽"
    },
    "rate": 100.0
}
```

**Update exchange rate**

Request
```
PATCH /exchangeRate/USDRUB
```

Response

```
{
    "id": 3,
    "baseCurrency": {
        "id": 1,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "tagretCurrency": {
        "id": 3,
        "name": "Russian Ruble",
        "code": "RUB",
        "sign": "₽"
    },
    "rate": 150.0
}
```

**Currency exchange**

Request

```
GET /exchange?from=USD&to=EUR&amount=10
```

Response


```
{
    "baseCurrency": {
        "id": 1,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 2,
        "name": "Euro",
        "code": "EUR",
        "sign": "€"
    },
    "rate": 0.9,
    "amount": 10,
    "convertedAmount": 9.0
}
```