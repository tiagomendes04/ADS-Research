```java
import java.util.ArrayList;
import java.util.Date;

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
}

class Post {
    private String content;
    private Date timestamp;
    private ArrayList<User> likes;
    private ArrayList<Comment> comments;

    public Post(String content) {
        this.content = content;
        this.timestamp = new Date();
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public void like(User user) {
        likes.add(user);
    }

    public void comment(User user, String commentContent) {
        comments.add(new Comment(user, commentContent, new Date()));
    }

    public String getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public ArrayList<User> getLikes() {
        return likes;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }
}

class Comment {
    private User user;
    private String content;
    private Date timestamp;

    public Comment(User user, String content, Date timestamp) {
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}

class SocialMediaFeed {
    private ArrayList<Post> posts;

    public SocialMediaFeed() {
        this.posts = new ArrayList<>();
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }
}

public class Main {
    public static void main(String[] args) {
        User user1 = new User("user1", "password1");
        User user2 = new User("user2", "password2");

        Post post1 = new Post("Hello, world!");
        post1.like(user1);
        post1.comment(user2, "Great post!");

        SocialMediaFeed feed = new SocialMediaFeed();
        feed.addPost(post1);

        for (Post post : feed.getPosts()) {
            System.out.println("Post: " + post.getContent() + " | Timestamp: " + post.getTimestamp());
            System.out.println("Likes: ");
            for (User likeUser : post.getLikes()) {
                System.out.println("- " + likeUser.getUsername());
            }
            System.out.println("Comments: ");
            for (Comment comment : post.getComments()) {
                System.out.println("- " + comment.getUser().getUsername() + ": " + comment.getContent() + " | Timestamp: " + comment.getTimestamp());
            }
        }
    }
}
```