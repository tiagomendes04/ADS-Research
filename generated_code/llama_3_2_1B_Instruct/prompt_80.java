```java
import java.util.ArrayList;
import java.util.List;

class Ballot {
    String[] options;
    int votes;

    public Ballot(String[] options) {
        this.options = options;
        this.votes = 0;
    }

    public void addVote(String option) {
        votes++;
        for (String o : options) {
            if (o.equals(option)) {
                votes--;
                break;
            }
        }
    }

    public int getVotes() {
        return votes;
    }
}

class VotingApp {
    List<Ballot> ballots = new ArrayList<>();

    public void createBallot(String[] options) {
        ballots.add(new Ballot(options));
    }

    public void vote(String ballotNumber, String option) {
        if (ballotNumber < 1 || ballotNumber > ballots.size()) {
            System.out.println("Invalid ballot number.");
            return;
        }
        ballots.get(ballotNumber - 1).addVote(option);
    }

    public int countVotes(String ballotNumber) {
        if (ballotNumber < 1 || ballotNumber > ballots.size()) {
            System.out.println("Invalid ballot number.");
            return 0;
        }
        return ballots.get(ballotNumber - 1).getVotes();
    }

    public void displayBallot(String ballotNumber) {
        if (ballotNumber < 1 || ballotNumber > ballots.size()) {
            System.out.println("Invalid ballot number.");
            return;
        }
        System.out.println(ballots.get(ballotNumber - 1));
    }

    public void displayAllBallots() {
        for (int i = 1; i <= ballots.size(); i++) {
            System.out.println("Ballot " + i + ":");
            displayBallot(i);
            System.out.println();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        VotingApp app = new VotingApp();
        app.createBallot(new String[]{"Option A", "Option B", "Option C"});
        app.createBallot(new String[]{"Option D", "Option E", "Option F"});
        app.createBallot(new String[]{"Option G", "Option H", "Option I"});
        app.displayAllBallots();
        app.vote(1, "Option A");
        app.vote(1, "Option B");
        app.displayAllBallots();
        System.out.println(app.countVotes(1));
        app.displayAllBallots();
    }
}
```