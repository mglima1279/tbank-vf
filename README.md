# 📘 API REST - Bank Demo (Endpoints Detalhados)

---

## 🔐 AUTH

---

### 📍 POST `/auth/register`

Cria um novo usuário e gera token JWT.

#### 📥 Request

```json
{
  "username": "joao.silva",
  "password": "123456",
  "cpf": "12345678900",
  "tel": "79999999999"
}
```

#### 📤 Response (200 OK)

```text
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### ❌ Erros

**409 Conflict**
```json
{
  "message": "Username already exists",
  "timestamp": "2026-05-06T12:00:00"
}
```

---

### 📍 POST `/auth/login`

Autentica usuário e retorna token JWT.

#### 📥 Request

```json
{
  "username": "joao.silva",
  "password": "123456"
}
```

#### 📤 Response (200 OK)

```text
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### ❌ Erros

**401 Unauthorized**
```json
{
  "message": "Invalid Credentials",
  "timestamp": "2026-05-06T12:00:00"
}
```

---

## 🧪 TEST

---

### 📍 GET `/test`

#### 📤 Response

```text
Server is running!!
```

---

### 📍 GET `/test/auth`

#### 🔐 Header

```
Authorization: Bearer <token>
```

#### 📤 Response

```text
Authenticated user: joao.silva, Token: <jwt>
```

#### ❌ Erros

**401 Unauthorized**
```json
{
  "message": "Unauthorized",
  "timestamp": "2026-05-06T12:00:00"
}
```

---

## 👤 ACCOUNT

---

### 📍 GET `/me`

#### 🔐 Header

```
Authorization: Bearer <token>
```

#### 📤 Response

```json
{
  "username": "joao.silva",
  "cpf": "12345678900",
  "tel": "79999999999",
  "balance": 1500.75,
  "transactions": []
}
```

#### ❌ Erros

**404 Not Found**
```json
{
  "message": "Account not found",
  "timestamp": "2026-05-06T12:00:00"
}
```

---

### 📍 POST `/me/deposit`

#### 🔐 Header

```
Authorization: Bearer <token>
```

#### 📥 Request

```json
200.00
```

#### 📤 Response

```json
{
  "username": "joao.silva",
  "cpf": "12345678900",
  "tel": "79999999999",
  "balance": 1700.75,
  "transactions": []
}
```

#### ❌ Erros

**400 Bad Request**
```json
{
  "message": "Invalid request body",
  "timestamp": "2026-05-06T12:00:00"
}
```

---

### 📍 POST `/me/withdraw`

#### 🔐 Header

```
Authorization: Bearer <token>
```

#### 📥 Request

```json
200.00
```

#### 📤 Response

```json
{
  "username": "joao.silva",
  "cpf": "12345678900",
  "tel": "79999999999",
  "balance": 1300.75,
  "transactions": []
}
```

#### ❌ Erros

**422 Unprocessable Entity**
```json
{
  "message": "Insufficient balance",
  "timestamp": "2026-05-06T12:00:00"
}
```

---

## 💸 TRANSACTIONS

---

### 📍 POST `/transactions`

#### 🔐 Header

```
Authorization: Bearer <token>
```

#### 📥 Request

```json
{
  "toAccountId": 2,
  "amount": 200.00
}
```

#### 📤 Response

```json
{
  "publicId": "550e8400-e29b-41d4-a716-446655440000",
  "fromUsername": "joao.silva",
  "toUsername": "maria.souza",
  "amount": 200.00,
  "timestamp": "2026-05-06T12:00:00"
}
```

#### ❌ Erros

**422 Unprocessable Entity**
```json
{
  "message": "Insufficient balance",
  "timestamp": "2026-05-06T12:00:00"
}
```

**422 Unprocessable Entity**
```json
{
  "message": "Transaction amount must be non-negative",
  "timestamp": "2026-05-06T12:00:00"
}
```

---

### 📍 GET `/transactions`

```json
[
  {
    "publicId": "550e8400-e29b-41d4-a716-446655440000",
    "fromUsername": "joao.silva",
    "toUsername": "maria.souza",
    "amount": 200.00,
    "timestamp": "2026-05-06T12:00:00"
  }
]
```

---

### 📍 GET `/transactions/{id}`

```json
{
  "publicId": "550e8400-e29b-41d4-a716-446655440000",
  "fromUsername": "joao.silva",
  "toUsername": "maria.souza",
  "amount": 200.00,
  "timestamp": "2026-05-06T12:00:00"
}
```

#### ❌ Erros

**404 Not Found**
```json
{
  "message": "Transaction not found",
  "timestamp": "2026-05-06T12:00:00"
}
```

**401 Unauthorized**
```json
{
  "message": "Unauthorized",
  "timestamp": "2026-05-06T12:00:00"
}
```

---

### 📍 DELETE `/transactions/{id}`

```
204 No Content
```

#### ❌ Erros

**404 Not Found**
```json
{
  "message": "Transaction not found",
  "timestamp": "2026-05-06T12:00:00"
}
```

---

## 🔑 AUTENTICAÇÃO GLOBAL

```
Authorization: Bearer <JWT_TOKEN>
```

---

## 🚨 PADRÃO DE ERROS

```json
{
  "message": "Descrição do erro",
  "timestamp": "2026-05-06T12:00:00"
}
```

---

## 📌 NOTAS

- JWT obrigatório em endpoints protegidos  
- Senhas criptografadas com PasswordEncoder  
- Transações usam UUID (publicId)  
- Exceções tratadas por CustomException  
- Handler global via @ControllerAdvice  

---
