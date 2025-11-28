# wallet-microservice

A simple **digital wallet microservice** built with **Spring Boot**, supporting:

- Create wallet (one per user)
- Deposit funds
- Withdraw funds
- Transfer funds between wallets
- JWT-based user identification (optional)
- H2 in-memory DB (switchable to PostgreSQL for production)

All endpoints return a **consistent JSON response** with success status, message, data, and error codes.

---

## Project Structure
src/main/java/com/example/wallet
```├── config/ # configuration classes
├── controller/ # REST controllers
├── domain/ # JPA entities (Wallet)
├── dto/ # DTOs (ApiResponse, TransferRequest)
├── exception/ # Custom exceptions
├── repository/ # Spring Data JPA repositories
└── service/ # business logic
```


---

## Setup & Run

### 1. Prerequisites

- Java 17+
- Maven 3+
- (Optional) PostgreSQL for production

---

### 2. Clone the repository

```bash
git clone <repo-url>
cd wallet-service

3. Run with H2 (default)

The project uses H2 in-memory DB for development. No extra setup is needed.



| Method | Endpoint            | Params / Body                                    | Description                |
| ------ | ------------------- | ------------------------------------------------ | -------------------------- |
| POST   | `/wallets`          | `userId`                                         | Create a wallet for a user |
| GET    | `/wallets`          | `userId`                                         | Fetch wallet details       |
| GET    | `/wallets/balance`  | `userId`                                         | Get wallet balance         |
| POST   | `/wallets/deposit`  | `userId`, `amount`                               | Deposit funds              |
| POST   | `/wallets/withdraw` | `userId`, `amount`                               | Withdraw funds             |
| POST   | `/wallets/transfer` | `TransferRequest {fromUserId, toUserId, amount}` | Transfer funds             |
```
## 3 Response Format

All endpoints return:
```
{
"success": true,
"code": 0,
"message": "Success message",
"data": {...} // can be Wallet object, balance, or null
}

{
"success": false,
"code": <error-code>,
"message": "Error message",
"data": null
}
```

## 4 Custom Error Codes
Custom Error Codes
```

| Code | Meaning                        |
| ---- | ------------------------------ |
| 1001 | Wallet already exists for user |
| 1002 | Deposit failed                 |
| 1003 | Withdraw failed                |
| 1004 | Transfer failed                |
| 1005 | Fetch wallet failed            |
| 5000 | Internal server error          |
```

## 5 Example Wallet JSON

```
{
"id": "a08aede6-ac06-49ab-b530-33970518dd80",
"userId": "01111111-1111-1111-1111-111111111111",
"balance": 5000
}
```


