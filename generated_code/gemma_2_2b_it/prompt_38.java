```java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicPlayer {

    private List<Song> playlist;
    private Random random;

    public MusicPlayer() {
        playlist = new ArrayList<>();
        random = new Random();
    }

    public void addSong(Song song) {
        playlist.add(song);
    }

    public void shufflePlaylist() {
        random.shuffle(playlist.subList(1, playlist.size()));
    }

    public void playSong() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }

        System.out.println("Playing: " + playlist.get(0).getName());
        // Implement playback logic here
    }

    public void playNextSong() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }

        int nextIndex = (random.nextInt(playlist.size()));
        playSong();
    }

    public void playRepeat() {
        // Implement repeat functionality here
    }
}
``` 
 
```java
class Song {
    private String name;
    private String artist;

    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }
}
```