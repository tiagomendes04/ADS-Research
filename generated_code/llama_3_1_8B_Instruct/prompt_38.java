```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Song {
    private String title;
    private String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }
}

class MusicPlayer {
    private ArrayList<Song> playlist;
    private int currentIndex;
    private boolean shuffle;
    private boolean repeat;

    public MusicPlayer() {
        this.playlist = new ArrayList<>();
        this.currentIndex = 0;
        this.shuffle = false;
        this.repeat = false;
    }

    public void addSong(Song song) {
        playlist.add(song);
    }

    public void play() {
        if (playlist.isEmpty()) {
            System.out.println("No songs in the playlist.");
            return;
        }

        if (shuffle) {
            Collections.shuffle(playlist);
        }

        if (repeat) {
            currentIndex = 0;
        }

        playNextSong();
    }

    public void playNextSong() {
        if (currentIndex >= playlist.size()) {
            if (repeat) {
                currentIndex = 0;
            } else {
                System.out.println("End of playlist.");
                return;
            }
        }

        Song currentSong = playlist.get(currentIndex);
        System.out.println("Playing: " + currentSong.getTitle() + " by " + currentSong.getArtist());
        currentIndex++;
    }

    public void shuffle() {
        this.shuffle = true;
    }

    public void unshuffle() {
        this.shuffle = false;
    }

    public void repeat() {
        this.repeat = true;
    }

    public void unrepeat() {
        this.repeat = false;
    }

    public void removeSong() {
        if (playlist.isEmpty()) {
            System.out.println("No songs in the playlist.");
            return;
        }

        playlist.remove(currentIndex);
        currentIndex = Math.max(currentIndex - 1, 0);

        if (currentIndex >= playlist.size()) {
            currentIndex = 0;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        MusicPlayer musicPlayer = new MusicPlayer();

        musicPlayer.addSong(new Song("Song 1", "Artist 1"));
        musicPlayer.addSong(new Song("Song 2", "Artist 2"));
        musicPlayer.addSong(new Song("Song 3", "Artist 3"));

        musicPlayer.play();

        musicPlayer.shuffle();
        musicPlayer.play();

        musicPlayer.repeat();
        musicPlayer.play();

        musicPlayer.removeSong();
        musicPlayer.play();
    }
}
```