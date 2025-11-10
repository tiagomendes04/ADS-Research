```java
import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

public class Message {
    private String id;
    private String senderId;
    private String content;
    private boolean isRead;

    public Message(String id, String senderId, String content) {
        this.id = id;
        this.senderId = senderId;
        this.content = content;
        this.isRead = false;
    }

    public String getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getContent() {
        return content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void markAsRead() {
        this.isRead = true;
    }
}

public class GroupChat {
    private String id;
    private List<User> members;
    private List<Message> messages;

    public GroupChat(String id) {
        this.id = id;
        this.members = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void addMember(User user) {
        this.members.add(user);
    }

    public void sendMessage(Message message) {
        this.messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }
}

public class Notification {
    private String userId;
    private String messageContent;

    public Notification(String userId, String messageContent) {
        this.userId = userId;
        this.messageContent = messageContent;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessageContent() {
        return messageContent;
    }
}

public class MessageService {
    private List<GroupChat> groupChats;

    public MessageService() {
        this.groupChats = new ArrayList<>();
    }

    public void createGroupChat(GroupChat groupChat) {
        this.groupChats.add(groupChat);
    }

    public void sendMessage(String groupId, Message message) {
        GroupChat groupChat = findGroupChatById(groupId);
        if (groupChat != null) {
            groupChat.sendMessage(message);
            notifyUsers(groupChat.getMessages().get(groupChat.getMessages().size() - 1));
        }
    }

    private GroupChat findGroupChatById(String groupId) {
        for (GroupChat groupChat : groupChats) {
            if (groupChat.getId().equals(groupId)) {
                return groupChat;
            }
        }
        return null;
    }

    private void notifyUsers(Message message) {
        GroupChat groupChat = findGroupChatById(message.getSenderId());
        for (User user : groupChat.getMembers()) {
            if (!user.getId().equals(message.getSenderId())) {
                String notificationContent = user.getName() + " just sent you a message: " + message.getContent();
                Notification notification = new Notification(user.getId(), notificationContent);
                // Code to send notification to user
            }
        }
    }
}
```