/**
 * Simulation demo of the job queue system.
 */
public class SimulationDemo {
    public static void main(String[] args) {
        System.out.println("=== Job Queue Demo ===\n");
        
        // Create a job queue manager with 3 workers
        JobQueueManager manager = new JobQueueManager(3);
        
        // Submit some jobs
        System.out.println("Submitting jobs...\n");
        
        for (int i = 1; i <= 10; i++) {
            String message = "Processing task " + i;
            int duration = (int)(Math.random() * 1000) + 500; // 500-1500ms
            Job job = new ExampleJob("JOB-" + i, message, duration);
            manager.submitJob(job);
        }
        
        // Wait a bit for jobs to process
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Submit a few more jobs
        System.out.println("\nSubmitting more jobs...\n");
        for (int i = 11; i <= 15; i++) {
            String message = "Processing task " + i;
            int duration = (int)(Math.random() * 1000) + 500;
            Job job = new ExampleJob("JOB-" + i, message, duration);
            manager.submitJob(job);
        }
        
        // Wait for all jobs to complete
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Shutdown the manager
        System.out.println("\nQueue size before shutdown: " + manager.getQueueSize());
        manager.shutdown();
        
        System.out.println("\n=== Demo Complete ===");
    }
}

