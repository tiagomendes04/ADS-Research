import java.util.HashMap;
import java.util.Map;

class Candidate {
    String name;
    int votes;

    public Candidate(String name) {
        this.name = name;
        this.votes = 0;
    }
}

class VotingSystem {
    Map<String, Candidate> candidates;
    Map<String, Map<String, Candidate>> ballots;

    public VotingSystem() {
        this.candidates = new HashMap<>();
        this.ballots = new HashMap<>();
    }

    public void addCandidate(String name) {
        candidates.put(name, new Candidate(name));
    }

    public void addBallot(String ballotType, String candidateName) {
        if (!candidates.containsKey(candidateName)) {
            System.out.println("Candidate not found.");
            return;
        }
        if (!ballots.containsKey(ballotType)) {
            ballots.put(ballotType, new HashMap<>());
        }
        ballots.get(ballotType).put(candidateName, candidates.get(candidateName));
        ballots.get(ballotType).get(candidateName).votes++;
    }

    public void tallyVotes() {
        for (Map.Entry<String, Map<String, Candidate>> entry : ballots.entrySet()) {
            System.out.println("Ballot Type: " + entry.getKey());
            int totalVotes = 0;
            for (Candidate candidate : entry.getValue().values()) {
                totalVotes += candidate.votes;
            }
            System.out.println("Total Votes: " + totalVotes);
            for (Candidate candidate : entry.getValue().values()) {
                System.out.println(candidate.name + ": " + candidate.votes + " votes");
            }
            System.out.println();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        VotingSystem votingSystem = new VotingSystem();
        votingSystem.addCandidate("John");
        votingSystem.addCandidate("Jane");
        votingSystem.addBallot("Presidential", "John");
        votingSystem.addBallot("Presidential", "Jane");
        votingSystem.addBallot("Presidential", "John");
        votingSystem.addBallot("Congressional", "Jane");
        votingSystem.addBallot("Congressional", "John");
        votingSystem.tallyVotes();
    }
}