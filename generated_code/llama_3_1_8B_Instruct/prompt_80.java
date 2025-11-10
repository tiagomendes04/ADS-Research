```java
import java.util.*;

class Ballot {
    private String name;
    private int age;
    private Map<String, String> choices;

    public Ballot(String name, int age, Map<String, String> choices) {
        this.name = name;
        this.age = age;
        this.choices = choices;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Map<String, String> getChoices() {
        return choices;
    }
}

public class VotingApp {
    private Map<String, Integer> candidateVotes;
    private Map<String, Integer> partyVotes;

    public VotingApp() {
        candidateVotes = new HashMap<>();
        partyVotes = new HashMap<>();
    }

    public void addBallot(Ballot ballot) {
        Map<String, String> choices = ballot.getChoices();
        for (Map.Entry<String, String> entry : choices.entrySet()) {
            if (entry.getValue().contains("Candidate")) {
                String candidate = entry.getValue().split(" ")[1];
                if (candidateVotes.containsKey(candidate)) {
                    candidateVotes.put(candidate, candidateVotes.get(candidate) + 1);
                } else {
                    candidateVotes.put(candidate, 1);
                }
            } else if (entry.getValue().contains("Party")) {
                String party = entry.getValue().split(" ")[1];
                if (partyVotes.containsKey(party)) {
                    partyVotes.put(party, partyVotes.get(party) + 1);
                } else {
                    partyVotes.put(party, 1);
                }
            }
        }
    }

    public void printResults() {
        System.out.println("Candidate Results:");
        for (Map.Entry<String, Integer> entry : candidateVotes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("\nParty Results:");
        for (Map.Entry<String, Integer> entry : partyVotes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        VotingApp app = new VotingApp();

        Map<String, String> ballot1Choices = new HashMap<>();
        ballot1Choices.put("President", "Candidate John");
        ballot1Choices.put("Senate", "Candidate Jane");
        ballot1Choices.put("Governor", "Candidate Bob");
        ballot1Choices.put("Party", "Party A");
        app.addBallot(new Ballot("Alice", 25, ballot1Choices));

        Map<String, String> ballot2Choices = new HashMap<>();
        ballot2Choices.put("President", "Candidate Jane");
        ballot2Choices.put("Senate", "Candidate Bob");
        ballot2Choices.put("Governor", "Candidate John");
        ballot2Choices.put("Party", "Party B");
        app.addBallot(new Ballot("Bob", 30, ballot2Choices));

        app.printResults();
    }
}
```