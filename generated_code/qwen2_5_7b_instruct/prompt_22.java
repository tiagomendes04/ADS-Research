```java
import java.util.*;

class Ballot {
    private List<Vote> votes;

    public Ballot(List<Vote> votes) {
        this.votes = votes;
    }

    public List<Vote> getVotes() {
        return votes;
    }
}

class Vote {
    private String candidate;

    public Vote(String candidate) {
        this.candidate = candidate;
    }

    public String getCandidate() {
        return candidate;
    }
}

enum BallotType {
    TYPE_A, TYPE_B, TYPE_C
}

class VotingSystem {
    private Map<BallotType, List<Ballot>> ballotsByType;

    public VotingSystem() {
        this.ballotsByType = new EnumMap<>(BallotType.class);
        for (BallotType type : BallotType.values()) {
            ballotsByType.put(type, new ArrayList<>());
        }
    }

    public void addBallot(BallotType type, Ballot ballot) {
        ballotsByType.get(type).add(ballot);
    }

    public Map<String, Integer> tallyVotes() {
        Map<String, Integer> voteCounts = new HashMap<>();
        for (BallotType type : BallotType.values()) {
            for (Ballot ballot : ballotsByType.get(type)) {
                for (Vote vote : ballot.getVotes()) {
                    voteCounts.put(vote.getCandidate(), voteCounts.getOrDefault(vote.getCandidate(), 0) + 1);
                }
            }
        }
        return voteCounts;
    }
}

public class Main {
    public static void main(String[] args) {
        VotingSystem system = new VotingSystem();

        Ballot ballot1 = new Ballot(Arrays.asList(new Vote("Alice"), new Vote("Bob")));
        Ballot ballot2 = new Ballot(Arrays.asList(new Vote("Alice"), new Vote("Charlie")));
        Ballot ballot3 = new Ballot(Arrays.asList(new Vote("Bob"), new Vote("Charlie")));

        system.addBallot(BallotType.TYPE_A, ballot1);
        system.addBallot(BallotType.TYPE_B, ballot2);
        system.addBallot(BallotType.TYPE_C, ballot3);

        Map<String, Integer> results = system.tallyVotes();
        System.out.println(results);
    }
}
```