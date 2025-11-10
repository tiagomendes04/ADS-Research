```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatBot {

    private List<String> conversationHistory = new ArrayList<>();
    private String botResponse;

    public static void main(String[] args) {
        ChatBot chatbot = new ChatBot();
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the Chatbot! Type 'exit' to quit.");

        while (true) {
            System.out.print("> ");
            String userMessage = input.nextLine();
            if (userMessage.equalsIgnoreCase("exit")) {
                break;
            }
            chatbot.handleInput(userMessage);
        }
        input.close();
    }

    public void handleInput(String message) {
        conversationHistory.add(message);
        botResponse = processResponse(message);
        System.out.println("Bot: " + botResponse);
    }

    private String processResponse(String userMessage) {
        String response = "";
        // Add your chatbot logic here
        // For example:
        if (userMessage.startsWith("hello")) {
            response = "Hello there!";
        } else if (userMessage.startsWith("how are you")) {
            response = "I'm doing well, thank you for asking!";
        } else {
            response = "I didn't understand that. Can you rephrase?";
        }
        return response;
    }
}
```