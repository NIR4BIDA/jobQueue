/**
 * Worker thread that processes jobs from a job queue.
 */
public class Worker extends Thread {
    private final JobQueue jobQueue;
    private volatile boolean running = true;
    private int jobsProcessed = 0;
    
    /**
     * Creates a new worker that processes jobs from the given queue.
     * @param jobQueue the queue to process jobs from
     * @param name the name of the worker thread
     */
    public Worker(JobQueue jobQueue, String name) {
        super(name);
        this.jobQueue = jobQueue;
    }
    
    @Override
    public void run() {
        System.out.println(getName() + " started");
        
        while (running) {
            try {
                Job job = jobQueue.dequeue();
                
                if (job == null) {
                    // Queue is shutdown and empty
                    break;
                }
                
                // Process the job
                System.out.println(getName() + " processing job: " + job.getId());
                job.execute();
                jobsProcessed++;
                System.out.println(getName() + " completed job: " + job.getId());
                
            } catch (InterruptedException e) {
                System.out.println(getName() + " interrupted");
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println(getName() + " error processing job: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        System.out.println(getName() + " stopped. Processed " + jobsProcessed + " jobs");
    }
    
    /**
     * Stops the worker gracefully.
     * The worker will finish processing the current job before stopping.
     */
    public void stopWorker() {
        running = false;
        interrupt();
    }
    
    /**
     * Gets the number of jobs processed by this worker.
     * @return the number of jobs processed
     */
    public int getJobsProcessed() {
        return jobsProcessed;
    }
}

