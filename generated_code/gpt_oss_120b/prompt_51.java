```java
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/* ---------- Tag ---------- */
public class Tag {
    private final String name;

    public Tag(String name) {
        this.name = Objects.requireNonNull(name, "Tag name cannot be null").trim();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;
        return name.equalsIgnoreCase(tag.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase(Locale.ROOT).hashCode();
    }

    @Override
    public String toString() {
        return "#" + name;
    }
}

/* ---------- Photo ---------- */
public class Photo {
    private final UUID id;
    private String title;
    private String filePath;
    private LocalDateTime createdAt;
    private final Set<Tag> tags = new HashSet<>();

    public Photo(String title, String filePath, LocalDateTime createdAt) {
        this.id = UUID.randomUUID();
        this.title = Objects.requireNonNull(title);
        this.filePath = Objects.requireNonNull(filePath);
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Photo setTitle(String title) {
        this.title = Objects.requireNonNull(title);
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public Photo setFilePath(String filePath) {
        this.filePath = Objects.requireNonNull(filePath);
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Photo setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = Objects.requireNonNull(createdAt);
        return this;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Photo addTag(Tag tag) {
        tags.add(tag);
        return this;
    }

    public Photo removeTag(Tag tag) {
        tags.remove(tag);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;
        return id.equals(photo.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

/* ---------- Album ---------- */
public class Album {
    private final UUID id;
    private String name;
    private final List<Photo> photos = new ArrayList<>();

    public Album(String name) {
        this.id = UUID.randomUUID();
        this.name = Objects.requireNonNull(name);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Album setName(String name) {
        this.name = Objects.requireNonNull(name);
        return this;
    }

    public List<Photo> getPhotos() {
        return Collections.unmodifiableList(photos);
    }

    public Album addPhoto(Photo photo) {
        photos.add(Objects.requireNonNull(photo));
        return this;
    }

    public Album removePhoto(Photo photo) {
        photos.remove(photo);
        return this;
    }

    public Album clear() {
        photos.clear();
        return this;
    }
}

/* ---------- Gallery (manager) ---------- */
public class Gallery {
    private final Map<UUID, Album> albums = new HashMap<>();
    private final Map<UUID, Photo> allPhotos = new HashMap<>();

    public Album createAlbum(String name) {
        Album album = new Album(name);
        albums.put(album.getId(), album);
        return album;
    }

    public Optional<Album> getAlbum(UUID albumId) {
        return Optional.ofNullable(albums.get(albumId));
    }

    public Collection<Album> getAllAlbums() {
        return Collections.unmodifiableCollection(albums.values());
    }

    public Photo addPhotoToAlbum(UUID albumId, Photo photo) {
        Album album = albums.get(albumId);
        if (album == null) throw new NoSuchElementException("Album not found");
        album.addPhoto(photo);
        allPhotos.put(photo.getId(), photo);
        return photo;
    }

    public Optional<Photo> getPhoto(UUID photoId) {
        return Optional.ofNullable(allPhotos.get(photoId));
    }

    public Collection<Photo> getAllPhotos() {
        return Collections.unmodifiableCollection(allPhotos.values());
    }

    public void removePhoto(UUID photoId) {
        Photo photo = allPhotos.remove(photoId);
        if (photo == null) return;
        albums.values().forEach(a -> a.removePhoto(photo));
    }

    public void deleteAlbum(UUID albumId) {
        Album removed = albums.remove(albumId);
        if (removed != null) {
            removed.getPhotos().forEach(p -> allPhotos.remove(p.getId()));
        }
    }
}

/* ---------- SearchEngine ---------- */
public class SearchEngine {
    private final Gallery gallery;

    public SearchEngine(Gallery gallery) {
        this.gallery = Objects.requireNonNull(gallery);
    }

    public List<Photo> searchByTitle(String keyword) {
        String lower = keyword.toLowerCase(Locale.ROOT);
        return gallery.getAllPhotos().stream()
                .filter(p -> p.getTitle().toLowerCase(Locale.ROOT).contains(lower))
                .collect(Collectors.toList());
    }

    public List<Photo> searchByTag(String tagName) {
        Tag target = new Tag(tagName);
        return gallery.getAllPhotos().stream()
                .filter(p -> p.getTags().contains(target))
                .collect(Collectors.toList());
    }

    public List<Photo> searchByDateRange(LocalDateTime from, LocalDateTime to) {
        return gallery.getAllPhotos().stream()
                .filter(p -> !p.getCreatedAt().isBefore(from) && !p.getCreatedAt().isAfter(to))
                .collect(Collectors.toList());
    }

    public List<Photo> search(Predicate<Photo> filter) {
        return gallery.getAllPhotos().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }
}
```