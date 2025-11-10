```java
import java.util.HashMap;
import java.util.Map;

public class VotingSystem {

    private Map<String, Integer> ballotCounts;

    public VotingSystem() {
        ballotCounts = new HashMap<>();
    }

    public void registerBallot(String ballotType, String voterName) {
        if (ballotCounts.containsKey(ballotType)) {
            ballotCounts.put(ballotType, ballotCounts.get(ballotType) + 1);
        } else {
            ballotCounts.put(ballotType, 1);
        }
    }

    public void tallyBallotCounts() {
        for (Map.Entry<String, Integer> entry : ballotCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}
```