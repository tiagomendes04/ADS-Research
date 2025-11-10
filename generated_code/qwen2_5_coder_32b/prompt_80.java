```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class VotingApp {
    private Map<String, Map<String, Integer>> ballots;

    public VotingApp() {
        this.ballots = new HashMap<>();
    }

    public void addBallot(String ballotName) {
        ballots.put(ballotName, new HashMap<>());
    }

    public void addOptionToBallot(String ballotName, String option) {
        if (ballots.containsKey(ballotName)) {
            ballots.get(ballotName).put(option, 0);
        }
    }

    public void castVote(String ballotName, String option) {
        if (ballots.containsKey(ballotName) && ballots.get(ballotName).containsKey(option)) {
            ballots.get(ballotName).put(option, ballots.get(ballotName).get(option) + 1);
        }
    }

    public void tallyVotes() {
        for (String ballotName : ballots.keySet()) {
            System.out.println("Results for " + ballotName + ":");
            Map<String, Integer> options = ballots.get(ballotName);
            for (String option : options.keySet()) {
                System.out.println(option + ": " + options.get(option) + " votes");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        VotingApp app = new VotingApp();
        Scanner scanner = new Scanner(System.in);

        app.addBallot("President");
        app.addOptionToBallot("President", "Alice");
        app.addOptionToBallot("President", "Bob");

        app.addBallot("Senator");
        app.addOptionToBallot("Senator", "Charlie");
        app.addOptionToBallot("Senator", "David");

        while (true) {
            System.out.println("Enter ballot name (or 'tally' to see results, 'exit' to quit):");
            String ballotName = scanner.nextLine();
            if (ballotName.equalsIgnoreCase("exit")) {
                break;
            } else if (ballotName.equalsIgnoreCase("tally")) {
                app.tallyVotes();
            } else {
                System.out.println("Enter your vote:");
                String option = scanner.nextLine();
                app.castVote(ballotName, option);
            }
        }
        scanner.close();
    }
}
```