```java
import java.util.*;

class Voter {
    String name;
    List<String> choices;

    public Voter(String name, List<String> choices) {
        this.name = name;
        this.choices = choices;
    }
}

class Ballot {
    String type;
    List<String> options;

    public Ballot(String type, List<String> options) {
        this.type = type;
        this.options = options;
    }
}

class Election {
    Map<String, Integer> candidates;
    List<Voter> voters;

    public Election() {
        this.candidates = new HashMap<>();
        this.voters = new ArrayList<>();
    }

    public void addCandidate(String name) {
        candidates.put(name, 0);
    }

    public void addVoter(Voter voter) {
        voters.add(voter);
    }

    public void tallyVotes() {
        for (Voter voter : voters) {
            for (String choice : voter.choices) {
                if (candidates.containsKey(choice)) {
                    candidates.put(choice, candidates.get(choice) + 1);
                }
            }
        }
    }

    public void displayResults() {
        System.out.println("Election Results:");
        for (Map.Entry<String, Integer> entry : candidates.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " votes");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Election election = new Election();

        Ballot presidentialBallot = new Ballot("presidential", Arrays.asList("Candidate A", "Candidate B", "Candidate C"));
        Ballot mayoralBallot = new Ballot("mayoral", Arrays.asList("Mayor A", "Mayor B", "Mayor C"));

        Voter voter1 = new Voter("John Doe", Arrays.asList("Candidate A", "Mayor B"));
        Voter voter2 = new Voter("Jane Doe", Arrays.asList("Candidate B", "Mayor A"));
        Voter voter3 = new Voter("Bob Smith", Arrays.asList("Candidate C", "Mayor C"));

        election.addCandidate("Candidate A");
        election.addCandidate("Candidate B");
        election.addCandidate("Candidate C");
        election.addCandidate("Mayor A");
        election.addCandidate("Mayor B");
        election.addCandidate("Mayor C");

        election.addVoter(voter1);
        election.addVoter(voter2);
        election.addVoter(voter3);

        election.tallyVotes();
        election.displayResults();
    }
}
```