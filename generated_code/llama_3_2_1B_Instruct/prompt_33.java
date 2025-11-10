```java
import java.util.ArrayList;
import java.util.List;

class User {
    private String id;
    private String name;
    private int likes;
    private int comments;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.likes = 0;
        this.comments = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public void like() {
        likes++;
    }

    public void comment() {
        comments++;
    }

    public void likePost() {
        likes++;
        this.like();
    }

    public void commentOnPost() {
        comments++;
        this.comment();
    }
}

class Post {
    private String id;
    private String text;
    private User author;
    private List<Comment> comments;

    public Post(String id, String text, User author) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.comments = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void like() {
        for (Comment c : comments) {
            c.like();
        }
    }

    public void comment() {
        for (Comment c : comments) {
            c.comment();
        }
    }
}

class Comment {
    private String id;
    private String text;
    private User author;

    public Comment(String id, String text, User author) {
        this.id = id;
        this.text = text;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    public void like() {
        for (Comment c : getComments()) {
            c.like();
        }
    }

    public void comment() {
        for (Comment c : getComments()) {
            c.comment();
        }
    }
}

public class SocialMediaFeed {
    public static void main(String[] args) {
        User user = new User("123", "John Doe");

        Post post = new Post("456", "Hello, world!", user);
        post.addComment(new Comment("789", "Nice post!", user));

        post.like();
        post.likePost();
        post.comment();

        System.out.println("Likes: " + post.getLikes());
        System.out.println("Comments: " + post.getComments());
    }
}
```