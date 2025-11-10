```java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Card {
    private String name;
    private int value;
    private int cost;
    private int manaCost;
    private int attack;
    private int defense;

    public Card(String name, int value, int cost, int manaCost, int attack, int defense) {
        this.name = name;
        this.value = value;
        this.cost = cost;
        this.manaCost = manaCost;
        this.attack = attack;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getCost() {
        return cost;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }
}

class Player {
    private String name;
    private List<Card> deck;
    private List<Card> hand;
    private int score;
    private AtomicInteger turn;

    public Player(String name) {
        this.name = name;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.score = 0;
        this.turn = new AtomicInteger(0);
    }

    public void addCard(Card card) {
        deck.add(card);
        if (card.getCost() <= 0) {
            hand.add(card);
        } else if (card.getManaCost() <= 0) {
            turn.incrementAndGet();
            score += card.getValue();
        }
    }

    public void removeCard(Card card) {
        deck.remove(card);
        if (card.getCost() <= 0) {
            hand.remove(card);
        } else if (card.getManaCost() <= 0) {
            turn.decrementAndGet();
        }
    }

    public String getName() {
        return name;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getScore() {
        return score;
    }

    public AtomicInteger getTurn() {
        return turn;
    }
}

class Game {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Random random;

    public Game() {
        this.player1 = new Player("Player 1");
        this.player2 = new Player("Player 2");
        this.random = new Random();
    }

    public void startGame() {
        while (true) {
            player1.removeCard(new Card("Card 1", 5, 1, 0, 0, 0));
            player1.removeCard(new Card("Card 2", 3, 2, 0, 0, 0));
            player2.removeCard(new Card("Card 3", 2, 3, 0, 0, 0));
            player2.removeCard(new Card("Card 4", 1, 4, 0, 0, 0));
            player1.addCard(new Card("Card 5", 4, 1, 0, 0, 0));
            player2.addCard(new Card("Card 6", 6, 2, 0, 0, 0));
            currentPlayer = player1;
            System.out.println("Current player: " + currentPlayer.getName());
            if (random.nextBoolean()) {
                System.out.println("Player 1 wins!");
                break;
            } else {
                System.out.println("Player 2 wins!");
                break;
            }
        }
    }

    public void playGame() {
        while (true) {
            System.out.println("Current player: " + currentPlayer.getName());
            System.out.println("Score: " + currentPlayer.getScore());
            int player1Turn = currentPlayer.getTurn().get();
            int player2Turn = currentPlayer.getTurn().get();
            int currentPlayerAttack = currentPlayer.getAttack();
            int currentPlayerDefense = currentPlayer.getDefense();
            System.out.println("Player 1's turn: " + currentPlayerTurn + " - " + player1Attack + " " + player1Defense);
            System.out.println("Player 2's turn: " + player2Turn + " - " + player2Attack + " " + player2Defense);
            if (player1Turn == 0) {
                player1.addCard(new Card("Card 7", 8, 3, 0, 0, 0));
                player1.addCard(new Card("Card 8", 9, 2, 0, 0, 0));
                currentPlayerTurn = 1;
            } else if (player2Turn == 0) {
                player2.addCard(new Card("Card 9", 10, 1, 0, 0, 0));
                player2.addCard(new Card("Card 10", 11, 3, 0, 0, 0));
                currentPlayerTurn = 2;
            }
            if (player1Turn == 0) {
                System.out.println("Player 1's turn: " + currentPlayerTurn + " - " + player1Attack + " " + player1Defense);
                if (player1Attack + player1Defense > player2Defense) {
                    System.out.println("Player 1 wins the turn!");
                    currentPlayerTurn = 1;
                    currentPlayer.attack = 10;
                    currentPlayer.defense = 10;
                } else if (player1Attack + player1Defense <= player2Defense) {
                    System.out.println("Player 2 wins the turn!");
                    currentPlayerTurn = 2;
                    currentPlayer.attack = 10;
                    currentPlayer.defense = 10;
                } else {
                    System.out.println("It's a draw!");
                    currentPlayerTurn = 3;
                    currentPlayer.attack = 10;
                    currentPlayer.defense = 10;
                }
            } else if (player2Turn == 0) {
                System.out.println("Player 2's turn: " + currentPlayerTurn + " - " + player2Attack + " " + player2Defense);
                if (player2Attack + player2Defense > player1Defense) {
                    System.out.println("Player 2 wins the turn!");
                    currentPlayerTurn = 1;
                    currentPlayer.attack = 10;
                    currentPlayer.defense = 10;
                } else if (player2Attack + player2Defense <= player1Defense) {
                    System.out.println("Player 1 wins the turn!");
                    currentPlayerTurn = 2;
                    currentPlayer.attack = 10;
                    currentPlayer.defense = 10;
                } else {
                    System.out.println("It's a draw!");
                    currentPlayerTurn = 3;
                    currentPlayer.attack = 10;
                    currentPlayer.defense = 10;
                }
            }
            if (currentPlayerTurn == 0) {
                System.out.println("Player 1 wins the game!");
                break;
            } else if (currentPlayerTurn == 1) {
                System.out.println("Player 2 wins the game!");
                break;
            } else if (currentPlayerTurn == 2) {
                System.out.println("It's a draw!");
                break;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }
}
```