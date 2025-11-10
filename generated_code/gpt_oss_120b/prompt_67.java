```java
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

// ---------- Role ----------
enum Role {
    ADMIN,
    MODERATOR,
    MEMBER,
    GUEST
}

// ---------- User ----------
class User {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    private final long id;
    private String username;
    private String email;
    private String passwordHash;
    private final Set<Role> roles = new HashSet<>();
    private final LocalDateTime registeredAt = LocalDateTime.now();

    public User(String username, String email, String passwordHash) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roles.add(Role.MEMBER);
    }

    public long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Set<Role> getRoles() { return Collections.unmodifiableSet(roles); }
    public LocalDateTime getRegisteredAt() { return registeredAt; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public void addRole(Role role) { roles.add(role); }
    public void removeRole(Role role) { roles.remove(role); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}

// ---------- Post ----------
class Post {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    private final long id;
    private final User author;
    private final ForumThread thread;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime editedAt;
    private String content;
    private final Post parent; // null for top‑level posts

    private final List<Post> replies = new ArrayList<>();

    public Post(User author, ForumThread thread, String content, Post parent) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.author = author;
        this.thread = thread;
        this.content = content;
        this.parent = parent;
    }

    public long getId() { return id; }
    public User getAuthor() { return author; }
    public ForumThread getThread() { return thread; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getEditedAt() { return editedAt; }
    public String getContent() { return content; }
    public Post getParent() { return parent; }
    public List<Post> getReplies() { return Collections.unmodifiableList(replies); }

    public void editContent(String newContent) {
        this.content = newContent;
        this.editedAt = LocalDateTime.now();
    }

    public void addReply(Post reply) {
        replies.add(reply);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}

// ---------- ForumThread ----------
class ForumThread {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    private final long id;
    private String title;
    private final User creator;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final List<Post> posts = new ArrayList<>();

    public ForumThread(String title, User creator) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.title = title;
        this.creator = creator;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public User getCreator() { return creator; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<Post> getPosts() { return Collections.unmodifiableList(posts); }

    public void setTitle(String title) { this.title = title; }

    public Post addPost(User author, String content) {
        Post post = new Post(author, this, content, null);
        posts.add(post);
        return post;
    }

    public Post addReply(User author, String content, long parentPostId) {
        Post parent = findPostById(parentPostId);
        if (parent == null) throw new IllegalArgumentException("Parent post not found");
        Post reply = new Post(author, this, content, parent);
        parent.addReply(reply);
        posts.add(reply);
        return reply;
    }

    private Post findPostById(long postId) {
        for (Post p : posts) {
            if (p.getId() == postId) return p;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForumThread that = (ForumThread) o;
        return id == that.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}

// ---------- Forum ----------
class Forum {
    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, ForumThread> threads = new HashMap<>();

    // ----- User Management -----
    public User registerUser(String username, String email, String passwordHash) {
        if (users.values().stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username))) {
            throw new IllegalArgumentException("Username already taken");
        }
        User user = new User(username, email, passwordHash);
        users.put(user.getId(), user);
        return user;
    }

    public Optional<User> findUserById(long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public Optional<User> findUserByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    // ----- Thread Management -----
    public ForumThread createThread(String title, User creator) {
        ForumThread thread = new ForumThread(title, creator);
        threads.put(thread.getId(), thread);
        return thread;
    }

    public Optional<ForumThread> getThreadById(long threadId) {
        return Optional.ofNullable(threads.get(threadId));
    }

    public List<ForumThread> listAllThreads() {
        return new ArrayList<>(threads.values());
    }

    // ----- Post Management -----
    public Post addPostToThread(long threadId, User author, String content) {
        ForumThread thread = threads.get(threadId);
        if (thread == null) throw new IllegalArgumentException("Thread not found");
        return thread.addPost(author, content);
    }

    public Post replyToPost(long threadId, long parentPostId, User author, String content) {
        ForumThread thread = threads.get(threadId);
        if (thread == null) throw new IllegalArgumentException("Thread not found");
        return thread.addReply(author, content, parentPostId);
    }
}
```