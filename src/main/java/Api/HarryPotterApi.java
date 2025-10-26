package Api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import Data.Data;

import java.util.List;
import java.util.Map;

public class HarryPotterApi extends ApiScraper {
    private final ObjectMapper objectMapper;

    public HarryPotterApi(String url) {
        super(url);
        this.objectMapper = new ObjectMapper();
    }

    public List<Data> parseJson(String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, new TypeReference<List<Map<String, Object>>>() {}).stream().map(Data::new).toList();
    }
}
