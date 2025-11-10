```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Card {
    private final String suit;
    private final int value;

    public Card(String suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}

class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        for (String suit : suits) {
            for (int i = 1; i <= 13; i++) {
                cards.add(new Card(suit, i));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        return cards.isEmpty() ? null : cards.remove(cards.size() - 1);
    }
}

class Player {
    private final String name;
    private final List<Card> hand;
    private int score;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
        score = 0;
    }

    public void addCardToHand(Card card) {
        if (card != null) {
            hand.add(card);
        }
    }

    public Card playCard(int index) {
        return index >= 0 && index < hand.size() ? hand.remove(index) : null;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ": " + hand.toString();
    }
}

class Game {
    private final List<Player> players;
    private final Deck deck;
    private int currentPlayerIndex;

    public Game(List<String> playerNames) {
        players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }
        deck = new Deck();
        deck.shuffle();
        currentPlayerIndex = 0;
    }

    public void dealCards() {
        while (!deck.cards.isEmpty()) {
            for (Player player : players) {
                player.addCardToHand(deck.dealCard());
            }
        }
    }

    public void playTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        Scanner scanner = new Scanner(System.in);
        System.out.println(currentPlayer.getName() + ", it's your turn.");
        System.out.println("Your hand: " + currentPlayer);
        System.out.print("Enter the index of the card you want to play (0-" + (currentPlayer.hand.size() - 1) + "): ");
        int index = scanner.nextInt();
        Card playedCard = currentPlayer.playCard(index);
        if (playedCard != null) {
            System.out.println(currentPlayer.getName() + " played: " + playedCard);
            updateScores(playedCard);
        } else {
            System.out.println("Invalid card index. Skipping turn.");
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    private void updateScores(Card playedCard) {
        for (Player player : players) {
            player.setScore(player.getScore() + playedCard.getValue());
        }
    }

    public void printScores() {
        System.out.println("Current scores:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getScore());
        }
    }

    public boolean isGameOver() {
        return players.stream().allMatch(player -> player.hand.isEmpty());
    }

    public void announceWinner() {
        Player winner = players.stream().max((p1, p2) -> Integer.compare(p1.getScore(), p2.getScore())).orElse(null);
        if (winner != null) {
            System.out.println("The winner is: " + winner.getName() + " with a score of " + winner.getScore());
        } else {
            System.out.println("No clear winner.");
        }
    }
}

public class MultiplayerCardGame {
    public static void main(String[] args) {
        List<String> playerNames = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for player " + (i + 1) + ": ");
            playerNames.add(scanner.nextLine());
        }

        Game game = new Game(playerNames);
        game.dealCards();

        while (!game.isGameOver()) {
            game.playTurn();
            game.printScores();
        }