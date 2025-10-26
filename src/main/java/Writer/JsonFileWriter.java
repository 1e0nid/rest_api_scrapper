package Writer;

import Data.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class JsonFileWriter {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
    private static final ReentrantLock lock = new ReentrantLock();

    public static void append(String pathJson, List<Data> newData) throws IOException {
        lock.lock();
        File file = new File(pathJson);

        if (!file.exists()) {
            objectMapper.writeValue(file, newData);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("[");


            String newDataStr = objectMapper.writeValueAsString(newData)
                    .replaceFirst("\\[", "")
                    .replaceAll("\\]$", "");

            writer.write(newDataStr);
            writer.write("]");
        }
        lock.unlock();
    }
}
