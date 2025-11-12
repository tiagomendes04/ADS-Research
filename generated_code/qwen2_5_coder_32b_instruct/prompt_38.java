```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Override
    public String toString() {
        return title + " by " + artist;
    }
}

class Playlist {
    private List<Song> songs;
    private boolean shuffle;
    private boolean repeat;
    private int currentIndex;

    public Playlist() {
        songs = new ArrayList<>();
        shuffle = false;
        repeat = false;
        currentIndex = 0;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
        if (shuffle) {
            Collections.shuffle(songs);
        }
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public Song playNext() {
        if (songs.isEmpty()) {
            return null;
        }
        Song currentSong = songs.get(currentIndex);
        currentIndex++;
        if (currentIndex >= songs.size()) {
            if (repeat) {
                currentIndex = 0;
                if (shuffle) {
                    Collections.shuffle(songs);
                }
            } else {
                currentIndex = songs.size() - 1;
            }
        }
        return currentSong;
    }

    public Song playPrevious() {
        if (songs.isEmpty()) {
            return null;
        }
        currentIndex--;
        if (currentIndex < 0) {
            if (repeat) {
                currentIndex = songs.size() - 1;
                if (shuffle) {
                    Collections.shuffle(songs);
                }
            } else {
                currentIndex = 0;
            }
        }
        return songs.get(currentIndex);
    }

    public Song getCurrentSong() {
        if (songs.isEmpty()) {
            return null;
        }
        return songs.get(currentIndex);
    }
}

public class MusicPlayer {
    private Playlist playlist;

    public MusicPlayer() {
        playlist = new Playlist();
    }

    public void addSongToPlaylist(Song song) {
        playlist.addSong(song);
    }

    public void removeSongFromPlaylist(Song song) {
        playlist.removeSong(song);
    }

    public void toggleShuffle() {
        playlist.setShuffle(!playlist.isShuffle());
    }

    public void toggleRepeat() {
        playlist.setRepeat(!playlist.isRepeat());
    }

    public Song playNext() {
        return playlist.playNext();
    }

    public Song playPrevious() {
        return playlist.playPrevious();
    }

    public Song getCurrentSong() {
        return playlist.getCurrentSong();
    }

    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();
        player.addSongToPlaylist(new Song("Imagine", "John Lennon"));
        player.addSongToPlaylist(new Song("Bohemian Rhapsody", "Queen"));
        player.addSongToPlaylist(new Song("Stairway to Heaven", "Led Zeppelin"));
        
        System.out.println("Current Song: " + player.getCurrentSong());
        System.out.println("Playing Next: " + player.playNext());
        System.out.println("Playing Next: " + player.playNext());
        System.out.println("Playing Previous: " + player.playPrevious());
        
        player.toggleShuffle();
        System.out.println("Shuffled Playing Next: " + player.playNext());
        
        player.toggleRepeat();
        System.out.println("Repeat Playing Next: " + player.playNext());
        System.out.println("Repeat Playing Next: " + player.playNext());
    }
}
```