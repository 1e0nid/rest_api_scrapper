import Scraper.ScraperConfig;
import Scraper.ScraperManager;

public class Main {
    public static void main(String[] args) {
        try {
            ScraperConfig config = new ScraperConfig(args);
            ScraperManager scraperManager = new ScraperManager(config);
            scraperManager.start();
        } catch (IllegalArgumentException | InterruptedException e) {
            System.out.println(e);
        }
    }
}