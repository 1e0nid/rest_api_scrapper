package Scraper;

import java.util.concurrent.*;
import java.util.concurrent.ScheduledExecutorService;

public class ScraperManager {
    private final ScraperConfig config;

    public ScraperManager(ScraperConfig config) {
        this.config = config;
    }

    public void start() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(config.getThreadPoolSize());
        for (NameApi nameApi : config.getApiNames()) {
            executor.scheduleAtFixedRate(
                    new ScraperTask(nameApi, config.getOutputFile()),
                    1,
                    config.getTimeoutSeconds(),
                    TimeUnit.SECONDS);
        }
        try {
            TimeUnit.SECONDS.sleep(10);
            executor.shutdown();
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        }
        catch (InterruptedException e) {
            executor.shutdownNow();
            System.out.println("Main is interrupted");
        }
    }
}
