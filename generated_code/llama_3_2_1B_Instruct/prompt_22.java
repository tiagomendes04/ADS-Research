```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class BallotType {
    String name;

    public BallotType(String name) {
        this.name = name;
    }
}

class Vote {
    int count;

    public Vote(int count) {
        this.count = count;
    }
}

class VotingSystem {
    Map<BallotType, Map<Integer, Vote>> ballots;

    public VotingSystem() {
        ballots = new HashMap<>();
    }

    public void addBallot(String name, int[] values) {
        if (ballots.containsKey(name)) {
            ballots.get(name).put(0, new Vote(values[0]));
            ballots.get(name).put(1, new Vote(values[1]));
        } else {
            ballots.put(name, new HashMap<>());
            ballots.get(name).put(0, new Vote(values[0]));
            ballots.get(name).put(1, new Vote(values[1]));
        }
    }

    public void tally() {
        for (Map<Integer, Vote> votes : ballots.values()) {
            votes.forEach((_, vote) -> vote.count += 1);
        }
    }

    public void printResults() {
        for (BallotType ballot : ballots.keySet()) {
            System.out.println(ballot.name + ": " + ballots.get(ballot).keySet().stream().mapToObj(x -> x + 1).collect(Collectors.joining(", ")));
        }
    }
}

public class Main {
    public static void main(String[] args) {
        VotingSystem votingSystem = new VotingSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add ballot");
            System.out.println("2. Tally votes");
            System.out.println("3. Print results");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    System.out.print("Enter ballot name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter ballot values (separated by space): ");
                    int[] values = new int[2];
                    for (int i = 0; i < 2; i++) {
                        values[i] = scanner.nextInt();
                        scanner.nextLine(); // Consume newline left-over
                    }
                    votingSystem.addBallot(name, values);
                    break;
                case 2:
                    votingSystem.tally();
                    break;
                case 3:
                    votingSystem.printResults();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
```