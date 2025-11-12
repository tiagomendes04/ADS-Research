```java
import java.util.*;

class Photo {
    private String id;
    private String name;
    private Set<String> tags;

    public Photo(String id, String name) {
        this.id = id;
        this.name = name;
        this.tags = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public Set<String> getTags() {
        return tags;
    }
}

class Album {
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

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public List<Photo> getPhotos() {
        return photos;
    }
}

class PhotoGallery {
    private Map<String, Album> albums;
    private Map<String, List<Photo>> tagIndex;

    public PhotoGallery() {
        this.albums = new HashMap<>();
        this.tagIndex = new HashMap<>();
    }

    public void createAlbum(String albumId, String albumName) {
        albums.put(albumId, new Album(albumId, albumName));
    }

    public void addPhotoToAlbum(String albumId, Photo photo) {
        if (albums.containsKey(albumId)) {
            Album album = albums.get(albumId);
            album.addPhoto(photo);
            indexPhotoByTags(photo);
        }
    }

    private void indexPhotoByTags(Photo photo) {
        for (String tag : photo.getTags()) {
            tagIndex.computeIfAbsent(tag, k -> new ArrayList<>()).add(photo);
        }
    }

    public List<Photo> searchByTag(String tag) {
        return tagIndex.getOrDefault(tag, Collections.emptyList());
    }

    public Album getAlbumById(String albumId) {
        return albums.get(albumId);
    }
}
```