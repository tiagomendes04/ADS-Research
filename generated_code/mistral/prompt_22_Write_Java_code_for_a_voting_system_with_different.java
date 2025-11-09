```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface Ballot {
    void castVote(String candidate);
    Map<String, Integer> tally();
}

class SimpleBallot implements Ballot {
    private Map<String, Integer> votes;

    public SimpleBallot() {
        votes = new HashMap<>();
    }

    @Override
    public void castVote(String candidate) {
        votes.put(candidate, votes.getOrDefault(candidate, 0) + 1);
    }

    @Override
    public Map<String, Integer> tally() {
        return new HashMap<>(votes);
    }
}

class RankedChoiceBallot implements Ballot {
    private List<List<String>> rankedVotes;

    public RankedChoiceBallot() {
        rankedVotes = new ArrayList<>();
    }

    @Override
    public void castVote(String candidate) {
        List<String> rankedVote = new ArrayList<>();
        rankedVote.add(candidate);
        rankedVotes.add(rankedVote);
    }

    @Override
    public Map<String, Integer> tally() {
        Map<String, Integer> results = new HashMap<>();
        for (List<String> vote : rankedVotes) {
            if (!vote.isEmpty()) {
                String candidate = vote.get(0);
                results.put(candidate, results.getOrDefault(candidate, 0) + 1);
            }
        }
        return results;
    }
}

class VotingSystem {
    private Ballot ballot;

    public VotingSystem(Ballot ballot) {
        this.ballot = ballot;
    }

    public void castVote(String candidate) {
        ballot.castVote(candidate);
    }

    public Map<String, Integer> tally() {
        return ballot.tally();
    }
}

public class Main {
    public static void main(String[] args) {
        Ballot simpleBallot = new SimpleBallot();
        VotingSystem simpleVotingSystem = new VotingSystem(simpleBallot);

        simpleVotingSystem.castVote("Alice");
        simpleVotingSystem.castVote("Bob");
        simpleVotingSystem.castVote("Alice");

        Map<String, Integer> simpleResults = simpleVotingSystem.tally();
        System.out.println("Simple Ballot Results: " + simpleResults);

        Ballot rankedBallot = new RankedChoiceBallot();
        VotingSystem rankedVotingSystem = new VotingSystem(rankedBallot);

        rankedVotingSystem.castVote("Charlie");
        rankedVotingSystem.castVote("Alice");
        rankedVotingSystem.castVote("Bob");

        Map<String, Integer> rankedResults = rankedVotingSystem.tally();
        System.out.println("Ranked Choice Ballot Results: " + rankedResults);
    }
}
```