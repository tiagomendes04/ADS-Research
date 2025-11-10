```java
import java.util.HashMap;
import java.util.Map;

abstract class Ballot {
    abstract void castVote(String candidate);
}

class PluralityBallot extends Ballot {
    private Map<String, Integer> votes;

    public PluralityBallot() {
        votes = new HashMap<>();
    }

    @Override
    void castVote(String candidate) {
        votes.put(candidate, votes.getOrDefault(candidate, 0) + 1);
    }

    public Map<String, Integer> getVotes() {
        return votes;
    }
}

class ApprovalBallot extends Ballot {
    private Map<String, Integer> approvals;

    public ApprovalBallot() {
        approvals = new HashMap<>();
    }

    @Override
    void castVote(String candidate) {
        approvals.put(candidate, approvals.getOrDefault(candidate, 0) + 1);
    }

    public Map<String, Integer> getApprovals() {
        return approvals;
    }
}

class VotingSystem {
    private Map<String, Ballot> ballots;

    public VotingSystem() {
        ballots = new HashMap<>();
    }

    public void addBallot(String ballotType, Ballot ballot) {
        ballots.put(ballotType, ballot);
    }

    public void castVote(String ballotType, String candidate) {
        if (ballots.containsKey(ballotType)) {
            ballots.get(ballotType).castVote(candidate);
        } else {
            System.out.println("Ballot type not found.");
        }
    }

    public Map<String, Integer> getResults(String ballotType) {
        if (ballots.containsKey(ballotType)) {
            Ballot ballot = ballots.get(ballotType);
            if (ballot instanceof PluralityBallot) {
                return ((PluralityBallot) ballot).getVotes();
            } else if (ballot instanceof ApprovalBallot) {
                return ((ApprovalBallot) ballot).getApprovals();
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        VotingSystem system = new VotingSystem();
        system.addBallot("Plurality", new PluralityBallot());
        system.addBallot("Approval", new ApprovalBallot());

        system.castVote("Plurality", "Alice");
        system.castVote("Plurality", "Bob");
        system.castVote("Plurality", "Alice");

        system.castVote("Approval", "Alice");
        system.castVote("Approval", "Bob");
        system.castVote("Approval", "Charlie");
        system.castVote("Approval", "Alice");
        system.castVote("Approval", "Bob");

        System.out.println("Plurality Results: " + system.getResults("Plurality"));
        System.out.println("Approval Results: " + system.getResults("Approval"));
    }
}
```