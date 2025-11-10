```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Message {
    enum Sender { USER, BOT }

    private final Sender sender;
    private final String text;

    Message(Sender sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    Sender getSender() { return sender; }
    String getText() { return text; }

    @Override
    public String toString() {
        return (sender == Sender.USER ? "You: " : "Bot: ") + text;
    }
}

class Conversation {
    private final List<Message> history = new ArrayList<>();

    void addMessage(Message message) {
        history.add(message);
    }

    List<Message> getHistory() {
        return new ArrayList<>(history);
    }

    void printHistory() {
        for (Message m : history) {
            System.out.println(m);
        }
    }
}

class ChatBot {
    private final Conversation conversation;

    ChatBot(Conversation conversation) {
        this.conversation = conversation;
    }

    String getResponse(String input) {
        String lowered = input.toLowerCase();
        String response;
        if (lowered.contains("hello") || lowered.contains("hi")) {
            response = "Hello! How can I help you today?";
        } else if (lowered.contains("how are you")) {
            response = "I'm just a bunch of code, but I'm functioning as expected!";
        } else if (lowered.contains("bye") || lowered.contains("goodbye")) {
            response = "Goodbye! Have a great day!";
        } else {
            response = "I'm not sure how to respond to that.";
        }
        conversation.addMessage(new Message(Message.Sender.BOT, response));
        return response;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        Conversation conversation = new Conversation();
        ChatBot bot = new ChatBot(conversation);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Simple ChatBot. Type 'exit' to quit, 'history' to view conversation.");

        while (true) {
            System.out.print("> ");
            String userInput = reader.readLine();
            if (userInput == null) break;
            userInput = userInput.trim();
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Exiting chat.");
                break;
            }
            if (userInput.equalsIgnoreCase("history")) {
                conversation.printHistory();
                continue;
            }
            conversation.addMessage(new Message(Message.Sender.USER, userInput));
            String botReply = bot.getResponse(userInput);
            System.out.println("Bot: " + botReply);
        }
    }
}
```