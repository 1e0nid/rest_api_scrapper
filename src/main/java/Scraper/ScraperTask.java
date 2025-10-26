package Scraper;

import Api.ApiScraper;
import Api.DotaApi;
import Api.EvilInsultsApi;
import Api.HarryPotterApi;
import Data.Data;
import Writer.CsvFileWriter;
import Writer.JsonFileWriter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ScraperTask implements Runnable {
    private NameApi nameApi;
    private final String outputFile;

    public ScraperTask(NameApi tasks, String outputFile) {
        this.nameApi = tasks;
        this.outputFile = outputFile;
    }

    @Override
    public void run() {
        System.out.println(nameApi);
        try {
            ApiScraper scraper = switch (nameApi) {
                case NameApi.HARRYPOTTER -> new HarryPotterApi("https://potterapi-fedeperin.vercel.app/en/characters");
                case NameApi.DOTA -> new DotaApi("https://api.opendota.com/api/heroes");
                case NameApi.EVILINSULTS ->
                        new EvilInsultsApi("https://evilinsult.com/generate_insult.php?lang=en&type=json");
            };
            List<Data> data = scraper.fetchAll();
            if (outputFile.substring(outputFile.lastIndexOf('.') + 1).equals("csv")) {
                CsvFileWriter.append(outputFile, data);
            } else if (outputFile.substring(outputFile.lastIndexOf('.') + 1).equals("json")) {
                JsonFileWriter.append(outputFile, data);
            }
            System.out.println("файл записан " + scraper.getClass());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException();
        }
    }
}