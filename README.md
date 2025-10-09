
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
</head>
<body>
  <div class="container" role="main">
    <header>
      <div class="logo">AI</div>
      <div>
        <h1>🤖 AI Interview Preparation App — Backend</h1>
        <p class="lead">AI-driven backend that generates interview questions, accepts answers, evaluates them via Gemini AI, and orchestrates mock interviews.</p>
        <div class="badges">
          <span class="badge">Java 25</span>
          <span class="badge">Spring Boot 3.5.6</span>
          <span class="badge">MongoDB Atlas</span>
          <span class="badge">Gemini AI API</span>
          <span class="badge">Keycloak</span>
          <span class="badge">Swagger</span>
          <span class="badge">Maven</span>
        </div>
      </div>
    </header>

<section aria-labelledby="purpose">
      <h2 id="purpose">🧠 Purpose</h2>
      <p style="color:var(--muted); margin:8px 0 0;">
        This project helps job-seekers practice for interviews by providing AI-generated questions and AI-driven evaluations with actionable recommendations. It targets users who lack high-quality, role-based practice resources.
      </p>
    </section>

<section aria-labelledby="services">
      <h2 id="services">🧩 Microservices Overview</h2>
      <div class="grid" style="margin-top:6px;">
        <div>
          <strong>User Service</strong>
          <p class="muted" style="color:var(--muted); margin:6px 0 0;">Registers, logs in, fetches and validates users. Integrates with Keycloak for RBAC.</p>
        </div>
        <div>
          <strong>Question Service</strong>
          <p class="muted" style="color:var(--muted); margin:6px 0 0;">Generates AI-backed questions, returns random questions, and fetches question sets for users.</p>
        </div>

  <div>
          <strong>Answer Service</strong>
          <p class="muted" style="color:var(--muted); margin:6px 0 0;">Accepts and stores user answers; provides endpoints to submit and retrieve answers.</p>
        </div>
        <div>
          <strong>Evaluation Service</strong>
          <p class="muted" style="color:var(--muted); margin:6px 0 0;">Triggers AI analysis to score answers and produce improvement suggestions.</p>
        </div>

  <div>
          <strong>AI Service</strong>
          <p class="muted" style="color:var(--muted); margin:6px 0 0;">Responsible for direct calls to Gemini AI for question generation, scoring, and recommendations.</p>
        </div>
        <div>
          <strong>Mock Interview Service</strong>
          <p class="muted" style="color:var(--muted); margin:6px 0 0;">Orchestrates interview flows — Easy (5 Qs), Medium (10 Qs), Hard (15 Qs) — coordinates Question, Answer, and Evaluation services.</p>
        </div>
      </div>
    </section>

<section aria-labelledby="features">
      <h2 id="features">🧠 Key Features</h2>
      <ul>
        <li>🔐 Role-based authentication & authorization via <strong>Keycloak</strong></li>
        <li>🤖 AI-generated questions (role-based & difficulty-based) using <strong>Gemini AI</strong></li>
        <li>📝 Answer submission & persistent storage (Answer Service)</li>
        <li>🧾 AI-powered evaluation with scores and improvement recommendations</li>
        <li>🎯 Mock interview orchestration with difficulty modes (Easy / Medium / Hard)</li>
        <li>🧱 Modular microservices architecture for independent scaling and deployment</li>
        <li>📘 Swagger docs for every service; tested APIs via Postman</li>
        <li>🚀 Planned: resume evaluation, voice answers, and analytics dashboard</li>
      </ul>
    </section>

    <section aria-labelledby="architecture">
      <h2 id="architecture">🏗 Architecture & Flow</h2>
      <pre><code>// Simplified call flow (user starts mock interview)
User --> MockInterviewService
  MockInterviewService -> QuestionService (generate questions via AIService)
  User submits answers -> AnswerService (persist)
  AnswerService -> EvaluationService -> AIService (score & recommendations)
  MockInterviewService aggregates results and returns summary
</code></pre>
      <p style="color:var(--muted); margin-top:8px;">
        Services communicate over REST. The AI Service is stateless and designed to be scaled horizontally if AI request volume increases.
      </p>
    </section>

  <section aria-labelledby="api">
      <h2 id="api">🔌 Example API Endpoints</h2>
      <div class="endpoint"><span class="chip">POST</span><code>/api/auth/register</code></div>
      <div class="endpoint"><span class="chip">POST</span><code>/api/auth/login</code></div>
      <div class="endpoint"><span class="chip">GET</span><code>/api/questions/generate/{role}</code></div>
      <div class="endpoint"><span class="chip">POST</span><code>/api/answers/submit</code></div>
      <div class="endpoint"><span class="chip">POST</span><code>/api/evaluate/answer</code></div>
      <div class="endpoint"><span class="chip">POST</span><code>/api/mock/start/{mode}</code></div>
    </section>

  <section aria-labelledby="setup">
      <h2 id="setup">⚙️ Setup Instructions</h2>

  <h3 style="margin:8px 0 6px;">Prerequisites</h3>
    <ul>
        <li>Java 25</li>
        <li>Maven 3+</li>
        <li>MongoDB Atlas account & connection string</li>
        <li>Keycloak instance (local or hosted) with realm & client configured</li>
        <li>Gemini AI API key (configured securely — do NOT commit to git)</li>
      </ul>

  <h3 style="margin:8px 0 6px;">Run Locally</h3>
  <pre><code>git clone https://github.com/T-nahsin/ai-interview-app.git
cd ai-interview-app
# configure application.properties / secret store for each service:
# - spring.data.mongodb.uri
# - keycloak.* (realm, client-id, client-secret, auth-server-url)
# - gemini.api.key
mvn -T 1C clean install
# run service (example)
mvn -pl mock-interview-service spring-boot:run
# visit Swagger (per service)
http://localhost:8080/swagger-ui/index.html
</code></pre>

<p style="color:var(--muted); margin-top:8px;">Tip: use Docker + docker-compose for local multi-service orchestration (recommended for testing inter-service calls).</p>
    </section>

  <section aria-labelledby="testing">
      <h2 id="testing">🧪 Testing & Validation</h2>
      <ul>
        <li>APIs tested via Postman collections (include collection in repo `/postman/`)</li>
        <li>Unit tests: JUnit + Mockito where applicable</li>
        <li>Integration tests: test inter-service flows locally or in CI</li>
      </ul>
    </section>

  <section aria-labelledby="security">
      <h2 id="security">🔐 Security</h2>
      <p style="color:var(--muted); margin:6px 0 0;">
        Authentication and authorization are delegated to Keycloak. Services validate incoming tokens and enforce RBAC for protected endpoints. API keys (Gemini) should be stored in environment variables or a secret manager — never in source control.
      </p>
    </section>

  <section aria-labelledby="future">
      <h2 id="future">🚧 Future Enhancements</h2>
      <ul>
        <li>🎤 Voice-based answers (speech-to-text) and audio evaluation</li>
        <li>📄 Resume parsing & automated resume evaluation</li>
        <li>📊 Analytics dashboard for user performance and trends</li>
        <li>⚡️ Async processing & queuing for heavy AI tasks (e.g., using Kafka or RabbitMQ)</li>
      </ul>
    </section>

  <section aria-labelledby="author">
      <h2 id="author">👨‍💻 Author</h2>
      <p style="color:var(--muted); margin:6px 0 0;">
        <strong>Nishant Singh</strong><br/>
        Backend Developer — Spring Boot, Java, MongoDB, AI integrations<br/>
        LinkedIn: <a href="https://www.linkedin.com/in/nishant-singh-95a15b2a6/" style="color:var(--accent); text-decoration:none;">Linkedln</a><br/>
        Email: <span style="color:var(--muted);">nishant16405@gmail.com</span>
      </p>
    </section>

  <section aria-labelledby="license">
      <h2 id="license">🏷 License</h2>
      <p style="color:var(--muted); margin:6px 0 0;">This project is open-source and available under the <strong>MIT License</strong>. See <code>LICENSE</code> for details.</p>
    </section>

  <div style="margin-top:14px;" class="note">
      <strong>Quick reviewer note:</strong> This HTML README mirrors the README.md content and is styled for presentation. Paste this into a file like <code>README.html</code> in your repo or use the markdown version for GitHub's README.md (HTML will also render inside GitHub pages).
    </div>

  <footer>
      <div style="color:var(--muted);">Last updated: <strong>October 2025</strong></div>
      <div style="color:var(--muted)">Project: AI Interview Preparation App</div>
    </footer>
  </div>
</body>
</html>
