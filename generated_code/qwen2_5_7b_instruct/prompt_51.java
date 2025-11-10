```java
import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryApp {
    public static void main(String[] args) {
        Album album1 = new Album("Vacation 2022");
        album1.addPhoto("Beach.jpg", "2022-07-15");
        album1.addPhoto("Cruise.jpg", "2022-07-20");

        Album album2 = new Album("Family Reunion");
        album2.addPhoto("Picnic.jpg", "2022-08-01");
        album2.addPhoto("Dinner.jpg", "2022-08-02");

        List<Album> albums = new ArrayList<>();
        albums.add(album1);
        albums.add(album2);

        Tag tag1 = new Tag("vacation");
        tag1.addPhoto("Beach.jpg");

        Tag tag2 = new Tag("family");
        tag2.addPhoto("Picnic.jpg");
        tag2.addPhoto("Dinner.jpg");

        List<Tag> tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);

        System.out.println("Search for 'vacation':");
        searchByTag(tags, "vacation");

        System.out.println("Search for '2022-07-20':");
        searchByDate(albums, "2022-07-20");
    }

    private static void searchByTag(List<Tag> tags, String tag) {
        for (Tag t : tags) {
            if (t.getName().equalsIgnoreCase(tag)) {
                System.out.println("Found photos with tag '" + tag + "':");
                for (String photo : t.getPhotos()) {
                    System.out.println(" - " + photo);
                }
            }
        }
    }

    private static void searchByDate(List<Album> albums, String date) {
        for (Album a : albums) {
            for (Photo p : a.getPhotos()) {
                if (p.getDate().equals(date)) {
                    System.out.println("Found photo '" + p.getName() + "' on " + date);
                }
            }
        }
    }
}

class Album {
    private String name;
    private List<Photo> photos;

    public Album(String name) {
        this.name = name;
        this.photos = new ArrayList<>();
    }

    public void addPhoto(String name, String date) {
        photos.add(new Photo(name, date));
    }

    public List<Photo> getPhotos() {
        return photos;
    }
}

class Photo {
    private String name;
    private String date;

    public Photo(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}

class Tag {
    private String name;
    private List<String> photos;

    public Tag(String name) {
        this.name = name;
        this.photos = new ArrayList<>();
    }

    public void addPhoto(String photo) {
        photos.add(photo);
    }

    public String getName() {
        return name;
    }

    public List<String> getPhotos() {
        return photos;
    }
}
```