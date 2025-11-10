```java
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class SocialMediaApp {

    // ---------- Models ----------
    public static class User {
        private final long id;
        private final String name;

        public User(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() { return id; }
        public String getName() { return name; }
    }

    public static class Comment {
        private final long id;
        private final User author;
        private final String content;
        private final LocalDateTime timestamp;

        public Comment(long id, User author, String content) {
            this.id = id;
            this.author = author;
            this.content = content;
            this.timestamp = LocalDateTime.now();
        }

        public long getId() { return id; }
        public User getAuthor() { return author; }
        public String getContent() { return content; }
        public LocalDateTime getTimestamp() { return timestamp; }

        @Override
        public String toString() {
            return author.getName() + " (" + timestamp + "): " + content;
        }
    }

    public static class Post {
        private final long id;
        private final User author;
        private final String content;
        private final LocalDateTime timestamp;
        private final List<User> likes = new ArrayList<>();
        private final List<Comment> comments = new ArrayList<>();

        public Post(long id, User author, String content) {
            this.id = id;
            this.author = author;
            this.content = content;
            this.timestamp = LocalDateTime.now();
        }

        public long getId() { return id; }
        public User getAuthor() { return author; }
        public String getContent() { return content; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public List<User> getLikes() { return likes; }
        public List<Comment> getComments() { return comments; }

        public void like(User user) {
            if (!likes.contains(user)) {
                likes.add(user);
            }
        }

        public void unlike(User user) {
            likes.remove(user);
        }

        public void addComment(Comment comment) {
            comments.add(comment);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(author.getName())
              .append(" (")
              .append(timestamp)
              .append("): ")
              .append(content)
              .append("\nLikes: ")
              .append(likes.size())
              .append("\nComments:\n");
            for (Comment c : comments) {
                sb.append("  ").append(c).append("\n");
            }
            return sb.toString();
        }
    }

    // ---------- Feed ----------
    public static class Feed {
        private final List<Post> posts = new ArrayList<>();
        private final AtomicLong postIdGen = new AtomicLong(1);
        private final AtomicLong commentIdGen = new AtomicLong(1);

        public Post createPost(User author, String content) {
            Post post = new Post(postIdGen.getAndIncrement(), author, content);
            posts.add(0, post); // newest first
            return post;
        }

        public void likePost(long postId, User user) {
            Post post = findPost(postId);
            if (post != null) post.like(user);
        }

        public void unlikePost(long postId, User user) {
            Post post = findPost(postId);
            if (post != null) post.unlike(user);
        }

        public Comment addComment(long postId, User author, String content) {
            Post post = findPost(postId);
            if (post == null) return null;
            Comment comment = new Comment(commentIdGen.getAndIncrement(), author, content);
            post.addComment(comment);
            return comment;
        }

        public List<Post> getPosts() {
            return new ArrayList<>(posts);
        }

        private Post findPost(long postId) {
            for (Post p : posts) {
                if (p.getId() == postId) return p;
            }
            return null;
        }
    }

    // ---------- Demo ----------
    public static void main(String[] args) {
        User alice = new User(1, "Alice");
        User bob = new User(2, "Bob");
        User carol = new User(3, "Carol");

        Feed feed = new Feed();

        Post p1 = feed.createPost(alice, "Hello world!");
        Post p2 = feed.createPost(bob, "Enjoying the sunshine.");

        feed.likePost(p1.getId(), bob);
        feed.likePost(p1.getId(), carol);
        feed.likePost(p2.getId(), alice);

        feed.addComment(p1.getId(), carol, "Nice post!");
        feed.addComment(p1.getId(), bob, "Welcome!");
        feed.addComment(p2.getId(), alice, "Great photo!");

        for (Post p : feed.getPosts()) {
            System.out.println(p);
            System.out.println("-----");
        }
    }
}
```