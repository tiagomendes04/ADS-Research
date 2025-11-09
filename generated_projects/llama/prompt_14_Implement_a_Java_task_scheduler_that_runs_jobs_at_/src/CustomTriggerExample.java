public class CustomTriggerExample {
    public static void main(String[] args) {
        Trigger trigger = new CustomTrigger(new long[] { 5, 10, 15 });

        for (int i = 0; i < 20; i++) {
            if (trigger.isTriggered(i)) {
                System.out.println("Triggered at " + i);
            }
        }
    }
}