```java
import java.util.*;

class Card {
    enum Suit { HEARTS, DIAMONDS, CLUBS, SPADES }
    enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
        EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);
        final int value;
        Rank(int v) { value = v; }
    }

    final Suit suit;
    final Rank rank;

    Card(Suit s, Rank r) {
        suit = s;
        rank = r;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

class Deck {
    private final List<Card> cards = new ArrayList<>();

    Deck() {
        for (Card.Suit s : Card.Suit.values())
            for (Card.Rank r : Card.Rank.values())
                cards.add(new Card(s, r));
        shuffle();
    }

    void shuffle() {
        Collections.shuffle(cards);
    }

    Card draw() {
        if (cards.isEmpty()) return null;
        return cards.remove(cards.size() - 1);
    }

    boolean isEmpty() {
        return cards.isEmpty();
    }
}

class Player {
    final String name;
    final List<Card> hand = new ArrayList<>();
    int score = 0;

    Player(String n) {
        name = n;
    }

    void addCard(Card c) {
        if (c != null) hand.add(c);
    }

    Card playCard() {
        if (hand.isEmpty()) return null;
        return hand.remove(0);
    }

    boolean hasCards() {
        return !hand.isEmpty();
    }

    @Override
    public String toString() {
        return name + " (Score: " + score + ")";
    }
}

class Game {
    private final List<Player> players = new ArrayList<>();
    private final Deck deck = new Deck();
    private int currentPlayerIdx = 0;
    private final int cardsPerPlayer = 5;

    Game(List<String> playerNames) {
        for (String n : playerNames) players.add(new Player(n));
        dealInitialCards();
    }

    private void dealInitialCards() {
        for (int i = 0; i < cardsPerPlayer; i++) {
            for (Player p : players) {
                p.addCard(deck.draw());
            }
        }
    }

    private Player currentPlayer() {
        return players.get(currentPlayerIdx);
    }

    private void nextTurn() {
        currentPlayerIdx = (currentPlayerIdx + 1) % players.size();
    }

    private void evaluateRound(Card c1, Card c2) {
        Player p1 = players.get(0);
        Player p2 = players.get(1);
        int v1 = c1.rank.value;
        int v2 = c2.rank.value;
        if (v1 > v2) p1.score++;
        else if (v2 > v1) p2.score++;
        // tie: no points
    }

    void play() {
        while (players.stream().anyMatch(Player::hasCards)) {
            Player p = currentPlayer();
            Card played = p.playCard();
            System.out.println(p.name + " plays " + played);
            // simple 2-player round evaluation
            if (players.size() == 2) {
                Player opponent = players.get((currentPlayerIdx + 1) % 2);
                Card oppCard = opponent.playCard();
                System.out.println(opponent.name + " plays " + oppCard);
                evaluateRound(played, oppCard);
            }
            nextTurn();
        }
        System.out.println("\n--- Game Over ---");
        players.forEach(p -> System.out.println(p));
        Player winner = players.stream().max(Comparator.comparingInt(pl -> pl.score)).orElse(null);
        if (winner != null) System.out.println("Winner: " + winner.name);
    }
}

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob");
        Game game = new Game(names);
        game.play();
    }
}
```