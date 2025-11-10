```java
public class Album {
    private String id;
    private String name;
    private String url;
    private List<Tag> tags;

    public Album(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.tags = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public List<Tag> getTags() {
        return tags;
    }
}
```

```java
public class Tag {
    private String id;
    private String name;

    public Tag(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class AlbumRepository {
    private List<Album> albums;

    public AlbumRepository() {
        this.albums = new ArrayList<>();
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public List<Album> getAllAlbums() {
        return albums;
    }

    public Album getAlbumById(String id) {
        for (Album album : albums) {
            if (album.getId().equals(id)) {
                return album;
            }
        }
        return null;
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class AlbumService {
    private AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> getAllAlbums() {
        return albumRepository.getAllAlbums();
    }

    public Album getAlbumById(String id) {
        return albumRepository.getAlbumById(id);
    }

    public void addAlbum(Album album) {
        albumRepository.addAlbum(album);
    }

    public void updateAlbum(Album album) {
        albumRepository.updateAlbum(album);
    }

    public void deleteAlbum(String id) {
        albumRepository.deleteAlbum(id);
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class TagService {
    private TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.getAllTags();
    }

    public void addTag(Tag tag) {
        tagRepository.addTag(tag);
    }

    public void updateTag(Tag tag) {
        tagRepository.updateTag(tag);
    }

    public void deleteTag(String id) {
        tagRepository.deleteTag(id);
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class TagRepository {
    private List<Tag> tags;

    public TagRepository() {
        this.tags = new ArrayList<>();
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void updateTag(Tag tag) {
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getId().equals(tag.getId())) {
                tags.set(i, tag);
                break;
            }
        }
    }

    public void deleteTag(String id) {
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getId().equals(id)) {
                tags.remove(i);
                break;
            }
        }
    }

    public List<Tag> getAllTags() {
        return tags;
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class AlbumSearchService {
    private AlbumRepository albumRepository;

    public AlbumSearchService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> searchAlbums(String name, String tag) {
        List<Album> albums = new ArrayList<>();
        for (Album album : albumRepository.getAllAlbums()) {
            if (album.getName().toLowerCase().contains(name.toLowerCase()) || album.getName().contains(tag.toLowerCase())) {
                albums.add(album);
            }
        }
        return albums;
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class SearchService {
    private AlbumRepository albumRepository;

    public SearchService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> searchAlbums(String name, String tag) {
        return albumRepository.searchAlbums(name, tag);
    }
}
```

```java
public class Photo {
    private String id;
    private String url;

    public Photo(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
```