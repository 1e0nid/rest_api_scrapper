package Writer;

import Data.Data;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

class WriterTest {

    List<Data> data = List.of(
            new Data(Map.of("name", "Harry", "age", 17)),
            new Data(Map.of("name", "Hermione", "age", 17))
    );

    @Test
    void testJsonWriteCreatesFile() throws Exception {
        File tempFile = File.createTempFile("test", ".json");
        tempFile.deleteOnExit();

        JsonFileWriter.append(tempFile.getAbsolutePath(), data);

        assert tempFile.exists();
        assert Files.readString(tempFile.toPath()).contains("Harry");
    }

    @Test
    void testCsvWriteCreatesFile() throws Exception {
        File tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit();

        CsvFileWriter.append(tempFile.getAbsolutePath(), data);

        assert tempFile.exists();
        String content = Files.readString(tempFile.toPath());
        assert content.contains("name");
        assert content.contains("Harry");
    }
}