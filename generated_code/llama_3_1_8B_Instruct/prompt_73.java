```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ChatBot {
    private Map<String, String> responses;
    private Map<String, String> conversationHistory;

    public ChatBot() {
        this.responses = new HashMap<>();
        this.conversationHistory = new HashMap<>();

        // Define chat bot responses
        responses.put("hello", "Hi! How can I help you today?");
        responses.put("hi", "Hello! What's on your mind?");
        responses.put("how are you", "I'm good, thanks for asking!");
        responses.put("quit", "Goodbye! It was nice chatting with you.");

        // Initialize conversation history
        conversationHistory.put("user", "");
        conversationHistory.put("bot", "");
    }

    public void startConversation() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nYou: " + conversationHistory.get("user"));
            System.out.println("Bot: " + conversationHistory.get("bot"));

            if (conversationHistory.get("bot").equals(responses.get("quit"))) {
                break;
            }

            System.out.print("You: ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("quit")) {
                System.out.println("Bot: Goodbye! It was nice chatting with you.");
                break;
            }

            conversationHistory.put("user", userInput);
            conversationHistory.put("bot", responses.getOrDefault(userInput.toLowerCase(), "Sorry, I didn't understand that."));
        }

        scanner.close();
    }
}

public class Main {
    public static void main(String[] args) {
        ChatBot chatBot = new ChatBot();
        chatBot.startConversation();
    }
}
```

```java
// Alternative implementation using a GUI (Swing)
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBotGUI {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField userInputField;

    public ChatBotGUI() {
        // Define chat bot responses
        Map<String, String> responses = new HashMap<>();
        responses.put("hello", "Hi! How can I help you today?");
        responses.put("hi", "Hello! What's on your mind?");
        responses.put("how are you", "I'm good, thanks for asking!");
        responses.put("quit", "Goodbye! It was nice chatting with you.");

        frame = new JFrame("Chat Bot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);

        // Create chat area
        chatArea = new JTextArea(20, 30);
        chatArea.setEditable(false);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Create input field and send button
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        userInputField = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = userInputField.getText();
                userInputField.setText("");

                if (userInput.equalsIgnoreCase("quit")) {
                    System.out.println("Bot: Goodbye! It was nice chatting with you.");
                    System.exit(0);
                }

                chatArea.append("You: " + userInput + "\n");
                chatArea.append("Bot: " + responses.getOrDefault(userInput.toLowerCase(), "Sorry, I didn't understand that.") + "\n");
                frame.repaint();
            }
        });
        panel.add(userInputField);
        panel.add(sendButton);
        frame.add(panel, BorderLayout.PAGE_END);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ChatBotGUI();
    }
}
```