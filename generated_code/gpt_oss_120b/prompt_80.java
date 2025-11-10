```java
import java.util.*;

public class VotingApp {
    public static void main(String[] args) {
        VotingSystem system = new VotingSystem();

        // Ballot 1
        Ballot presidential = new Ballot("Presidential Election");
        presidential.addCandidate("Alice");
        presidential.addCandidate("Bob");
        system.addBallot(presidential);

        // Ballot 2
        Ballot mayoral = new Ballot("Mayor Election");
        mayoral.addCandidate("Carol");
        mayoral.addCandidate("Dave");
        system.addBallot(mayoral);

        // Cast votes
        system.castVote("Presidential Election", "Alice");
        system.castVote("Presidential Election", "Bob");
        system.castVote("Presidential Election", "Alice");
        system.castVote("Mayor Election", "Dave");
        system.castVote("Mayor Election", "Carol");
        system.castVote("Mayor Election", "Dave");

        // Show results
        system.printAllResults();
    }
}

class VotingSystem {
    private final Map<String, Ballot> ballots = new HashMap<>();

    public void addBallot(Ballot ballot) {
        ballots.put(ballot.getName(), ballot);
    }

    public boolean castVote(String ballotName, String candidateName) {
        Ballot ballot = ballots.get(ballotName);
        return ballot != null && ballot.vote(candidateName);
    }

    public void printAllResults() {
        for (Ballot ballot : ballots.values()) {
            System.out.println("Results for \"" + ballot.getName() + "\":");
            ballot.getResults().forEach((candidate, count) ->
                System.out.println("  " + candidate + ": " + count));
            System.out.println();
        }
    }
}

class Ballot {
    private final String name;
    private final Map<String, Integer> votes = new LinkedHashMap<>();

    public Ballot(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCandidate(String candidate) {
        votes.putIfAbsent(candidate, 0);
    }

    public boolean vote(String candidate) {
        Integer current = votes.get(candidate);
        if (current == null) return false;
        votes.put(candidate, current + 1);
        return true;
    }

    public Map<String, Integer> getResults() {
        return Collections.unmodifiableMap(votes);
    }
}
```