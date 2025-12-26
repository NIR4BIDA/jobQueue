import java.util.ArrayList;
import java.util.List;

/**
 * Manages a job queue and multiple worker threads.
 */
public class JobQueueManager {
    private final JobQueue jobQueue;
    private final List<Worker> workers;
    private final int numWorkers;
    
    /**
     * Creates a new job queue manager with the specified number of workers.
     * @param numWorkers the number of worker threads to create
     */
    public JobQueueManager(int numWorkers) {
        this.numWorkers = numWorkers;
        this.jobQueue = new JobQueue();
        this.workers = new ArrayList<>();
        
        // Create and start workers
        for (int i = 1; i <= numWorkers; i++) {
            Worker worker = new Worker(jobQueue, "Worker-" + i);
            workers.add(worker);
            worker.start();
        }
    }
    
    /**
     * Creates a new job queue manager with the specified number of workers and queue capacity.
     * @param numWorkers the number of worker threads to create
     * @param queueCapacity the maximum capacity of the job queue
     */
    public JobQueueManager(int numWorkers, int queueCapacity) {
        this.numWorkers = numWorkers;
        this.jobQueue = new JobQueue(queueCapacity);
        this.workers = new ArrayList<>();
        
        // Create and start workers
        for (int i = 1; i <= numWorkers; i++) {
            Worker worker = new Worker(jobQueue, "Worker-" + i);
            workers.add(worker);
            worker.start();
        }
    }
    
    /**
     * Submits a job to the queue.
     * @param job the job to be executed
     * @return true if the job was added successfully
     */
    public boolean submitJob(Job job) {
        return jobQueue.enqueue(job);
    }
    
    /**
     * Shuts down the job queue manager.
     * This will stop accepting new jobs and wait for all workers to finish.
     */
    public void shutdown() {
        System.out.println("Shutting down job queue manager...");
        jobQueue.shutdown();
        
        // Wait for all workers to finish
        for (Worker worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interrupted while waiting for worker: " + worker.getName());
            }
        }
        
        System.out.println("All workers stopped. Total jobs processed:");
        for (Worker worker : workers) {
            System.out.println("  " + worker.getName() + ": " + worker.getJobsProcessed() + " jobs");
        }
    }
    
    /**
     * Gets the current number of jobs in the queue.
     * @return the number of pending jobs
     */
    public int getQueueSize() {
        return jobQueue.size();
    }
    
    /**
     * Gets the number of workers.
     * @return the number of workers
     */
    public int getNumWorkers() {
        return numWorkers;
    }
}

