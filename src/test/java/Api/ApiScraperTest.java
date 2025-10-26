package Api;

import Data.Data;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiScraperTest {

    @Test
    public void testParseJson_ValidJson() throws JsonProcessingException {
        String json = """
            [
              {
                "name": "Axe",
                "role": "Initiator"
              },
              {
                "name": "Crystal Maiden",
                "role": "Support"
              }
            ]
        """;

        DotaApi api = new DotaApi("http://fake-url");
        List<Data> result = api.parseJson(json);

        assertEquals(2, result.size());

        assertEquals("Axe", result.get(0).content().get("name"));
        assertEquals("Initiator", result.get(0).content().get("role"));

        assertEquals("Crystal Maiden", result.get(1).content().get("name"));
        assertEquals("Support", result.get(1).content().get("role"));
    }

    @Test
    public void testParseJson_EmptyArray() throws JsonProcessingException {
        String json = "[]";
        DotaApi api = new DotaApi("http://fake-url");
        List<Data> result = api.parseJson(json);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testParseJson_InvalidJson() {
        String invalidJson = "{not a valid json}";
        DotaApi api = new DotaApi("http://fake-url");

        assertThrows(JsonProcessingException.class, () -> {
            api.parseJson(invalidJson);
        });
    }

    @Test
    public void testParseJson_NonArrayJson() {
        String objectJson = """
            {
              "name": "Invoker"
            }
        """;
        DotaApi api = new DotaApi("http://fake-url");

        assertThrows(JsonProcessingException.class, () -> {
            api.parseJson(objectJson);
        });
    }

    @Test
    public void testFetchAll_BadURL() {
        ApiScraper apiScraper = new ApiScraper("http://fake-url") {
            @Override
            List<Data> parseJson(String jsonString) {
                return null;
            }
        };
        assertThrows(IOException.class, () -> apiScraper.fetchAll());

    }
}