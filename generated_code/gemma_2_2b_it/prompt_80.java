```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VotingApp {
    static List<Ballot> ballots = new ArrayList<>();
    static int voterCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter 'create' to create a ballot.");
            System.out.println("Enter 'vote' to vote.");
            System.out.println("Enter 'show' to show ballots.");
            System.out.println("Enter 'exit' to exit.");

            String input = scanner.nextLine();
            if (input.equals("create")) {
                createBallot();
            } else if (input.equals("vote")) {
                vote();
            } else if (input.equals("show")) {
                showBallots();
            } else if (input.equals("exit")) {
                System.out.println("Exiting...");
                scanner.close();
                return;
            }
        }
    }

    static void createBallot() {
        System.out.println("Enter the voter ID:");
        int voterId = scanner.nextInt();
        ballots.add(new Ballot(voterId));
        voterCount++;
    }

    static void vote() {
        System.out.println("Enter the voter ID:");
        int voterId = scanner.nextInt();
        
        // TODO: Implement ballot voting logic
        for (Ballot ballot : ballots) {
            if (ballot.voterId == voterId) {
                // TODO: Implement voting logic
                System.out.println("Please enter the option you would like to vote for:");
                int option = scanner.nextInt();
                // TODO: Implement option handling logic
                return;
            }
        }
    }
    
    static void showBallots() {
        if (ballots.isEmpty()) {
            System.out.println("No ballots created yet.");
            return;
        }
        for (Ballot ballot : ballots) {
            System.out.println(ballot);
        }
    }
}

class Ballot {
    int voterId;
    List<Integer> options;

    public Ballot(int voterId) {
        this.voterId = voterId;
        this.options = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Voter ID: " + voterId + "\nOptions: " + options;
    }
}
```