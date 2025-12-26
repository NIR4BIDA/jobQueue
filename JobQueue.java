import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Thread-safe job queue that stores and retrieves jobs.
 */
public class JobQueue {
    private final BlockingQueue<Job> queue;
    private volatile boolean isShutdown = false;
    
    /**
     * Creates a new job queue with unbounded capacity.
     */
    public JobQueue() {
        this.queue = new LinkedBlockingQueue<>();
    }
    
    /**
     * Creates a new job queue with specified capacity.
     * @param capacity maximum number of jobs in the queue
     */
    public JobQueue(int capacity) {
        this.queue = new LinkedBlockingQueue<>(capacity);
    }
    
    /**
     * Adds a job to the queue.
     * @param job the job to be added
     * @return true if the job was added successfully
     * @throws IllegalStateException if the queue is shutdown
     */
    public boolean enqueue(Job job) {
        if (isShutdown) {
            throw new IllegalStateException("Queue is shutdown");
        }
        return queue.offer(job);
    }
    
    /**
     * Retrieves and removes a job from the queue.
     * Blocks until a job is available or the queue is shutdown.
     * @return the next job, or null if the queue is shutdown
     * @throws InterruptedException if interrupted while waiting
     */
    public Job dequeue() throws InterruptedException {
        if (isShutdown && queue.isEmpty()) {
            return null;
        }
        return queue.take();
    }
    
    /**
     * Gets the current number of jobs in the queue.
     * @return the size of the queue
     */
    public int size() {
        return queue.size();
    }
    
    /**
     * Checks if the queue is empty.
     * @return true if the queue is empty
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    /**
     * Shuts down the queue, preventing new jobs from being added.
     * Workers will finish processing existing jobs.
     */
    public void shutdown() {
        isShutdown = true;
    }
    
    /**
     * Checks if the queue is shutdown.
     * @return true if the queue is shutdown
     */
    public boolean isShutdown() {
        return isShutdown;
    }
}

