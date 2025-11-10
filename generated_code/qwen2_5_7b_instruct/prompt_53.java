```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Card {
    String suit;
    int value;

    public Card(String suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}

class Player {
    String name;
    List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void drawCard(Card card) {
        hand.add(card);
    }

    public Card playCard() {
        if (hand.isEmpty()) {
            throw new IllegalStateException("Player has no cards to play.");
        }
        return hand.remove(hand.size() - 1);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return hand.stream().mapToInt(card -> card.value).sum();
    }
}

class Game {
    private List<Player> players;
    private int currentPlayerIndex;

    public Game(List<String> playerNames) {
        this.players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }
        this.currentPlayerIndex = 0;
    }

    public void addCardToAllHands(Card card) {
        for (Player player : players) {
            player.drawCard(card);
        }
    }

    public void playTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println(currentPlayer.getName() + " is playing.");
        Card playedCard = currentPlayer.playCard();
        System.out.println(currentPlayer.getName() + " played: " + playedCard);
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void showScores() {
        System.out.println("Current scores:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getScore());
        }
    }

    public void startGame() {
        int cardsPerPlayer = 5;
        for (int i = 0; i < cardsPerPlayer; i++) {
            Card card = new Card("Hearts", i + 1);
            addCardToAllHands(card);
        }
        while (true) {
            playTurn();
            showScores();
            if (players.stream().allMatch(Player::handIsEmpty)) {
                break;
            }
        }
        System.out.println("Game over!");
    }

    private boolean allPlayersHaveSameScore() {
        int firstPlayerScore = players.get(0).getScore();
        return players.stream().allMatch(player -> player.getScore() == firstPlayerScore);
    }

    private void declareWinner() {
        if (allPlayersHaveSameScore()) {
            System.out.println("It's a tie!");
        } else {
            int minScore = Collections.min(players.stream().mapToInt(player -> player.getScore()).toList());
            List<Player> winners = players.stream().filter(player -> player.getScore() == minScore).toList();
            System.out.println("Winner(s): " + String.join(", ", winners.stream().map(Player::getName).toList()) + " with score: " + minScore);
        }
    }

    private boolean allPlayersHandIsEmpty() {
        return players.stream().allMatch(Player::handIsEmpty);
    }

    private boolean handIsEmpty() {
        return players.get(currentPlayerIndex).hand.isEmpty();
    }
}

class PlayerUtil {
    public static List<String> getPlayerNames() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter player names (separated by space):");
        String input = scanner.nextLine();
        String[] names = input.split("\\s+");
        return new ArrayList<>(List.of(names));
    }
}

public class MultiplayerCardGame {
    public static void main(String[] args) {
        List<String> playerNames = PlayerUtil.getPlayerNames();
        Game game = new Game(playerNames);
        game.startGame();
        game.declareWinner();
    }
}
```