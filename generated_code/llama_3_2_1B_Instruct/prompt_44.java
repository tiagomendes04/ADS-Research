```java
import java.util.ArrayList;
import java.util.List;

public class MessagingApp {
    private String username;
    private String password;
    private List<GroupChat> groups;
    private List<Message> messages;

    public MessagingApp(String username, String password, List<GroupChat> groups) {
        this.username = username;
        this.password = password;
        this.groups = groups;
        this.messages = new ArrayList<>();
    }

    public void sendGroupChatMessage(String message, String recipientUsername) {
        GroupChat recipientGroup = getGroupChat(recipientUsername);
        recipientGroup.sendMessage(message);
    }

    public void sendPersonalMessage(String message, String senderUsername) {
        Message messageObject = new Message(senderUsername, message);
        messages.add(messageObject);
    }

    public List<GroupChat> getGroups() {
        return groups;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void createGroupChat(String chatName, String description) {
        GroupChat chat = new GroupChat(chatName, description);
        groups.add(chat);
    }

    private GroupChat getGroupChat(String username) {
        for (GroupChat group : groups) {
            if (group.getMembers().contains(username)) {
                return group;
            }
        }
        return null;
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class GroupChat {
    private String chatName;
    private String description;
    private List<Member> members;

    public GroupChat(String chatName, String description) {
        this.chatName = chatName;
        this.description = description;
        this.members = new ArrayList<>();
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void sendMessage(String message) {
        for (Member member : members) {
            member.sendMessage(message);
        }
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Member {
    private String username;

    public Member(String username) {
        this.username = username;
    }

    public void sendMessage(String message) {
        System.out.println(username + " received your message: " + message);
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Message {
    private String sender;
    private String recipient;
    private String message;

    public Message(String sender, String recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    public void sendMessage(MessageObject messageObject) {
        System.out.println(sender + " sent a message to " + recipient + ": " + messageObject.getMessage());
    }
}
```