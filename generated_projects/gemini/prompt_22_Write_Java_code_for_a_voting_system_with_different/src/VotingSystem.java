import java.util.*;

public class VotingSystem {

    public interface Ballot {
        String getType();
        Map<String, Integer> getVotes();
    }

    public static class SimpleBallot implements Ballot {
        private final Map<String, Integer> votes;

        public SimpleBallot(String candidate) {
            this.votes = new HashMap<>();
            this.votes.put(candidate, 1);
        }

        @Override
        public String getType() {
            return "Simple";
        }

        @Override
        public Map<String, Integer> getVotes() {
            return this.votes;
        }

        @Override
        public String toString() {
            return "SimpleBallot{" +
                    "votes=" + votes +
                    '}';
        }
    }

    public static class RankedChoiceBallot implements Ballot {
        private final List<String> rankedChoices;

        public RankedChoiceBallot(List<String> rankedChoices) {
            this.rankedChoices = new ArrayList<>(rankedChoices);
        }

        @Override
        public String getType() {
            return "RankedChoice";
        }

        @Override
        public Map<String, Integer> getVotes() {
            Map<String, Integer> votes = new HashMap<>();
        for (int i = 0; i < rankedChoices.size(); i++) {
            votes.put(rankedChoices.get(i), rankedChoices.size() - i);
        }
        return votes;
        }

        @Override
        public String toString() {
            return "RankedChoiceBallot{" +
                    "rankedChoices=" + rankedChoices +
                    '}';
        }
    }

    public static class ApprovalBallot implements Ballot {
        private final Set<String> approvedCandidates;

        public ApprovalBallot(Set<String> approvedCandidates) {
            this.approvedCandidates = new HashSet<>(approvedCandidates);
        }

        @Override
        public String getType() {
            return "Approval";
        }

        @Override
        public Map<String, Integer> getVotes() {
            Map<String, Integer> votes = new HashMap<>();
            for (String candidate : approvedCandidates) {
                votes.put(candidate, 1);
            }
            return votes;
        }

        @Override
        public String toString() {
            return "ApprovalBallot{" +
                    "approvedCandidates=" + approvedCandidates +
                    '}';
        }
    }

    public static class VotingMachine {
        private final List<Ballot> ballots = new ArrayList<>();

        public void castBallot(Ballot ballot) {
            ballots.add(ballot);
        }

        public Map<String, Integer> tallyVotes() {
            Map<String, Integer> voteCounts = new HashMap<>();

            for (Ballot ballot : ballots) {
                Map<String, Integer> ballotVotes = ballot.getVotes();
                for (Map.Entry<String, Integer> entry : ballotVotes.entrySet()) {
                    String candidate = entry.getKey();
                    int voteValue = entry.getValue();
                    voteCounts.put(candidate, voteCounts.getOrDefault(candidate, 0) + voteValue);
                }
            }

            return voteCounts;
        }

        public void reset() {
            ballots.clear();
        }
    }

    public static void main(String[] args) {
        VotingMachine votingMachine = new VotingMachine();

        // Simple Ballot example
        votingMachine.castBallot(new SimpleBallot("Alice"));
        votingMachine.castBallot(new SimpleBallot("Bob"));
        votingMachine.castBallot(new SimpleBallot("Alice"));

        // Ranked Choice example
        votingMachine.castBallot(new RankedChoiceBallot(Arrays.asList("Charlie", "Bob", "Alice")));
        votingMachine.castBallot(new RankedChoiceBallot(Arrays.asList("Alice", "Charlie", "Bob")));

        // Approval Ballot example
        votingMachine.castBallot(new ApprovalBallot(new HashSet<>(Arrays.asList("Alice", "Bob"))));
        votingMachine.castBallot(new ApprovalBallot(new HashSet<>(Arrays.asList("Charlie"))));

        Map<String, Integer> results = votingMachine.tallyVotes();
        System.out.println("Vote Tally: " + results);

        //Example of using other tally methods
        Map<String, Integer> rankedChoiceResults = tallyRankedChoice(votingMachine.ballots);
        System.out.println("Ranked Choice Tally: " + rankedChoiceResults);

        Map<String, Integer> approvalResults = tallyApproval(votingMachine.ballots);
        System.out.println("Approval Tally: " + approvalResults);

        votingMachine.reset();
        System.out.println("Vote tally after reset: " + votingMachine.tallyVotes());
    }

    public static Map<String, Integer> tallyRankedChoice(List<Ballot> ballots) {
        Map<String, Integer> rankedChoiceTallies = new HashMap<>();

        for (Ballot ballot : ballots) {
            if (ballot instanceof RankedChoiceBallot) {
                Map<String, Integer> votes = ballot.getVotes();
                for (Map.Entry<String, Integer> entry : votes.entrySet()) {
                    String candidate = entry.getKey();
                    int voteValue = entry.getValue();
                    rankedChoiceTallies.put(candidate, rankedChoiceTallies.getOrDefault(candidate, 0) + voteValue);
                }
            }
        }
        return rankedChoiceTallies;
    }

    public static Map<String, Integer> tallyApproval(List<Ballot> ballots) {
        Map<String, Integer> approvalTallies = new HashMap<>();
        for(Ballot ballot : ballots) {
            if(ballot instanceof ApprovalBallot) {
                Map<String, Integer> votes = ballot.getVotes();
                for (Map.Entry<String, Integer> entry : votes.entrySet()) {
                    String candidate = entry.getKey();
                    int voteValue = entry.getValue();
                    approvalTallies.put(candidate, approvalTallies.getOrDefault(candidate, 0) + voteValue);
                }
            }
        }
        return approvalTallies;
    }
}