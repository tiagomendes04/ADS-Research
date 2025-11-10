```java
import java.util.*;

public class MusicPlayerApp {
    public static void main(String[] args) {
        Song s1 = new Song("Song A", "Artist 1", 210);
        Song s2 = new Song("Song B", "Artist 2", 185);
        Song s3 = new Song("Song C", "Artist 3", 240);
        Song s4 = new Song("Song D", "Artist 4", 200);
        Song s5 = new Song("Song E", "Artist 5", 230);

        Playlist playlist = new Playlist("My Playlist");
        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);
        playlist.addSong(s4);
        playlist.addSong(s5);

        MusicPlayer player = new MusicPlayer();
        player.setPlaylist(playlist);
        player.setRepeatMode(RepeatMode.ALL);
        player.play();

        player.next();
        player.next();
        player.toggleShuffle();
        player.next();
        player.toggleShuffle();
        player.next();
        player.setRepeatMode(RepeatMode.ONE);
        player.next();
        player.stop();
    }
}

class Song {
    private final String title;
    private final String artist;
    private final int durationSeconds;

    public Song(String title, String artist, int durationSeconds) {
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getDurationSeconds() { return durationSeconds; }

    @Override
    public String toString() {
        return String.format("%s - %s (%d:%02d)", artist, title,
                durationSeconds / 60, durationSeconds % 60);
    }
}

class Playlist {
    private final String name;
    private final List<Song> songs = new ArrayList<>();

    public Playlist(String name) {
        this.name = name;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public List<Song> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    public String getName() {
        return name;
    }
}

enum RepeatMode {
    NONE,
    ONE,
    ALL
}

class MusicPlayer {
    private Playlist playlist;
    private List<Song> playQueue = new ArrayList<>();
    private int currentIndex = -1;
    private boolean shuffle = false;
    private RepeatMode repeatMode = RepeatMode.NONE;
    private boolean playing = false;

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
        resetQueue();
    }

    public void setRepeatMode(RepeatMode mode) {
        this.repeatMode = mode;
        System.out.println("Repeat mode set to: " + mode);
    }

    public void toggleShuffle() {
        this.shuffle = !this.shuffle;
        System.out.println("Shuffle " + (shuffle ? "enabled" : "disabled"));
        resetQueue();
    }

    private void resetQueue() {
        if (playlist == null) {
            playQueue.clear();
            currentIndex = -1;
            return;
        }
        playQueue = new ArrayList<>(playlist