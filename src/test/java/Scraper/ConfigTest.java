package Scraper;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    void testValidArgs() throws Exception {
        String[] args = {"3", "5", "HARRYPOTTER", "DOTA", "output.json"};

        ScraperConfig config = new ScraperConfig(args);

        assertEquals(3, config.getThreadPoolSize());
        assertEquals(5, config.getTimeoutSeconds());
        assertEquals(List.of(NameApi.HARRYPOTTER, NameApi.DOTA), config.getApiNames());
        assertEquals("output.json", config.getOutputFile());
    }

    @Test
    void testSingleApiArg() throws Exception {
        String[] args = {"2", "10", "EVILINSULTS", "outputCsv.csv"};

        ScraperConfig config = new ScraperConfig(args);

        assertEquals(2, config.getThreadPoolSize());
        assertEquals(10, config.getTimeoutSeconds());
        assertEquals(List.of(NameApi.EVILINSULTS), config.getApiNames());
        assertEquals("outputCsv.csv", config.getOutputFile());
    }

    @Test
    void testInvalidNumberArgThrows() {
        String[] args = {"notANumber", "5", "HARRYPOTTER", "output.json"};

        Exception e = assertThrows(Exception.class, () -> new ScraperConfig(args));
        assertEquals("Error entering an argument", e.getMessage());
    }

    @Test
    void testInvalidApiNameThrows() {
        String[] args = {"2", "5", "INVALID_API", "output.json"};

        Exception e = assertThrows(Exception.class, () -> new ScraperConfig(args));
        assertEquals("Error entering an argument", e.getMessage());
    }

    @Test
    void testTooFewArgsThrows() {
        String[] args = {"2", "5"};  // Недостаточно аргументов

        Exception e = assertThrows(Exception.class, () -> new ScraperConfig(args));
        assertEquals("Error entering an argument", e.getMessage());
    }

}