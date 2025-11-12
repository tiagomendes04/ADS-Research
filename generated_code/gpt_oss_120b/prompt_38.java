```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MusicPlayer {

    public static void main(String[] args) {
        // Sample usage
        Playlist playlist = new Playlist("My Favorites");
        playlist.addSong(new Song("Imagine", "John Lennon", 183));
        playlist.addSong(new Song("Bohemian Rhapsody", "Queen", 354));
        playlist.addSong(new Song("Stairway to Heaven", "Led Zeppelin", 482));
        playlist.addSong(new Song("Hotel California", "Eagles", 391));

        Player player = new Player(playlist);
        player.play();          // Play first song
        player.next();          // Next song
        player.toggleShuffle(); // Enable shuffle
        player.next();          // Next shuffled song
        player.toggleRepeatMode(); // Cycle repeat mode (NONE -> ONE -> ALL)
        player.next();          // Next song respecting repeat mode
        player.stop();          // Stop playback
    }
}

/* ---------- Core Models ---------- */

class Song {
    private final String title;
    private final String artist;
    private final int durationSeconds; // length of the song

    public Song(String title, String artist, int durationSeconds) {
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
    }

    public String getTitle()   { return title; }
    public String getArtist()  { return artist; }
    public int getDuration()   { return durationSeconds; }

    @Override
    public String toString() {
        return String.format("%s - %s (%d:%02d)",
                artist, title, durationSeconds / 60, durationSeconds % 60);
    }
}

class Playlist {
    private final String name;
    private final List<Song> songs = new ArrayList<>();

    public Playlist(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public List<Song> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    public int size() {
        return songs.size();
    }

    public Song get(int index) {
        return songs.get(index);
    }
}

/* ---------- Player Logic ---------- */

class Player {
    private final Playlist playlist;
    private final List<Integer> playOrder = new ArrayList<>();
    private int currentIndex = -1;
    private boolean isPlaying = false;
    private boolean shuffle = false;
    private RepeatMode repeatMode = RepeatMode.NONE;
    private final Random rng = new Random();

    public Player(Playlist playlist) {
        this.playlist = playlist;
        rebuildPlayOrder();
    }

    public void play() {
        if (playlist.size() == 0) {
            System.out.println("Playlist is empty.");
            return;
        }
        if (currentIndex == -1) currentIndex = 0;
        isPlaying = true;
        printNowPlaying();
    }

    public void stop() {
        isPlaying = false;
        System.out.println("Playback stopped.");
    }

    public void next() {
        if (playlist.size() == 0) return;
        if (repeatMode == RepeatMode.ONE) {
            // stay on same track
        } else {
            currentIndex++;
            if (currentIndex >= playOrder.size()) {
                if (repeatMode == RepeatMode.ALL) {
                    rebuildPlayOrder();
                    currentIndex = 0;
                } else {
                    currentIndex = playOrder.size() - 1;
                    System.out.println("Reached end of playlist.");
                    stop();
                    return;
                }
            }
        }
        if (isPlaying) printNowPlaying();
    }

    public void previous() {
        if (playlist.size() == 0) return;
        if (repeatMode == RepeatMode.ONE) {
            // stay on same track
        } else {
            currentIndex--;
            if (currentIndex < 0) {
                if (repeatMode == RepeatMode.ALL) {
                    rebuildPlayOrder();
                    currentIndex = playOrder.size() - 1;
                } else {
                    currentIndex = 0;
                    System.out.println("At start of playlist.");
                }
            }
        }
        if (isPlaying) printNowPlaying();
    }

    public void toggleShuffle() {
        shuffle = !shuffle;
        System.out.println("Shuffle " + (shuffle ? "enabled" : "disabled"));
        rebuildPlayOrder();
        // reset index to current track in new order
        if (isPlaying && currentIndex >= 0) {
            int currentSongId = getCurrentSongId();
            currentIndex = playOrder.indexOf(currentSongId);
        }
    }

    public void toggleRepeatMode() {
        repeatMode = repeatMode.next();
        System.out.println("Repeat mode set to " + repeatMode);
    }

    private void rebuildPlayOrder() {
        playOrder.clear();
        for (int i = 0; i < playlist.size(); i++) playOrder.add(i);
        if (shuffle) Collections.shuffle(playOrder, rng);
    }

    private int getCurrentSongId() {
        return (currentIndex >= 0 && currentIndex < playOrder.size())
                ? playOrder.get(currentIndex)
                : -1;
    }

    private Song getCurrentSong() {
        int id = getCurrentSongId();
        return id >= 0 ? playlist.get(id) : null;
    }

    private void printNowPlaying() {
        Song song = getCurrentSong();
        if (song != null) {
            System.out.println("Now playing: " + song);
        } else {
            System.out.println("No song to play.");
        }
    }

    enum RepeatMode {
        NONE, ONE, ALL;

        public RepeatMode next() {
            return values()[(ordinal() + 1) % values().length];
        }
    }
}
```