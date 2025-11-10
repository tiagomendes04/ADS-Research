```java
import java.util.ArrayList;
import java.util.List;

class Thread {
    int id;
    String title;
    List<Post> posts;

    public Thread(int id, String title) {
        this.id = id;
        this.title = title;
        this.posts = new ArrayList<>();
    }
}

class Post {
    int id;
    String content;
    User author;

    public Post(int id, String content, User author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }
}

class User {
    int id;
    String username;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }
}
```

```java
import java.util.HashMap;
import java.util.Map;

class Forum {
    Map<Integer, Thread> threads;
    Map<Integer, User> users;

    public Forum() {
        this.threads = new HashMap<>();
        this.users = new HashMap<>();
    }

    public void createThread(int id, String title) {
        Thread thread = new Thread(id, title);
        this.threads.put(id, thread);
    }

    public void createUser(int id, String username) {
        User user = new User(id, username);
        this.users.put(id, user);
    }

    public void addPost(int threadId, String content, User author) {
        Thread thread = this.threads.get(threadId);
        if (thread != null) {
            Post post = new Post(thread.posts.size() + 1, content, author);
            thread.posts.add(post);
        }
    }

    // ... other methods
}
```