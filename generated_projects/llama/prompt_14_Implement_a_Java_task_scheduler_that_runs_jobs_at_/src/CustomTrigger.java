public class CustomTrigger implements Trigger {
    private final long[] triggerTimes;

    public CustomTrigger(long[] triggerTimes) {
        this.triggerTimes = triggerTimes;
    }

    @Override
    public boolean isTriggered(long time) {
        for (long triggerTime : triggerTimes) {
            if (time == triggerTime) {
                return true;
            }
        }
        return false;
    }
}