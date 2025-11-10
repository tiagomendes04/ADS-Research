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

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}

class Thread {
    private String title;
    private List<User> authors;
    private List<Post> posts;

    public Thread(String title) {
        this.title = title;
        this.authors = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void addAuthor(User author) {
        this.authors.add(author);
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thread thread = (Thread) o;
        return Objects.equals(title, thread.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}

class Post {
    private String title;
    private String content;
    private User author;
    private Thread thread;

    public Post(String title, String content, User author, Thread thread) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.thread = thread;
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

    public Thread getThread() {
        return thread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(title, post.title) && Objects.equals(author, post.author) && Objects.equals(thread, post.thread);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, thread);
    }
}

class Forum {
    private List<User> users;
    private List<Post> posts;

    public Forum() {
        this.users = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Post> getPosts() {
        return posts;
    }
}

class Main {
    public static void main(String[] args) {
        User user1 = new User("John", "password123");
        User user2 = new User("Alice", "password456");

        Thread thread1 = new Thread("Thread1");
        Thread thread2 = new Thread("Thread2");

        Post post1 = new Post("Thread1", "Hello", user1, thread1);
        Post post2 = new Post("Thread2", "Hello", user2, thread2);

        Forum forum = new Forum();
        forum.addUser(user1);
        forum.addUser(user2);
        forum.addPost(post1);
        forum.addPost(post2);

        for (User user : forum.getUsers()) {
            System.out.println(user.getUsername());
        }
    }
}
```