package Api;

import com.fasterxml.jackson.core.JsonProcessingException;
import Data.Data;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.util.List;

public abstract class ApiScraper {
    private final String URL;
    private final HttpClient httpClient;

    protected ApiScraper(String url) {
        URL = url;
        this.httpClient = HttpClient.newHttpClient();
    }

    public List<Data> fetchAll() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create((URL))).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new HttpTimeoutException("Response error " + response.statusCode());
        }
        return parseJson(response.body());
    }

    abstract List<Data> parseJson(String jsonString) throws JsonProcessingException;
}
