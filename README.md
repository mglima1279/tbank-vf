# 📘 API REST - Bank Demo

Documentação de uso da API REST para gerenciamento de autenticação, conta e transações.

---

## 🔐 Autenticação

### 📍 POST `/auth/register`

Cria um novo usuário e retorna um token JWT.

### 📥 Request

```json
{
  "username": "string",
  "password": "string",
  "cpf": "string",
  "tel": "string"
}
```

### 📤 Response (200 OK)

```text
jwt_token_string
```

### ❌ Erros

* **400 Bad Request**

```text
Error occurred while registering user: <mensagem>
```

---

### 📍 POST `/auth/login`

Autentica um usuário e retorna um token JWT.

### 📥 Request

```json
{
  "username": "string",
  "password": "string"
}
```

### 📤 Response (200 OK)

```text
jwt_token_string
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
Authorization: Bearer <token>
```

### 📤 Response (200 OK)

```text
Authenticated user: username, Token: jwt_token
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
Authorization: Bearer <token>
```

### 📤 Response (200 OK)

```json
{
  "username": "string",
  "cpf": "string",
  "tel": "string",
  "balance": 1000.00,
  "transactions": []
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
Authorization: Bearer <token>
```

### 📥 Request

```json
{
  "toAccountId": 1,
  "amount": 100.50
}
```

### 📤 Response (200 OK)

```json
{
  "publicId": "uuid",
  "fromUsername": "string",
  "toUsername": "string",
  "amount": 100.50,
  "timestamp": "2026-01-01T10:00:00"
}
```

### ❌ Erros

* **400 Bad Request**

---

### 📍 GET `/transactions`

Lista todas as transações do usuário autenticado.

### 🔐 Header obrigatório

```
Authorization: Bearer <token>
```

### 📤 Response (200 OK)

```json
[
  {
    "publicId": "uuid",
    "fromUsername": "string",
    "toUsername": "string",
    "amount": 100.50,
    "timestamp": "2026-01-01T10:00:00"
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
Authorization: Bearer <token>
```

### 📥 Request

```json
"uuid"
```

### 📤 Response (200 OK)

```json
{
  "publicId": "uuid",
  "fromUsername": "string",
  "toUsername": "string",
  "amount": 100.50,
  "timestamp": "2026-01-01T10:00:00"
}
```

### ❌ Erros

* **400 Bad Request**

---

### 📍 DELETE `/transactions`

Remove uma transação.

### 🔐 Header obrigatório

```
Authorization: Bearer <token>
```

### 📥 Request

```json
"uuid"
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

* Todas as datas seguem o padrão ISO 8601.
* Valores monetários utilizam `BigDecimal`.
* O `publicId` das transações é um UUID.
* Tokens JWT são gerados no login e registro.

---

## 🚀 Exemplo de fluxo

1. Registrar usuário → `/auth/register`
2. Fazer login → `/auth/login`
3. Usar token para acessar endpoints protegidos
4. Criar e consultar transações
5. Consultar dados da conta em `/me`
