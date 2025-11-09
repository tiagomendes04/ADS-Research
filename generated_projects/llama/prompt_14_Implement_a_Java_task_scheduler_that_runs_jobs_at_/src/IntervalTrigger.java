public class IntervalTrigger implements Trigger {
    private final long interval;

    public IntervalTrigger(long interval) {
        this.interval = interval;
    }

    @Override
    public boolean isTriggered(long time) {
        return time % interval == 0;
    }
}