```java
import java.util.ArrayList;
import java.util.List;

class Post {
    private String content;
    private List<Comment> comments;
    private List<String> likes;
    private String author;

    public Post(String author, String content) {
        this.author = author;
        this.content = content;
        this.comments = new ArrayList<>();
        this.likes = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public List<String> getLikes() {
        return likes;
    }

    public void addLike(String user) {
        this.likes.add(user);
    }

    public String getAuthor() {
        return author;
    }
}

class Comment {
    private String content;
    private String author;

    public Comment(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }
}

public class SocialMediaFeed {
    private List<Post> posts;

    public SocialMediaFeed() {
        this.posts = new ArrayList<>();
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public List<Post> getPosts() {
        return posts;
    }

    public static void main(String[] args) {
        SocialMediaFeed feed = new SocialMediaFeed();
        Post post = new Post("John Doe", "Hello world!");
        feed.addPost(post);

        Comment comment = new Comment("Jane Doe", "Nice post!");
        post.addComment(comment);

        post.addLike("Bob Smith");

        for (Post p : feed.getPosts()) {
            System.out.println("Post by " + p.getAuthor());
            System.out.println(p.getContent());
            System.out.println("Likes: " + p.getLikes());
            System.out.println("Comments:");
            for (Comment c : p.getComments()) {
                System.out.println(c.getAuthor() + ": " + c.getContent());
            }
            System.out.println();
        }
    }
}
```