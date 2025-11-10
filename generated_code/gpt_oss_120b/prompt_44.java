```java
package com.example.messaging;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// ---------- Entities ----------
public class User {
    private final UUID id;
    private final String username;
    private final Set<Chat> chats = ConcurrentHashMap.newKeySet();

    public User(String username) {
        this.id = UUID.randomUUID();
        this.username = username;
    }

    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public Set<Chat> getChats() { return Collections.unmodifiableSet(chats); }

    void joinChat(Chat chat) { chats.add(chat); }
    void leaveChat(Chat chat) { chats.remove(chat); }
}

public abstract class Chat {
    protected final UUID id = UUID.randomUUID();
    protected final String name;
    protected final Set<User> participants = ConcurrentHashMap.newKeySet();
    protected final List<Message> messages = Collections.synchronizedList(new ArrayList<>());

    protected Chat(String name) { this.name = name; }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public Set<User> getParticipants() { return Collections.unmodifiableSet(participants); }
    public List<Message> getMessages() { return Collections.unmodifiableList(messages); }

    public void addParticipant(User user) {
        participants.add(user);
        user.joinChat(this);
    }

    public void removeParticipant(User user) {
        participants.remove(user);
        user.leaveChat(this);
    }

    public Message sendMessage(User sender, String content) {
        if (!participants.contains(sender))
            throw new IllegalArgumentException("Sender not part of the chat");
        Message msg = new Message(this, sender, content);
        messages.add(msg);
        NotificationService.getInstance().dispatch(msg);
        return msg;
    }
}

public class DirectChat extends Chat {
    public DirectChat(User a, User b) {
        super("DirectChat-" + a.getUsername() + "-" + b.getUsername());
        addParticipant(a);
        addParticipant(b);
    }
}

public class GroupChat extends Chat {
    public GroupChat(String groupName, User creator) {
        super(groupName);
        addParticipant(creator);
    }
}

public class Message {
    private final UUID id = UUID.randomUUID();
    private final Chat chat;
    private final User sender;
    private final String content;
    private final Instant timestamp = Instant.now();

    public Message(Chat chat, User sender, String content) {
        this.chat = chat;
        this.sender = sender;
        this.content = content;
    }

    public UUID getId() { return id; }
    public Chat getChat() { return chat; }
    public User getSender() { return sender; }
    public String getContent() { return content; }
    public Instant getTimestamp() { return timestamp; }
}

// ---------- Notification ----------
public class Notification {
    private final UUID id = UUID.randomUUID();
    private final User recipient;
    private final Message message;
    private final Instant createdAt = Instant.now();
    private boolean read = false;

    public Notification(User recipient, Message message) {
        this.recipient = recipient;
        this.message = message;
    }

    public UUID getId() { return id; }
    public User getRecipient() { return recipient; }
    public Message getMessage() { return message; }
    public Instant getCreatedAt() { return createdAt; }
    public boolean isRead() { return read; }
    public void markRead() { this.read = true; }
}

// ---------- Services ----------
class NotificationService {
    private static final NotificationService INSTANCE = new NotificationService();
    private final Map<UUID, List<Notification>> inbox = new ConcurrentHashMap<>();

    private NotificationService() {}

    public static NotificationService getInstance() { return INSTANCE; }

    public void dispatch(Message message) {
        for (User user : message.getChat().getParticipants()) {
            if (!user.equals(message.getSender())) {
                Notification n = new Notification(user, message);
                inbox.computeIfAbsent(user.getId(), k -> Collections.synchronizedList(new ArrayList<>()))
                     .add(n);
            }
        }
    }

    public List<Notification> getNotifications(User user) {
        return inbox.getOrDefault(user.getId(), Collections.emptyList());
    }

    public void clear(User user) {
        inbox.remove(user.getId());
    }
}

public class MessagingApp {
    private final Map<UUID, User> users = new ConcurrentHashMap<>();
    private final Map<UUID, Chat> chats = new ConcurrentHashMap<>();

    public User registerUser(String username) {
        User u = new User(username);
        users.put(u.getId(), u);
        return u;
    }

    public DirectChat createDirectChat(User a, User b) {
        DirectChat chat = new DirectChat(a, b);
        chats.put(chat.getId(), chat);
        return chat;
    }

    public GroupChat createGroupChat(String name, User creator) {
        GroupChat chat = new GroupChat(name, creator);
        chats.put(chat.getId(), chat);
        return chat;
    }

    public Optional<User> findUser(UUID id) { return Optional.ofNullable(users.get(id)); }
    public Optional<Chat> findChat(UUID id) { return Optional.ofNullable(chats.get(id)); }
}
```