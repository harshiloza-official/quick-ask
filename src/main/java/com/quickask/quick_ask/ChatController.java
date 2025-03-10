package com.quickask.quick_ask;


import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    private final OllamaChatModel chatModel;
    private final List<String> chatHistory = new ArrayList<>();

    @Autowired
    public ChatController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/ai/generate")
    public String generate(@RequestParam(value = "message") String message, Model model) {
        String rawResponse = this.chatModel.call(message);

        // Remove unwanted <think> tags
        String aiResponse = rawResponse.replaceAll("<think>|</think>", "");

        model.addAttribute("message", message);
        model.addAttribute("ask", aiResponse);


        chatHistory.add("You: " + message);
        chatHistory.add("AI: " + aiResponse);

        return "ask :: responseFragment"; // Correct Thymeleaf fragment name
    }


    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }

    @GetMapping("/ai/history")
    @ResponseBody  // This ensures JSON response instead of Thymeleaf rendering
    public List<String> getChatHistory() {
        return chatHistory;
    }

}
