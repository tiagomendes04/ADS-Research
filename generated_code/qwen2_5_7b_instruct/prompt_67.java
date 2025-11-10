```java
import java.util.ArrayList;
import java.util.List;

class User {
    private String username;
    private String password;
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

class Post {
    private String title;
    private String content;
    private User author;
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public List<Comment> getComments() {
        return comments;
    }
}

class Comment {
    private String content;
    private User author;
    private Post post;

    public Comment(String content, User author, Post post) {
        this.content = content;
        this.author = author;
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public Post getPost() {
        return post;
    }
}

class Thread {
    private String title;
    private List<Post> posts = new ArrayList<>();

    public Thread(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public List<Post> getPosts() {
        return posts;
    }
}

class Forum {
    private List<User> users = new ArrayList<>();
    private List<Thread> threads = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public void addThread(Thread thread) {
        threads.add(thread);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Thread> getThreads() {
        return threads;
    }
}
```