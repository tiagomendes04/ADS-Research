public class ExampleUsage {
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();

        // Schedule a job to run every 5 seconds
        scheduler.scheduleAtFixedRate(new Job() {
            @Override
            public void execute() {
                System.out.println("Job executed at fixed rate");
            }
        }, 0, 5, TimeUnit.SECONDS);

        // Schedule a job to run every 10 seconds with a delay of 2 seconds
        scheduler.scheduleWithFixedDelay(new Job() {
            @Override
            public void execute() {
                System.out.println("Job executed with fixed delay");
            }
        }, 0, 2, TimeUnit.SECONDS);

        // Schedule a job to run at a custom trigger time
        scheduler.schedule(new Job() {
            @Override
            public void execute() {
                System.out.println("Job executed at custom trigger time");
            }
        }, 10000, TimeUnit.MILLISECONDS);

        // Shutdown the scheduler
        scheduler.shutdown();
    }
}