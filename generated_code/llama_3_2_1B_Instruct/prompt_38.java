```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Song {
    String title;
    String artist;
    String album;
    String duration;

    public Song(String title, String artist, String album, String duration) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Artist: " + artist + ", Album: " + album + ", Duration: " + duration;
    }
}

class Playlist {
    List<Song> songs;

    public Playlist() {
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void shuffle() {
        Collections.shuffle(songs);
    }

    public void repeat() {
        for (int i = 0; i < songs.size(); i++) {
            System.out.println(songs.get(i));
        }
    }
}

public class MusicPlayer {
    private List<Song> songs;
    private Playlist playlist;

    public MusicPlayer() {
        this.songs = new ArrayList<>();
        this.playlist = new Playlist();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void play() {
        playlist.shuffle();
        while (true) {
            System.out.println("Press 'q' to quit");
            System.out.print("Enter the song number: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")) {
                break;
            }
            playlist.songs.get(Integer.parseInt(input)).toString();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.addSong(new Song("Song 1", "Artist 1", "Album 1", "2:30"));
        musicPlayer.addSong(new Song("Song 2", "Artist 2", "Album 2", "3:00"));
        musicPlayer.play();
    }
}
```