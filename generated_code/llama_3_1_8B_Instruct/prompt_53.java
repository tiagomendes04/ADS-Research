```java
import java.util.*;

class Card {
    private String name;
    private int value;

    public Card(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}

class Player {
    private String name;
    private int score;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void drawCard(Card card) {
        this.hand.add(card);
    }

    public Card playCard(int index) {
        if (index >= 0 && index < hand.size()) {
            Card card = hand.get(index);
            hand.remove(index);
            return card;
        }
        return null;
    }

    public void discardCard(Card card) {
        if (hand.contains(card)) {
            hand.remove(card);
        }
    }
}

class Game {
    private List<Player> players;
    private List<Card> deck;
    private int currentPlayerIndex;
    private int round;

    public Game(List<Player> players, List<Card> deck) {
        this.players = players;
        this.deck = deck;
        this.currentPlayerIndex = 0;
        this.round = 1;
    }

    public void startGame() {
        while (true) {
            Player currentPlayer = players.get(currentPlayerIndex);
            System.out.println("Round " + round);
            System.out.println("Current Player: " + currentPlayer.getName());
            System.out.println("Current Score: " + currentPlayer.getScore());
            System.out.println("Your hand: " + currentPlayer.hand);

            System.out.println("Choose an action:");
            System.out.println("1. Draw a card");
            System.out.println("2. Play a card");
            System.out.println("3. Discard a card");
            System.out.println("4. End turn");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    drawCard(currentPlayer);
                    break;
                case 2:
                    playCard(currentPlayer);
                    break;
                case 3:
                    discardCard(currentPlayer);
                    break;
                case 4:
                    endTurn(currentPlayer);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            round++;
            if (round > 10) {
                break;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }

        System.out.println("Game over!");
        System.out.println("Final scores:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getScore());
        }
    }

    private void drawCard(Player player) {
        if (deck.isEmpty()) {
            System.out.println("No cards left in the deck. Drawing a card from another player's hand.");
            for (Player p : players) {
                if (p != player) {
                    Card card = p.hand.remove((int) (Math.random() * p.hand.size()));
                    player.hand.add(card);
                    System.out.println("Card drawn from " + p.getName() + "'s hand.");
                    break;
                }
            }
        } else {
            Card card = deck.remove(0);
            player.drawCard(card);
            System.out.println("Card drawn from the deck.");
        }
    }

    private void playCard(Player player) {
        System.out.println("Which card would you like to play?");
        for (int i = 0; i < player.hand.size(); i++) {
            System.out.println((i + 1) + ". " + player.hand.get(i).getName());
        }
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline left-over

        Card card = player.playCard(index);
        if (card != null) {
            System.out.println("Card played: " + card.getName());
            // Update scores based on the card played
            // For example:
            if (card.getValue() == 10) {
                player.addScore(10);
            }
        } else {
            System.out.println("Invalid card index.");
        }
    }

    private void discardCard(Player player) {
        System.out.println("Which card would you like to discard?");
        for (int i = 0; i < player.hand.size(); i++) {
            System.out.println((i + 1) + ". " + player.hand.get(i).getName());
        }
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline left-over

        Card card = player.hand.get(index);
        player.discardCard(card);
        System.out.println("Card discarded: " + card.getName());
    }

    private void endTurn(Player player) {
        System.out.println("End of turn. Next player's turn.");
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}

public class Main {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));

        List<Card> deck = new ArrayList<>();
        deck.add(new Card("Card 1", 10));
        deck.add(new Card("Card 2", 20));
        deck.add(new Card("Card 3", 30));
        deck.add(new Card("Card 4", 40));
        deck.add(new Card("Card 5", 50));

        Game game = new Game(players, deck);
        game.startGame();
    }
}
```