```java
// User.java
package model;

public class User {
    private String email;
    private String name;

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}

// Newsletter.java
package model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Newsletter {
    private String subject;
    private String content;
    private LocalTime scheduleTime;
    private LocalDateTime nextSendTime;

    public Newsletter(String subject, String content, LocalTime scheduleTime) {
        this.subject = subject;
        this.content = content;
        this.scheduleTime = scheduleTime;
        this.nextSendTime = LocalDateTime.now().withHour(scheduleTime.getHour()).withMinute(scheduleTime.getMinute());
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public LocalTime getScheduleTime() {
        return scheduleTime;
    }

    public LocalDateTime getNextSendTime() {
        return nextSendTime;
    }

    public void updateNextSendTime() {
        this.nextSendTime = this.nextSendTime.plusDays(1);
    }
}
```

```java
// NewsletterService.java
package service;

import model.Newsletter;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NewsletterService {
    private List<User> subscribers;
    private List<Newsletter> newsletters;

    public NewsletterService() {
        this.subscribers = new ArrayList<>();
        this.newsletters = new ArrayList<>();
    }

    public void subscribe(User user) {
        this.subscribers.add(user);
    }

    public void unsubscribe(User user) {
        this.subscribers.remove(user);
    }

    public void addNewsletter(Newsletter newsletter) {
        this.newsletters.add(newsletter);
    }

    public void sendNewsletters() {
        List<Newsletter> activeNewsletters = this.newsletters.stream()
                .filter(nl -> nl.getNextSendTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());

        activeNewsletters.forEach(nl -> {
            nl.updateNextSendTime();
            sendNewsletter(nl);
        });
    }

    private void sendNewsletter(Newsletter newsletter) {
        // Simulating email sending
        System.out.println("Sending newsletter: " + newsletter.getSubject());
        System.out.println("To: " + this.subscribers.stream()
                .map(User::getEmail)
                .collect(Collectors.joining(", ")));
        System.out.println("Content: " + newsletter.getContent());
    }
}
```

```java
// Main.java
package main;

import model.Newsletter;
import model.User;
import service.NewsletterService;

public class Main {
    public static void main(String[] args) {
        NewsletterService newsletterService = new NewsletterService();

        User user1 = new User("user1@example.com", "John Doe");
        User user2 = new User("user2@example.com", "Jane Doe");

        newsletterService.subscribe(user1);
        newsletterService.subscribe(user2);

        Newsletter newsletter = new Newsletter("Hello World!", "This is a test newsletter.", LocalTime.of(10, 0));
        newsletterService.addNewsletter(newsletter);

        newsletterService.sendNewsletters();
    }
}
```