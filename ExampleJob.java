/**
 * Example implementation of a Job.
 */
public class ExampleJob implements Job {
    private final String id;
    private final String message;
    private final int duration; // milliseconds
    
    /**
     * Creates a new example job.
     * @param id unique identifier for the job
     * @param message message to print when executing
     * @param duration how long the job should take (in milliseconds)
     */
    public ExampleJob(String id, String message, int duration) {
        this.id = id;
        this.message = message;
        this.duration = duration;
    }
    
    @Override
    public void execute() {
        try {
            System.out.println("  [Job " + id + "] " + message);
            Thread.sleep(duration); // Simulate work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Job " + id + " was interrupted");
        }
    }
    
    @Override
    public String getId() {
        return id;
    }
}

