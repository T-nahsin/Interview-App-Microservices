# AI-Powered Interview Preparation App 🎯

This project is a microservices-based application that helps users practice interviews with AI-generated questions and evaluations.  

## 🚀 Features
- Role-based question generation (AI-powered)  
- Voice/text answers with scoring  
- Mock interview sessions  
- Performance tracking & gamification  
- Admin panel for managing content  

## 🛠️ Tech Stack
- Java 21, Spring Boot 3.4.6  
- MongoDB Atlas  
- Microservices + Eureka Server  
- WebClient + Gemini AI API  
- JWT Authentication  

## 📂 Project Structure
- **question-service** → Generates interview questions  
- **answer-service** → Handles user answers & evaluation  
- **mock-interview-service** → Conducts full mock interviews  
- **ai-service** → Communicates with Gemini AI API  

## 📖 API Documentation
Swagger is enabled: `/swagger-ui.html`

## ⚡ How to Run
```
mvn spring-boot:run
(Run each service separately)
```

## 👨‍💻 Author
Nishant Singh
