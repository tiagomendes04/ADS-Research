```java
import java.util.ArrayList;
import java.util.List;

class Album {
    private String name;
    private List<Photo> photos;

    public Album(String name) {
        this.name = name;
        this.photos = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }
}

class Photo {
    private String name;
    private String tag;
    private String url;

    public Photo(String name, String tag, String url) {
        this.name = name;
        this.tag = tag;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

class PhotoGallery {
    private List<Album> albums;

    public PhotoGallery() {
        albums = new ArrayList<>();
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public void search(String query) {
        // Implementation for searching albums and photos based on query
    }
}
```