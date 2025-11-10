```java
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessagingApp {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private Map<String, ChatRoom> chatRooms = new HashMap<>();
    private Map<String, List<Message>> messageHistory = new HashMap<>();

    public void createChatRoom(String groupName) {
        chatRooms.put(groupName, new ChatRoom(groupName));
    }

    public void joinChatRoom(String groupName, String username) {
        ChatRoom chatRoom = chatRooms.get(groupName);
        if (chatRoom != null) {
            chatRoom.addMember(username);
        }
    }

    public void sendMessage(String groupName, String message, String sender) {
        ChatRoom chatRoom = chatRooms.get(groupName);
        if (chatRoom != null) {
            Message messageObj = new Message(sender, message);
            chatRoom.addMessage(messageObj);
            notifyMembers(groupName, messageObj);
        }
    }

    public void notifyMembers(String groupName, Message message) {
        List<String> members = chatRooms.get(groupName).getMembers();
        for (String member : members) {
            executor.execute(() -> {
                try {
                    System.out.println("Sending notification to " + member + " in group " + groupName);
                    // Implement notification logic here
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void addMessageHistory(String groupName, Message message) {
        List<Message> messages = messageHistory.getOrDefault(groupName, new ArrayList<>());
        messages.add(message);
        messageHistory.put(groupName, messages);
    }

    public void closeChatRoom(String groupName) {
        ChatRoom chatRoom = chatRooms.get(groupName);
        if (chatRoom != null) {
            chatRoom.close();
        }
    }

    // Other methods for managing chat rooms and messages...
}

class ChatRoom {
    private String groupName;
    private Set<String> members;
    private List<Message> messages;

    public ChatRoom(String groupName) {
        this.groupName = groupName;
        this.members = new java.util.HashSet<>();
        this.messages = new java.util.ArrayList<>();
    }

    public void addMember(String username) {
        members.add(username);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public Set<String> getMembers() {
        return members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void close() {
        // Implement closure logic
    }
}

class Message {
    private String sender;
    private String messageContent;

    public Message(String sender, String messageContent) {
        this.sender = sender;
        this.messageContent = messageContent;
    }

    public String getSender() {
        return sender;
    }

    public String getMessageContent() {
        return messageContent;
    }
}
```



```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessagingApp {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    // ... (other methods from previous code)
}
```