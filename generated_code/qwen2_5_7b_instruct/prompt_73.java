```java
import java.util.HashMap;
import java.util.Scanner;

public class SimpleChatBot {
    private HashMap<String, String> responses;
    private StringBuilder conversationHistory;

    public SimpleChatBot() {
        responses = new HashMap<>();
        conversationHistory = new StringBuilder();
        responses.put("hello", "Hello! How can I assist you today?");
        responses.put("how are you", "I'm just a bot, but thanks for asking!");
        responses.put("what is your name", "I'm a simple chat bot.");
        responses.put("bye", "Goodbye! Have a nice day!");
    }

    public void startConversation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(responses.get("hello"));
        conversationHistory.append("User: ").append("hello").append("\nBot: ").append(responses.get("hello")).append("\n");

        while (true) {
            System.out.print("User: ");
            String userInput = scanner.nextLine().trim().toLowerCase();
            if (userInput.isEmpty()) {
                System.out.println("Bot: Please enter a valid message.");
                continue;
            }

            if (userInput.equals("bye")) {
                System.out.println(responses.get("bye"));
                conversationHistory.append("User: ").append(userInput).append("\nBot: ").append(responses.get("bye")).append("\n");
                break;
            }

            String response = responses.getOrDefault(userInput, "Sorry, I didn't understand that.");
            System.out.println("Bot: " + response);
            conversationHistory.append("User: ").append(userInput).append("\nBot: ").append(response).append("\n");
        }

        System.out.println("Conversation History:\n" + conversationHistory);
    }

    public static void main(String[] args) {
        SimpleChatBot bot = new SimpleChatBot();
        bot.startConversation();
    }
}
```