```java
import java.util.ArrayList;
import java.util.List;

public class SocialMediaFeed {

    List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        posts.add(post);
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void likePost(int postId) {
        // Implementation for liking a post
    }

    public void commentPost(int postId, String comment) {
        // Implementation for commenting on a post
    }
}
```

```java
class Post {
    String content;
    String username;
    int id;
    List<Comment> comments = new ArrayList<>();
    int likes;
    
    public Post(String content, String username, int id) {
        this.content = content;
        this.username = username;
        this.id = id;
        this.likes = 0;
    }
}

class Comment {
    String comment;
    String username;
    int postId;
    
    public Comment(String comment, String username, int postId) {
        this.comment = comment;
        this.username = username;
        this.postId = postId;
    }
}
```