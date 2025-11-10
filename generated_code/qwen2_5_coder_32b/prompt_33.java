```java
import java.util.ArrayList;
import java.util.List;

class Post {
    private String content;
    private int likes;
    private List<Comment> comments;

    public Post(String content) {
        this.content = content;
        this.likes = 0;
        this.comments = new ArrayList<>();
    }

    public void like() {
        this.likes++;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes;
    }

    public List<Comment> getComments() {
        return comments;
    }
}

class Comment {
    private String text;

    public Comment(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

class SocialMediaFeed {
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
}

public class Main {
    public static void main(String[] args) {
        SocialMediaFeed feed = new SocialMediaFeed();
        Post post1 = new Post("Hello World!");
        post1.like();
        post1.like();
        post1.addComment(new Comment("Great post!"));
        post1.addComment(new Comment("Thanks!"));
        feed.addPost(post1);

        Post post2 = new Post("Java is awesome.");
        post2.like();
        post2.addComment(new Comment("Agreed!"));
        feed.addPost(post2);

        for (Post post : feed.getPosts()) {
            System.out.println("Post: " + post.getContent());
            System.out.println("Likes: " + post.getLikes());
            System.out.println("Comments:");
            for (Comment comment : post.getComments()) {
                System.out.println(" - " + comment.getText());
            }
            System.out.println();
        }
    }
}
```