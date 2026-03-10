<!DOCTYPE html>
<html lang="en">
  <body style="font-family: Arial, Helvetica, sans-serif; background-color: #0b1220; color: #e6eef6; line-height: 1.6; padding: 30px;">

  <header style="text-align: center; margin-bottom: 40px;">
      <h1 style="color: #7dd3fc;">🤖 AI Interview Preparation App — Backend</h1>
      <p style="color: #9aa4b2; font-size: 16px; margin: 0 auto; max-width: 800px;">
        A scalable microservice-based backend designed to help users prepare for interviews with <b>AI-generated questions</b>, <b>intelligent answer evaluation</b>, and now — a new <b>AI-powered Resume Evaluator</b> built using Gemini AI.
      </p>
      <p>
        <b>Tech Stack:</b> Java 25 | Spring Boot 3.5.6 | Spring Cloud Gateway | Spring Cloud Config | MongoDB Atlas | Redis | Gemini AI API | Keycloak | Swagger | Maven
      </p>
    </header>

  <section style="margin-bottom: 30px;">
      <h2 style="color: #60a5fa;">🧠 Purpose</h2>
      <p style="color: #d1d5db;">
        The goal of this project is to create an intelligent interview preparation backend that can simulate real-world interviews, evaluate performance, and now — assess resumes to help candidates improve their profiles before the actual interview.
      </p>
    </section>

  <section style="margin-bottom: 30px;">
      <h2 style="color: #60a5fa;">🧩 Microservices Overview</h2>
      <ul>
        <li><b>Config Service:</b> Centralized configuration management using Spring Cloud Config.</li>
        <li><b>API Gateway (Port 8080):</b> Routes requests and handles authentication and forwarding.</li>
        <li><b>User Service:</b> Manages registration, authentication (Keycloak), and user roles.</li>
        <li><b>Question Service:</b> Generates AI-driven, role-based questions using Gemini API.</li>
        <li><b>Answer Service:</b> Stores and manages user-submitted answers.</li>
        <li><b>Evaluation Service:</b> Evaluates answers, assigns scores, and provides AI feedback.</li>
        <li><b>AI Service:</b> Core AI communication layer with Gemini API for question and evaluation generation.</li>
        <li><b>Mock Interview Service:</b> Orchestrates question-answer-evaluation flow for practice sessions.</li>
        <li><b>Resume Service (New):</b> Accepts resume PDFs, extracts text, fetches user’s role, and uses Gemini AI to evaluate resumes with role-specific feedback.</li>
      </ul>
    </section>

  <section style="margin-bottom: 30px;">
      <h2 style="color: #60a5fa;">🏗 Architecture & Flow</h2>
      <pre style="background-color: #071025; padding: 15px; border-radius: 8px; color: #d1fae5;">
[Client]
   ↓
[API Gateway :8080]
   ↓ routes
[User Service] ←→ [Config Service]
[Question Service] ←→ [AI Service]
[Answer Service] ←→ [Evaluation Service] ←→ [AI Service]
[Mock Interview Service] orchestrates the flow
[Resume Service] ←→ [AI Service] ←→ [User Service]
      </pre>
      <p style="color: #9aa4b2;">
        • The <b>API Gateway</b> provides unified routing and authentication. <br>
        • <b>Redis caching</b> is used to store user roles and AI responses for better performance. <br>
        • <b>Services</b> communicate asynchronously via REST using WebClient.
      </p>
    </section>

  <section style="margin-bottom: 30px;">
      <h2 style="color: #60a5fa;">⚙️ Setup Instructions</h2>
      <h3>🧾 Prerequisites</h3>
      <ul>
        <li>Java 25+</li>
        <li>Maven 3+</li>
        <li>MongoDB Atlas</li>
        <li>Keycloak (realm & client configured)</li>
        <li>Gemini API Key</li>
        <li>Redis Server (local or remote)</li>
      </ul>

  <h3>🚀 Run Locally</h3>
      <pre style="background-color: #071025; padding: 15px; border-radius: 8px; color: #d1fae5;">
        
 # 1️⃣ Clone the repository 
git clone https://github.com/T-nahsin/ai-interview-app.git
cd ai-interview-app

# 2️⃣ Start Config Service
mvn -pl config-service spring-boot:run

# 3️⃣ Start API Gateway
mvn -pl api-gateway spring-boot:run

# 4️⃣ Start all other services
mvn -pl user-service spring-boot:run
mvn -pl question-service spring-boot:run
mvn -pl ai-service spring-boot:run
mvn -pl answer-service spring-boot:run
mvn -pl evaluation-service spring-boot:run
mvn -pl mock-interview-service spring-boot:run
mvn -pl resume-service spring-boot:run

# 5️⃣ Access the system
http://localhost:8081/

# 6️⃣ Open Swagger Docs
http://localhost:8080/swagger-ui.html
      </pre>
    </section>

  <section style="margin-bottom: 30px;">
      <h2 style="color: #60a5fa;">🧠 Key Features</h2>
      <ul>
        <li>🔐 Role-based authentication with Keycloak</li>
        <li>🤖 AI-powered question generation (Gemini API)</li>
        <li>🧩 Resume analysis and evaluation (New!)</li>
        <li>🎯 Mock interviews with multiple difficulty levels</li>
        <li>📘 API documentation via Swagger</li>
        <li>⚡ Caching and optimization using Redis</li>
        <li>🏗 Scalable, modular microservices design</li>
        <li>🧱 Centralized configuration & API Gateway routing</li>
        <li>🧪 Tested APIs via Postman collections</li>
      </ul>
    </section>

  <section style="margin-bottom: 30px;">
      <h2 style="color: #60a5fa;">🚧 Future Enhancements</h2>
      <ul>
        <li>🎤 Voice-based answer submission with AI speech evaluation</li>
        <li>📊 Performance analytics & leaderboard</li>
        <li>☁️ Docker Compose / Kubernetes deployment setup</li>
        <li>🧩 Resilience4j for fault tolerance and fallback handling</li>
      </ul>
    </section>

  <section style="margin-bottom: 30px;">
      <h2 style="color: #60a5fa;">👨‍💻 Author</h2>
      <p>
        <b>Nishant Singh</b><br>
        Backend Developer — Java | Spring Boot | MongoDB | AI Integration<br>
        <a href="https://www.linkedin.com/in/nishant-singh-95a15b2a6/" target="_blank" style="color: #7dd3fc; text-decoration: none;">LinkedIn</a><br>
        📧 <span style="color: #9aa4b2;">nishant16405@gmail.com</span>
      </p>
    </section>

  <footer style="text-align: center; margin-top: 40px; color: #9aa4b2; font-size: 14px;">
      <p>Last Updated: November 2025 | AI Interview Preparation App Backend</p>
    </footer>

  </body>
</html>