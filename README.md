# QuickAsk  
An AI-based Spring Boot application leveraging **Ollama (DeepSeek-R1)** model.  

## 🚀 Features  
- AI-powered chatbot using **DeepSeek-R1**  
- Real-time chat updates with **HTMX**  
- Modern UI with **Tailwind CSS**

---

## 📂 Project Structure  
quick-ask/ │── src/main/java/com/quickask/ │ ├── Application.java # Main Spring Boot Application │ ├── ChatController.java # Handles chat API requests │── src/main/resources/templates/ │ ├── index.html # Chat UI │ ├── ask.html # Chat message fragment │── pom.xml # Maven dependencies │── README.md # Project Documentation

# Build the Application
./mvnw clean package

# Run the Application
java -jar target/quick-ask.jar

Default URL: http://localhost:8080

# Tech Stack

Backend: Spring Boot, Spring AI
Frontend: HTMX, Thymeleaf, Tailwind CSS
AI Model: Ollama DeepSeek R1

# Deploying on Azure
Deploy Spring Boot App on Azure App Service
Push your code to GitHub.
Go to Azure Portal → Create a Web App.
Select Java 17 and link it to your GitHub repo.
Deploy and get your public URL.

# Deploy Ollama on Azure VM
Create an Azure Virtual Machine (VM).
ssh azureuser@<PUBLIC_IP>
Install Ollama and DeepSeek R1:
  curl -fsSL https://ollama.ai/install.sh | sh
  ollama pull deepseek-r1:1.5b
  ollama serve &
az vm open-port --resource-group ollama-group --name ollama-vm --port 11434

# Update Spring Boot to Use Ollama
Modify application.properties:
  ollama.api.url=http://<PUBLIC_IP>:11434/api/generate

Modify ChatController.java:


    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestParam String message) {
        String apiUrl = "http://<PUBLIC_IP>:11434/api/generate";
        Map<String, String> requestBody = Map.of("model", "deepseek-r1:1.5b", "prompt", message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity(apiUrl, request, String.class);
    }


# API Endpoints
Method	Endpoint	Description
POST	/ai/generate	Generates AI response
GET	/ai/history	Retrieves chat history

# Contributing
Contributions are welcome! Feel free to open an issue or submit a PR.

# License
MIT License – Free to use & modify.

