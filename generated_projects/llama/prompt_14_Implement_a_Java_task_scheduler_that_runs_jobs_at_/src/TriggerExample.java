public class TriggerExample {
    public static void main(String[] args) {
        Trigger trigger = new IntervalTrigger(5);

        for (int i = 0; i < 10; i++) {
            if (trigger.isTriggered(i)) {
                System.out.println("Triggered at " + i);
            }
        }
    }
}