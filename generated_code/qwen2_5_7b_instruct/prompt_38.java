```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MusicPlayer {

    private ArrayList<String> playlist;
    private String currentSong;
    private boolean shuffleMode;
    private boolean repeatMode;

    public MusicPlayer() {
        playlist = new ArrayList<>();
        currentSong = "";
        shuffleMode = false;
        repeatMode = false;
    }

    public void addSong(String song) {
        playlist.add(song);
    }

    public void playNextSong() {
        if (playlist.isEmpty()) {
            System.out.println("No songs in the playlist.");
            return;
        }

        if (shuffleMode) {
            Collections.shuffle(playlist);
        }

        if (repeatMode && currentSong.equals("")) {
            currentSong = playlist.get(0);
        } else if (currentSong.equals("")) {
            currentSong = playlist.get(0);
        } else {
            int index = playlist.indexOf(currentSong);
            if (index + 1 < playlist.size()) {
                currentSong = playlist.get(index + 1);
            } else {
                if (repeatMode) {
                    currentSong = playlist.get(0);
                } else {
                    currentSong = "";
                }
            }
        }
        System.out.println("Now playing: " + currentSong);
    }

    public void setShuffleMode(boolean shuffleMode) {
        this.shuffleMode = shuffleMode;
    }

    public void setRepeatMode(boolean repeatMode) {
        this.repeatMode = repeatMode;
    }

    public static void main(String[] args) {
        MusicPlayer mp = new MusicPlayer();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add song\n2. Play next song\n3. Toggle shuffle mode\n4. Toggle repeat mode\n5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter song name: ");
                    mp.addSong(scanner.nextLine());
                    break;
                case 2:
                    mp.playNextSong();
                    break;
                case 3:
                    mp.setShuffleMode(!mp.shuffleMode);
                    System.out.println("Shuffle mode is " + (mp.shuffleMode ? "on" : "off"));
                    break;
                case 4:
                    mp.setRepeatMode(!mp.repeatMode);
                    System.out.println("Repeat mode is " + (mp.repeatMode ? "on" : "off"));
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
```