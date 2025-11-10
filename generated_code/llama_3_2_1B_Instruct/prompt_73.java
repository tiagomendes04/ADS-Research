```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ChatBot {
    private Map<String, String> responses;
    private String conversationHistory;

    public ChatBot() {
        responses = new HashMap<>();
        responses.put("hello", "Hello! How are you?");
        responses.put("hi", "Hi! What's up?");
        responses.put("how are you", "I'm doing great, thanks for asking!");
        responses.put("bye", "See you later!");
        responses.put("default", "I didn't understand that. Can you please rephrase?");

        conversationHistory = "";
    }

    public void startConversation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the chat bot! Type 'bye' to exit.");

        while (true) {
            System.out.print("User: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                break;
            }

            String response = getResponse(input);
            conversationHistory += "User: " + input + "\nBot: " + response + "\n";
            System.out.println(response);

            if (response.contains("Please rephrase")) {
                System.out.print("User: ");
                input = scanner.nextLine();
                response = getResponse(input);
                conversationHistory += "User: " + input + "\nBot: " + response + "\n";
                System.out.println(response);
            }
        }

        System.out.println("Conversation History:\n" + conversationHistory);
    }

    private String getResponse(String input) {
        input = input.toLowerCase();

        for (Map.Entry<String, String> entry : responses.entrySet()) {
            if (input.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        return responses.get("default");
    }
}

public class Main {
    public static void main(String[] args) {
        ChatBot chatBot = new ChatBot();
        chatBot.startConversation();
    }
}
```