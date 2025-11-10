```java
import java.util.ArrayList;
import java.util.List;

public class GameLoop {

    private List<Unit> units;
    private Map map;

    public GameLoop(List<Unit> units, Map map) {
        this.units = units;
        this.map = map;
    }

    public void run() {
        while (true) {
            // Player Input
            // ...

            // Update Unit Positions
            for (Unit unit : units) {
                unit.updatePosition();
            }

            // Check for Combat
            for (Unit unit1 : units) {
                for (Unit unit2 : units) {
                    if (unit1 != unit2 && unit1.canAttack(unit2)) {
                        unit1.attack(unit2);
                    }
                }
            }

            // Update Map (check for events, etc.)
            // ...

            // Draw the game
            // ...

            // Pause
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // ...
            }
        }
    }
}
```