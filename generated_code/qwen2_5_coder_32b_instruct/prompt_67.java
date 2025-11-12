```java
import java.util.ArrayList;
import java.util.List;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class Post {
    private User author;
    private String content;
    private List<Post> replies;

    public Post(User author, String content) {
        this.author = author;
        this.content = content;
        this.replies = new ArrayList<>();
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Post> getReplies() {
        return replies;
    }

    public void addReply(Post reply) {
        replies.add(reply);
    }
}

class Thread {
    private String title;
    private Post initialPost;
    private List<Post> posts;

    public Thread(String title, Post initialPost) {
        this.title = title;
        this.initialPost = initialPost;
        this.posts = new ArrayList<>();
        this.posts.add(initialPost);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Post getInitialPost() {
        return initialPost;
    }

    public void setInitialPost(Post initialPost) {
        this.initialPost = initialPost;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }
}

class Forum {
    private List<User> users;
    private List<Thread> threads;

    public Forum() {
        this.users = new ArrayList<>();
        this.threads = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void addThread(Thread thread) {
        threads.add(thread);
    }
}
```