# 📘 API REST - Bank Demo

Documentação de uso da API REST para gerenciamento de autenticação, conta e transações.

---

## 🔐 Autenticação

### 📍 POST `/auth/register`

Cria um novo usuário e retorna um token JWT.

### 📥 Request

```json
{
  "username": "joao.silva",
  "password": "123456",
  "cpf": "12345678900",
  "tel": "79999999999"
}
```

### 📤 Response (200 OK)

```text
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### ❌ Erros

* **400 Bad Request**

```text
Error occurred while registering user: Username already exists
```

---

### 📍 POST `/auth/login`

Autentica um usuário e retorna um token JWT.

### 📥 Request

```json
{
  "username": "joao.silva",
  "password": "123456"
}
```

### 📤 Response (200 OK)

```text
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### ❌ Erros

* **400 Bad Request**

```text
Invalid username or password
```

---

## 🧪 Testes

### 📍 GET `/test`

Verifica se o servidor está online.

### 📤 Response (200 OK)

```text
Server is running!!
```

---

### 📍 GET `/test/auth`

Testa autenticação via token JWT.

### 🔐 Header obrigatório

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 📤 Response (200 OK)

```text
Authenticated user: joao.silva, Token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### ❌ Erros

* **401 Unauthorized**

```text
Unauthorized
```

---

## 👤 Conta do Usuário

### 📍 GET `/me`

Retorna dados da conta do usuário autenticado.

### 🔐 Header obrigatório

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 📤 Response (200 OK)

```json
{
  "username": "joao.silva",
  "cpf": "12345678900",
  "tel": "79999999999",
  "balance": 1500.75,
  "transactions": [
    {
      "publicId": "550e8400-e29b-41d4-a716-446655440000",
      "fromUsername": "joao.silva",
      "toUsername": "maria.souza",
      "amount": 200.00,
      "timestamp": "2026-05-01T14:30:00"
    }
  ]
}
```

### ❌ Erros

* **400 Bad Request**

---

### 📍 GET `/me/deposit`

Retorna dados da conta do usuário após se fazer um depósito.

### 🔐 Header obrigatório

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 📥 Request

```json
  200
```

### 📤 Response (200 OK)

```json
{
  "username": "joao.silva",
  "cpf": "12345678900",
  "tel": "79999999999",
  "balance": 1500.75,
  "transactions": [
    {
      "publicId": "550e8400-e29b-41d4-a716-446655440000",
      "fromUsername": "joao.silva",
      "toUsername": "maria.souza",
      "amount": 200.00,
      "timestamp": "2026-05-01T14:30:00"
    }
  ]
}
```

### ❌ Erros

* **400 Bad Request**

---

### 📍 GET `/me/withdraw`

Retorna dados da conta do usuário após se fazer um saque se houver saldo.

### 🔐 Header obrigatório

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 📥 Request

```json
  200
```

### 📤 Response (200 OK)

```json
{
  "username": "joao.silva",
  "cpf": "12345678900",
  "tel": "79999999999",
  "balance": 1500.75,
  "transactions": [
    {
      "publicId": "550e8400-e29b-41d4-a716-446655440000",
      "fromUsername": "joao.silva",
      "toUsername": "maria.souza",
      "amount": 200.00,
      "timestamp": "2026-05-01T14:30:00"
    }
  ]
}
```

### ❌ Erros

* **400 Bad Request**

---

## 💸 Transações

### 📍 POST `/transactions`

Cria uma nova transação.

### 🔐 Header obrigatório

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 📥 Request

```json
{
  "toAccountId": 2,
  "amount": 200.00
}
```

### 📤 Response (200 OK)

```json
{
  "publicId": "550e8400-e29b-41d4-a716-446655440000",
  "fromUsername": "joao.silva",
  "toUsername": "maria.souza",
  "amount": 200.00,
  "timestamp": "2026-05-01T14:30:00"
}
```

### ❌ Erros

* **400 Bad Request**

---

### 📍 GET `/transactions`

Lista todas as transações do usuário autenticado.

### 🔐 Header obrigatório

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 📤 Response (200 OK)

```json
[
  {
    "publicId": "550e8400-e29b-41d4-a716-446655440000",
    "fromUsername": "joao.silva",
    "toUsername": "maria.souza",
    "amount": 200.00,
    "timestamp": "2026-05-01T14:30:00"
  },
  {
    "publicId": "660e8400-e29b-41d4-a716-446655440111",
    "fromUsername": "joao.silva",
    "toUsername": "carlos.lima",
    "amount": 50.00,
    "timestamp": "2026-05-02T09:15:00"
  }
]
```

### ❌ Erros

* **400 Bad Request**

---

### 📍 POST `/transactions/by-id`

Busca uma transação específica pelo ID público.

### 🔐 Header obrigatório

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 📥 Request

```json
"550e8400-e29b-41d4-a716-446655440000"
```

### 📤 Response (200 OK)

```json
{
  "publicId": "550e8400-e29b-41d4-a716-446655440000",
  "fromUsername": "joao.silva",
  "toUsername": "maria.souza",
  "amount": 200.00,
  "timestamp": "2026-05-01T14:30:00"
}
```

### ❌ Erros

* **400 Bad Request**

---

### 📍 DELETE `/transactions`

Remove uma transação.

### 🔐 Header obrigatório

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 📥 Request

```json
"550e8400-e29b-41d4-a716-446655440000"
```

### 📤 Response

* **204 No Content** (sucesso)

### ❌ Erros

* **400 Bad Request**

---

## 🔑 Autenticação

Todos os endpoints protegidos exigem o header:

```
Authorization: Bearer <seu_token_jwt>
```

---

## 📌 Observações

* Datas no padrão ISO 8601 (`yyyy-MM-ddTHH:mm:ss`)
* Valores monetários utilizam `BigDecimal`
* IDs de transação são UUID
* Token JWT é retornado no login e registro
* O campo `transactions` pode vir vazio caso não existam movimentações

---

## 🚀 Exemplo de fluxo completo

1. Registrar usuário (`joao.silva`)
2. Fazer login e obter token
3. Criar transação para `maria.souza`
4. Listar transações
5. Consultar conta em `/me`
6. Buscar transação por ID
7. Deletar transação
