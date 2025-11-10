```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SimpleChatBot {
    private Map<String, String> responses;
    private List<String> conversationHistory;

    public SimpleChatBot() {
        this.responses = new HashMap<>();
        this.conversationHistory = new ArrayList<>();
        initializeResponses();
    }

    private void initializeResponses() {
        responses.put("hello", "Hi there! How can I assist you today?");
        responses.put("how are you", "I'm just a bot, but I'm here and ready to help you!");
        responses.put("bye", "Goodbye! Have a great day!");
        responses.put("default", "I'm not sure how to respond to that.");
    }

    public String getResponse(String input) {
        String response = responses.getOrDefault(input.toLowerCase(), responses.get("default"));
        conversationHistory.add("User: " + input);
        conversationHistory.add("Bot: " + response);
        return response;
    }

    public void printConversationHistory() {
        System.out.println("Conversation History:");
        for (String entry : conversationHistory) {
            System.out.println(entry);
        }
    }

    public static void main(String[] args) {
        SimpleChatBot bot = new SimpleChatBot();
        Scanner scanner = new Scanner(System.in);
        String userInput;
        System.out.println("Simple Chat Bot - Type 'exit' to end the conversation and see the history.");

        do {
            System.out.print("You: ");
            userInput = scanner.nextLine();
            if (!userInput.equalsIgnoreCase("exit")) {
                System.out.println(bot.getResponse(userInput));
            }
        } while (!userInput.equalsIgnoreCase("exit"));

        bot.printConversationHistory();
        scanner.close();
    }
}
```