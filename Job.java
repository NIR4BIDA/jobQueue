/**
 * Interface representing a job that can be executed by a worker.
 */
public interface Job {
    /**
     * Executes the job.
     * This method will be called by worker threads.
     */
    void execute();
    
    /**
     * Gets the job ID for identification.
     * @return unique job identifier
     */
    String getId();
}

