package Scraper;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ScraperConfig {
    private final int threadPoolSize;
    private final int timeoutSeconds;
    private final List<NameApi> apiNames;
    private final String outputFile;

    public ScraperConfig(String[] args) throws IllegalArgumentException {
        try {
            this.threadPoolSize = Integer.parseInt(args[0]);
            this.timeoutSeconds = Integer.parseInt(args[1]);
            this.apiNames = Arrays.stream(Arrays.copyOfRange(args, 2, args.length - 1)).map(NameApi::valueOf).collect(Collectors.toList());
            this.outputFile = args[args.length - 1];
            if (!(outputFile.substring(outputFile.lastIndexOf('.') + 1).equals("json") | outputFile.substring(outputFile.lastIndexOf('.') + 1).equals("csv"))){
                throw new Exception("Error path output file");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error entering an argument");
        }
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public List<NameApi> getApiNames() {
        return apiNames;
    }

    public String getOutputFile() {
        return outputFile;
    }
}