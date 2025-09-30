# Interview App - Microservices Architecture

```json

This is a microservices-based **Interview Application** built using Java and Spring Boot. It enables functionalities such as user management, question and answer handling, and AI-powered features. The services communicate via REST and are registered/discovered through a Eureka server.

---

## 📦 Microservices Structure

```

📁 interview-app-microservices/
│
├── userService/ → Manages users (candidates/interviewers)
├── questionService/ → Handles interview questions
├── answerService/ → Manages submitted answers
├── aiService/ → AI-based suggestions/evaluations (e.g., GPT-based)
├── eureka/ → Eureka service registry (discovery server)

```json

--

## 🚀 Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Cloud (Eureka)**
- **REST APIs**
- **Maven**
- **Docker (optional for deployment)**

---

## 🔁 Services Overview

| Service         | Port  | Description                             |
|----------------|-------|-----------------------------------------|
| userService     | 8081  | User registration, authentication, roles |
| questionService | 8082  | CRUD operations for interview questions |
| answerService   | 8083  | Submit and evaluate answers             |
| aiService       | 8084  | AI-based answer suggestions/evaluation |
| eureka          | 8761  | Eureka service discovery                |

---

## ⚙️ Getting Started

### ✅ Prerequisites

- Java 17+
- Maven
- Git
- Docker (optional)

---

### 🔧 Clone the Repository


git clone https://github.com/T-nahsin/Interview-App-Microservices.git
cd Interview-App-Microservices

```

▶️ Start Eureka Server

```json
bash
Copy code
cd eureka

mvn spring-boot:run

```

Eureka will be available at: http://localhost:8761

▶️ Run Each Microservice
In separate terminals:

```json
Copy code
cd userService
mvn spring-boot:run

cd questionService
mvn spring-boot:run

cd answerService
mvn spring-boot:run

cd aiService
mvn spring-boot:run

```

Each service will register itself with Eureka.

#  📡 API Endpoints (Sample)
Here are a few sample endpoints per service:

# 🧑‍💼 User Service
Method	Endpoint	Description
POST	/users/register	Register a new user
POST	/users/login	Login with credentials
GET	/users/{id}	Get user details

# ❓ Question Service
Method	Endpoint	Description
POST	/questions	Add a new question
GET	/questions/{id}	Fetch a question
GET	/questions	List all questions

# 📝 Answer Service
Method	Endpoint	Description
POST	/answers	Submit an answer
GET	/answers/{id}	Get an answer

🤖 AI Service (Planned)
Method	Endpoint	Description
POST	/ai/evaluate	Evaluate answer using AI model
POST	/ai/suggest	Generate suggested answers/questions

May integrate OpenAI or custom ML/NLP models

#📌 Future Improvements
✅ API Gateway (Spring Cloud Gateway)

✅ Centralized Config (Spring Cloud Config)

✅ Security (JWT Authentication & Role-based Access)

✅ Docker Compose for local dev

✅ Service-to-service Feign clients

✅ Database integration (MySQL/Postgres for each service)

✅ OpenAPI/Swagger documentation

🐳 Docker (optional)
Coming soon...

```json
Copy code
docker-compose up --build
```
# 🤝 Contributing
Contributions are welcome! Please open an issue or submit a PR.

# 📄 License
This project is open-source and available under the MIT License.

# 👤 Author
T-nahsin
