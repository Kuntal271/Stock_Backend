# Portfolio Management API Documentation

---

## 1. Overview
This application is a **Portfolio Management API** designed for managing trades, user portfolios, stock updates, and associated functionalities. Built using Spring Boot, it integrates Swagger for API documentation.

**Key Features**:
- User portfolio management.
- Stock trading operations.
- Update and query stock details.
- RESTful API design.

---

## 2. API Documentation

### Base URL
`/api`

### Endpoints

| HTTP Method | Endpoint                    | Description                     |
|-------------|-----------------------------|---------------------------------|
| GET         | `/api/stocks`              | Fetch all stocks.              |
| POST        | `/api/trades`              | Create a new trade.            |
| GET         | `/api/portfolio/{userName}`| Get user portfolio by username.|
| POST        | `/api/users`               | Create a new user.             |

### Example Requests and Responses

#### Fetch All Stocks
**Request:**
```json
[
    {
        "id": 1,
        "stockSymbol": "AAPL",
        "stockName": "Apple Inc.",
        "prevClose": 145.32,
        "openPrice": 146.50,
        "highPrice": 147.00,
        "lowPrice": 145.00,
        "hi52Week": 180.50,
        "lo52Week": 120.00,
        "settlementPrice": 146.00
    }
]
```
####    Create a Trade
**Request**
```json
{
"userName": "johndoe",
"stockId": 1,
"tradeType": "BUY",
"quantity": 10,
"priceAtTradeTime": 146.50
}
```



#### Fetch All Stocks

- **Method**: GET
- **Endpoint**: `/api/portfolio/{userId}`


```json
[
  {
    "stockId": 1,
    "stockName": "APPLLE  INC",
    "quantity": 10,
    "buyPrice": 150.50,
    "currentPrice": 170.00,
    "gainLoss": 19.50,
    "gainLossPercent": 12.96
  },
  {
    "stockId": 2,
    "stockName": "Google LLC",
    "quantity": 5,
    "buyPrice": 2500.00,
    "currentPrice": 2600.00,
    "gainLoss": 100.00,
    "gainLossPercent": 4.00
  }
]
```
#### Create New User

- **Method**: POST  
- **Endpoint**: `/api/user`

#### Request Body:
```json
{
  "username": "string",
  "email": "string",
  "portfolio": [
    {
      "stockId": 1,
      "stockName": "APPLE INC",
      "quantity": 10,
      "buyPrice": 150.50,
      "currentPrice": 170.00,
      "gainLoss": 19.50,
      "gainLossPercent": 12.96
    }
  ]
}

