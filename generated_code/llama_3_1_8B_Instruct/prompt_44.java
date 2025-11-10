```java
package com.messagingapp.model;

import java.util.List;

public class User {
    private String id;
    private String username;
    private String email;

    public User() {}

    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

```java
package com.messagingapp.model;

import java.util.List;

public class Group {
    private String id;
    private String name;
    private List<User> members;

    public Group() {}

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
        this.members = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
```

```java
package com.messagingapp.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    private UUID id;
    private String content;
    private User sender;
    private LocalDateTime createdAt;

    public Message() {}

    public Message(String content, User sender) {
        this.id = UUID.randomUUID();
        this.content = content;
        this.sender = sender;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
```

```java
package com.messagingapp.model;

import java.util.List;

public class GroupChat {
    private String id;
    private Group group;
    private List<Message> messages;

    public GroupChat() {}

    public GroupChat(String id, Group group) {
        this.id = id;
        this.group = group;
        this.messages = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
```

```java
package com.messagingapp.model;

public class Notification {
    private String id;
    private String content;
    private User recipient;
    private LocalDateTime createdAt;

    public Notification() {}

    public Notification(String content, User recipient) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.recipient = recipient;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
```

```java
package com.messagingapp;

import com.messagingapp.model.Group;
import com.messagingapp.model.GroupChat;
import com.messagingapp.model.Message;
import com.messagingapp.model.Notification;
import com.messagingapp.model.User;

public class MessagingApp {
    private List<User> users;
    private List<Group> groups;
    private List<GroupChat> groupChats;
    private List<Message> messages;
    private List<Notification> notifications;

    public MessagingApp() {
        this.users = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.groupChats = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void addGroupChat(GroupChat groupChat) {
        this.groupChats.add(groupChat);
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    public void sendNotification(User recipient, String content) {
        Notification notification = new Notification(content, recipient);
        this.addNotification(notification);
    }

    public void sendMessage(GroupChat groupChat, User sender, String content) {
        Message message = new Message(content, sender);
        this.addMessage(message);
        groupChat.setMessages(groupChat.getMessages() + message);
    }

    public static void main(String[] args) {
        MessagingApp app = new MessagingApp();
        User user1 = new User("1", "John Doe", "john.doe@example.com");
        User user2 = new User("2", "Jane Doe", "jane.doe@example.com");

        app.addUser(user1);
        app.addUser(user2);

        Group group = new Group("1", "Friends");
        GroupChat groupChat = new GroupChat("1", group);

        app.addGroup(group);
        app.addGroupChat(groupChat);

        app.sendMessage(groupChat, user1, "Hello, world!");
        app.sendNotification(user2, "You have a new message from John Doe!");
    }
}
```

```java
package java.util;

import java.util.function.Consumer;

public class ArrayList<T> extends AbstractList<T> {
    private Object[] elementData;
    private int size;

    public ArrayList() {
        elementData = new Object[10];
    }

    @Override
    public T get(int index) {
        return (T) elementData[index];
    }

    @Override
    public void add(int index, T element) {
        ensureCapacity(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        T element = (T) elementData[index];
        System.arraycopy(elementData, index + 1, elementData, index, size - index - 1);
        size--;
        return element;
    }

    @Override
    public T set(int index, T element) {
        T oldValue = (T) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public int size() {
        return size;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elementData.length) {
            elementData = Arrays.copyOf(elementData, Math.max(minCapacity, elementData.length * 2));
        }
    }
}
```