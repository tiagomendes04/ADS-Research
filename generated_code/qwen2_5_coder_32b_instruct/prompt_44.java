```java
import java.util.ArrayList;
import java.util.List;

class User {
    private String username;
    private List<Message> notifications;

    public User(String username) {
        this.username = username;
        this.notifications = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void addNotification(Message message) {
        notifications.add(message);
    }

    public List<Message> getNotifications() {
        return notifications;
    }
}

class Message {
    private User sender;
    private String content;
    private long timestamp;

    public Message(User sender, String content) {
        this.sender = sender;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

class GroupChat {
    private String name;
    private List<User> members;
    private List<Message> messages;

    public GroupChat(String name) {
        this.name = name;
        this.members = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void sendMessage(Message message) {
        messages.add(message);
        notifyMembers(message);
    }

    private void notifyMembers(Message message) {
        for (User member : members) {
            if (!member.equals(message.getSender())) {
                member.addNotification(message);
            }
        }
    }

    public List<User> getMembers() {
        return members;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
```