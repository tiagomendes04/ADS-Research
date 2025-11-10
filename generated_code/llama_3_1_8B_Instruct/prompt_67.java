```java
// User.java
package com.forum.model;

import java.util.Date;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private Date createdAt;

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = new Date();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

// Post.java
package com.forum.model;

import java.util.Date;

public class Post {
    private String id;
    private String content;
    private User author;
    private Date createdAt;

    public Post(String id, String content, User author) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.createdAt = new Date();
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

// Thread.java
package com.forum.model;

import java.util.ArrayList;
import java.util.List;

public class Thread {
    private String id;
    private String title;
    private User author;
    private List<Post> posts;

    public Thread(String id, String title, User author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.posts = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }
}

// Forum.java
package com.forum.model;

import java.util.ArrayList;
import java.util.List;

public class Forum {
    private List<Thread> threads;

    public Forum() {
        this.threads = new ArrayList<>();
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    public void addThread(Thread thread) {
        this.threads.add(thread);
    }
}

// ForumService.java
package com.forum.service;

import com.forum.model.Forum;
import com.forum.model.Thread;
import com.forum.model.User;

public class ForumService {
    private Forum forum;

    public ForumService() {
        this.forum = new Forum();
    }

    public void createUser(User user) {
        // Save user to database
    }

    public void createThread(Thread thread) {
        forum.addThread(thread);
        // Save thread to database
    }

    public void createPost(Thread thread, Post post) {
        thread.addPost(post);
        // Save post to database
    }
}
```

```java
// ForumController.java
package com.forum.controller;

import com.forum.model.Forum;
import com.forum.model.Thread;
import com.forum.model.User;
import com.forum.service.ForumService;

public class ForumController {
    private ForumService forumService;

    public ForumController() {
        this.forumService = new ForumService();
    }

    public void registerUser(User user) {
        forumService.createUser(user);
    }

    public void createThread(Thread thread) {
        forumService.createThread(thread);
    }

    public void createPost(Thread thread, Post post) {
        forumService.createPost(thread, post);
    }
}
```