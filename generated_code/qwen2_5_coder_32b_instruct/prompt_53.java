```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Card {
    private String suit;
    private String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        }
        return null;
    }
}

class Player {
    private String name;
    private List<Card> hand;
    private int score;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.score = 0;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public Card playCard(int index) {
        if (index >= 0 && index < hand.size()) {
            return hand.remove(index);
        }
        return null;
    }

    public void incrementScore() {
        score++;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public List<Card> getHand() {
        return hand;
    }
}

class Game {
    private List<Player> players;
    private Deck deck;
    private int rounds;

    public Game(List<Player> players, int rounds) {
        this.players = players;
        this.deck = new Deck();
        this.rounds = rounds;
    }

    public void startGame() {
        deck.shuffle();
        dealCards();

        for (int round = 0; round < rounds; round++) {
            System.out.println("Round " + (round + 1));
            playRound();
        }

        announceWinner();
    }

    private void dealCards() {
        for (int i = 0; i < 5; i++) { // Assuming 5 cards per player
            for (Player player : players) {
                player.addCardToHand(deck.drawCard());
            }
        }
    }

    private void playRound() {
        List<Card> playedCards = new ArrayList<>();
        for (Player player : players) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(player.getName() + "'s hand: " + player.getHand());
            System.out.print(player.getName() + ", enter the index of the card you want to play: ");
            int index = scanner.nextInt();
            Card card = player.playCard(index);
            if (card != null) {
                playedCards.add(card);
                System.out.println(player.getName() + " played: " + card);
            }
        }

        determineRoundWinner(playedCards);
    }

    private void determineRoundWinner(List<Card> playedCards) {
        Card winningCard = playedCards.get(0);
        Player winner = players.get(0);

        for (int i = 1; i < playedCards.size(); i++) {
            Card currentCard = playedCards.get(i);
            if (getCardValue(currentCard) > getCardValue(winningCard)) {
                winningCard = currentCard;
                winner = players.get(i);
            }
        }

        winner.incrementScore();
        System.out.println(winner.getName() + " wins the round with " + winningCard);
    }

    private int getCardValue(Card card) {
        String rank = card.getRank();
        switch (rank) {
            case "2": return 2;
            case "3": return 3;
            case "4": return 4;
            case "5": return 5;
            case "6": return 6;
            case "7": return 7;
            case "8": return 8;
            case "9": return 9;
            case "10": return 10;
            case "Jack": return 11;
            case "Queen": return 12;
            case "King": return 13;
            case "Ace": return 14;
            default: return 0;
        }
    }

    private void announceWinner() {
        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        System.out.println(winner.getName() + " wins the game with a score of " + winner.getScore());
    }
}

public class MultiplayerCardGame {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Alice"));
        players.add(new Player("Bob"));
        players.add(new Player("Charlie"));

        Game game = new Game(players, 3); // 3 rounds for demonstration
        game.startGame();
    }
}
```