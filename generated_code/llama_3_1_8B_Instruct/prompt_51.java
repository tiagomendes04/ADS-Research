```java
// Photo.java
public class Photo {
    private String id;
    private String title;
    private String description;
    private String path;
    private List<Tag> tags;

    public Photo(String id, String title, String description, String path) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.path = path;
        this.tags = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPath() {
        return path;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }
}
```

```java
// Album.java
public class Album {
    private String id;
    private String name;
    private List<Photo> photos;

    public Album(String id, String name) {
        this.id = id;
        this.name = name;
        this.photos = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }
}
```

```java
// Tag.java
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
// PhotoGallery.java
public class PhotoGallery {
    private List<Album> albums;

    public PhotoGallery() {
        this.albums = new ArrayList<>();
    }

    public void addAlbum(Album album) {
        this.albums.add(album);
    }

    public List<Album> getAlbums() {
        return albums;
    }
}
```

```java
// SearchCriteria.java
public class SearchCriteria {
    private String query;
    private List<Album> albums;

    public SearchCriteria(String query) {
        this.query = query;
        this.albums = new ArrayList<>();
    }

    public String getQuery() {
        return query;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Album> getAlbums() {
        return albums;
    }
}
```

```java
// AlbumService.java
public class AlbumService {
    public void addPhotoToAlbum(PhotoGallery photoGallery, Album album, Photo photo) {
        album.addPhoto(photo);
        photo.addTag(new Tag(photo.getId(), album.getName()));
    }
}
```

```java
// SearchService.java
public class SearchService {
    public List<Album> search(PhotoGallery photoGallery, SearchCriteria searchCriteria) {
        List<Album> results = new ArrayList<>();
        for (Album album : photoGallery.getAlbums()) {
            if (album.getName().toLowerCase().contains(searchCriteria.getQuery().toLowerCase())) {
                results.add(album);
            }
        }
        return results;
    }
}
```

```java
// Main.java
public class Main {
    public static void main(String[] args) {
        PhotoGallery photoGallery = new PhotoGallery();

        Album album1 = new Album("1", "Album 1");
        Album album2 = new Album("2", "Album 2");

        photoGallery.addAlbum(album1);
        photoGallery.addAlbum(album2);

        AlbumService albumService = new AlbumService();
        albumService.addPhotoToAlbum(photoGallery, album1, new Photo("1", "Photo 1", "Description 1", "path1"));
        albumService.addPhotoToAlbum(photoGallery, album2, new Photo("2", "Photo 2", "Description 2", "path2"));

        SearchCriteria searchCriteria = new SearchCriteria("Album");
        SearchService searchService = new SearchService();
        List<Album> results = searchService.search(photoGallery, searchCriteria);
        System.out.println(results);
    }
}
```