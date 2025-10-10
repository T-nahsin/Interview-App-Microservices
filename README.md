<!DOCTYPE html>
<html lang="en">
  <body style="font-family: Arial, Helvetica, sans-serif; background-color: #0b1220; color: #e6eef6; line-height: 1.6; padding: 30px;">
    <header style="text-align: center; margin-bottom: 40px;">
      <h1 style="color: #7dd3fc;">🤖 AI Interview Preparation App — Backend</h1>
      <p style="color: #9aa4b2; font-size: 16px; margin: 0 auto; max-width: 800px;">
        A microservice-based backend that helps users prepare for job interviews by generating AI-powered questions, evaluating answers, and providing smart feedback — powered by Gemini AI.
      </p>
      <p>
        <b>Tech Stack:</b> Java 25, Spring Boot 3.5.6, Spring Cloud Gateway, Spring Cloud Config, MongoDB Atlas, Gemini AI API, Keycloak, Swagger, Maven
      </p>
    </header>

  <section style="margin-bottom: 30px;">
      <h2 style="color: #60a5fa;">🧠 Purpose</h2>
      <p style="color: #d1d5db;">
        This project helps job seekers practice interviews effectively by using AI to generate custom role-based questions, evaluate their answers, and provide improvement suggestions. It serves as a personal AI Interview Coach.
      </p>
    </section>

  <section style="margin-bottom: 30px;">
      <h2 style="color: #60a5fa;">🧩 Microservices Overview</h2>
      <ul>
    <li><b>Config Service:</b> Centralized configuration server (Spring Cloud Config) for managing all microservice configurations.</li>
        <li><b>API Gateway (Port 8080):</b> The single entry point for all requests; routes traffic to respective microservices and handles security/auth forwarding.</li>
        <li><b>User Service:</b> Handles registration, login, fetching, and validation of users (integrated with Keycloak).</li>
        <li><b>Question Service:</b> Generates AI-driven questions based on job roles and difficulty levels.</li>
        <li><b>Answer Service:</b> Accepts and stores user answers.</li>
        <li><b>Evaluation Service:</b> Uses AI to evaluate answers, assign scores, and provide improvement recommendations.</li>
        <li><b>AI Service:</b> Interacts with Gemini AI API to generate questions, evaluate answers, and return recommendations.</li>
        <li><b>Mock Interview Service:</b> Manages mock interview sessions (Easy - 5 questions, Medium - 10, Hard - 15) by coordinating Question, Answer, and Evaluation services.</li>
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
[Mock Interview Service] orchestrates across them
      </pre>
<p style="color: #9aa4b2;">
        • The <b>API Gateway</b> simplifies access and centralizes routing.<br>
        • The <b>Config Service</b> provides dynamic configuration to all microservices.<br>
        • Services communicate via REST, keeping the system modular and scalable.
      </p>
    </section>
    <section style="margin-bottom: 30px;">
      <h2 style="color: #60a5fa;">⚙️ Setup Instructions</h2>
      <h3>Prerequisites</h3>
      <ul>
        <li>Java 25</li>
        <li>Maven 3+</li>
        <li>MongoDB Atlas account</li>
        <li>Keycloak (realm + client configured)</li>
        <li>Gemini AI API key</li>
      </ul>
      <h3>Steps to Run</h3>
      <pre style="background-color: #071025; padding: 15px; border-radius: 8px; color: #d1fae5;">
# 1️⃣ Clone the repository
git clone https://github.com/T-nahsin/ai-interview-app.git
cd ai-interview-app

# 2️⃣ Start the Config Service
mvn -pl config-service spring-boot:run

# 3️⃣ Start the API Gateway (Port 8080)
mvn -pl api-gateway spring-boot:run

# 4️⃣ Start all other microservices
mvn -pl user-service spring-boot:run
mvn -pl question-service spring-boot:run
mvn -pl ai-service spring-boot:run
mvn -pl answer-service spring-boot:run
mvn -pl evaluation-service spring-boot:run
mvn -pl mock-interview-service spring-boot:run

# 5️⃣ Access through API Gateway
http://localhost:8080/

# 6️⃣ Open Swagger UI
http://localhost:8080/swagger-ui.html
      </pre>
      <p style="color: #9aa4b2;">
        Each service automatically fetches its configuration from the Config Service at startup.
      </p>
    </section>
    <section style="margin-bottom: 30px;">
      <h2 style="color: #60a5fa;">🧠 Features</h2>
      <ul>
        <li>🔐 Role-based authentication and authorization via Keycloak</li>
        <li>🤖 AI-generated questions using Gemini AI</li>
        <li>🧾 AI-based evaluation with feedback and score generation</li>
        <li>🎯 Mock interviews with selectable difficulty levels</li>
        <li>📘 Swagger documentation for all APIs</li>
        <li>🚀 Scalable microservices architecture</li>
        <li>🧱 Centralized config management and gateway routing</li>
        <li>🛠 Tested APIs with Postman collections</li>
      </ul>
    </section>
    <section style="margin-bottom: 30px;">
      <h2 style="color: #60a5fa;">🚧 Future Enhancements</h2>
      <ul>
        <li>🎤 Voice-based answer submission and AI speech evaluation</li>
        <li>📄 Resume parsing and evaluation via AI</li>
        <li>📊 User performance analytics dashboard</li>
        <li>☁️ Docker Compose or Kubernetes deployment setup</li>
      </ul>
    </section>
    <section style="margin-bottom: 30px;">
      <h2 style="color: #60a5fa;">👨‍💻 Author</h2>
      <p>
        <b>Nishant Singh</b><br />
        Backend Developer — Java | Spring Boot | MongoDB | AI Integration<br />
        <a href="https://www.linkedin.com/in/nishant-singh-95a15b2a6/" target="_blank" style="color: #7dd3fc; text-decoration: none;">
        LinkedIn:
     </a><br />
        📧 Email: <span style="color: #9aa4b2;">nishant16405@gmail.com</span>
      </p>
    </section>
    <section style="margin-bottom: 30px;">
      <h2 style="color: #60a5fa;">🏷 License</h2>
      <p style="color: #9aa4b2;">This project is open-source and available under the <b>MIT License</b>.</p>
    </section>
    <footer style="text-align: center; margin-top: 40px; color: #9aa4b2; font-size: 14px;">
      <p>Last Updated: October 2025 | AI Interview Preparation App Backend</p>
    </footer>
  </body>
</html>
