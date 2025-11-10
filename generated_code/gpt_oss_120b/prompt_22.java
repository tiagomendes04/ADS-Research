```java
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

// Base ballot interface
interface Ballot {
    String getVoterId();
    void validate() throws IllegalArgumentException;
}

// Single‑choice ballot implementation
class SingleChoiceBallot implements Ballot {
    private final String voterId;
    private final String candidateId;

    public SingleChoiceBallot(String voterId, String candidateId) {
        this.voterId = voterId;
        this.candidateId = candidateId;
    }

    @Override
    public String getVoterId() {
        return voterId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    @Override
    public void validate() {
        if (voterId == null || voterId.isEmpty())
            throw new IllegalArgumentException("Voter ID missing");
        if (candidateId == null || candidateId.isEmpty())
            throw new IllegalArgumentException("Candidate ID missing");
    }
}

// Ranked‑choice ballot implementation
class RankedChoiceBallot implements Ballot {
    private final String voterId;
    private final List<String> rankedCandidateIds; // highest preference first

    public RankedChoiceBallot(String voterId, List<String> rankedCandidateIds) {
        this.voterId = voterId;
        this.rankedCandidateIds = new ArrayList<>(rankedCandidateIds);
    }

    @Override
    public String getVoterId() {
        return voterId;
    }

    public List<String> getRankedCandidateIds() {
        return Collections.unmodifiableList(rankedCandidateIds);
    }

    @Override
    public void validate() {
        if (voterId == null || voterId.isEmpty())
            throw new IllegalArgumentException("Voter ID missing");
        if (rankedCandidateIds == null || rankedCandidateIds.isEmpty())
            throw new IllegalArgumentException("At least one ranked candidate required");
        Set<String> uniq = new HashSet<>(rankedCandidateIds);
        if (uniq.size() != rankedCandidateIds.size())
            throw new IllegalArgumentException("Duplicate candidates in ranking");
    }
}

// Tally result holder
class TallyResult {
    private final Map<String, Integer> singleChoiceCounts = new HashMap<>();
    private final Map<String, Integer> rankedChoiceCounts = new HashMap<>();

    public void addSingleChoice(String candidateId) {
        singleChoiceCounts.merge(candidateId, 1, Integer::sum);
    }

    public void addRankedChoice(String candidateId) {
        rankedChoiceCounts.merge(candidateId, 1, Integer::sum);
    }

    public Map<String, Integer> getSingleChoiceCounts() {
        return Collections.unmodifiableMap(singleChoiceCounts);
    }

    public Map<String, Integer> getRankedChoiceCounts() {
        return Collections.unmodifiableMap(rankedChoiceCounts);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Single‑Choice Results ===\n");
        singleChoiceCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(e -> sb.append(e.getKey()).append(": ").append(e.getValue()).append('\n'));

        sb.append("\n=== Ranked‑Choice First‑Preference Results ===\n");
        rankedChoiceCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(e -> sb.append(e.getKey()).append(": ").append(e.getValue()).append('\n'));

        return sb.toString();
    }
}

// Core voting system
class VotingSystem {
    private final Set<String> allowedCandidates;
    private final Set<String> votedVoterIds = new HashSet<>();
    private final List<Ballot> ballots = new ArrayList<>();

    public VotingSystem(Set<String> allowedCandidates) {
        this.allowedCandidates = new HashSet<>(allowedCandidates);
    }

    // Submit a ballot (single‑choice or ranked‑choice)
    public synchronized void submitBallot(Ballot ballot) {
        ballot.validate();

        if (!votedVoterIds.add(ballot.getVoterId())) {
            throw new IllegalArgumentException("Voter has already voted: " + ballot.getVoterId());
        }

        if (ballot instanceof SingleChoiceBallot) {
            SingleChoiceBallot sc = (SingleChoiceBallot) ballot;
            if (!allowedCandidates.contains(sc.getCandidateId())) {
                throw new IllegalArgumentException("Invalid candidate: " + sc.getCandidateId());
            }
        } else if (ballot instanceof RankedChoiceBallot) {
            RankedChoiceBallot rc = (RankedChoiceBallot) ballot;
            for (String cid : rc.getRankedCandidateIds()) {
                if (!allowedCandidates.contains(cid)) {
                    throw new IllegalArgumentException("Invalid candidate in ranking: " + cid);
                }
            }
        }

        ballots.add(ballot);
    }

    // Perform tallying
    public TallyResult tally() {
        TallyResult result = new TallyResult();

        for (Ballot ballot : ballots) {
            if (ballot instanceof SingleChoiceBallot) {
                SingleChoiceBallot sc = (SingleChoiceBallot) ballot;
                result.addSingleChoice(sc.getCandidateId());
            } else if (ballot instanceof RankedChoiceBallot) {
                RankedChoiceBallot rc = (RankedChoiceBallot) ballot;
                // Only first‑preference counts are shown; more complex instant‑runoff logic can be added.
                if (!rc.getRankedCandidateIds().isEmpty()) {
                    result.addRankedChoice(rc.getRankedCandidateIds().get(0));
                }
            }
        }

        return result;
    }

    // Utility: list all received ballots (for debugging)
    public List<Ballot> getAllBallots() {
        return Collections.unmodifiableList(new ArrayList<>(ballots));
    }
}

// Demo / test harness
public class VotingDemo {
    public static void main(String[] args) {
        Set<String> candidates = Set.of("Alice", "Bob", "Charlie", "Diana");
        VotingSystem system = new VotingSystem(candidates);

        // Sample single‑choice votes
        system.submitBallot(new SingleChoiceBallot("V001", "Alice"));
        system.submitBallot(new SingleChoiceBallot("V002", "Bob"));
        system.submitBallot(new SingleChoiceBallot("V003", "Alice"));
        system.submitBallot(new SingleChoiceBallot("V004", "Diana"));

        // Sample ranked‑choice votes
        system.submitBallot(new RankedChoiceBallot("V005",
                List.of("Charlie", "Alice", "Bob")));
        system.submitBallot(new RankedChoiceBallot("V006",
                List.of("Bob", "Diana")));
        system.submitBallot(new RankedChoiceBallot("V007",
                List.of("Alice")));

        // Tally and display results
        TallyResult result = system.tally();
        System.out.println(result);
    }
}
```