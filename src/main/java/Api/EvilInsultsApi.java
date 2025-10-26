package Api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Data.Data;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EvilInsultsApi extends ApiScraper{
    private final ObjectMapper objectMapper;

    public EvilInsultsApi(String url) {
        super(url);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<Data> parseJson(String jsonString) throws JsonProcessingException {
        Data data = new Data(objectMapper.readValue(jsonString, Map.class));
        return Collections.singletonList(data);
        }
}
