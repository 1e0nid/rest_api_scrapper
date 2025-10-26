package Writer;

import Data.Data;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class CsvFileWriter {
    private static final CsvMapper mapper = new CsvMapper();
    private static final ReentrantLock lock = new ReentrantLock();

    public static void append(String filePath, List<Data> newData) throws IOException {
        lock.lock();

        List<Map<String, Object>> newRecords = convertToMaps(newData);

        Set<String> headers = collectHeaders(newRecords);
        CsvSchema schema = buildSchema(headers);

        File file = new File(filePath);
        appendData(file, newRecords, schema.withoutHeader());

        lock.unlock();
    }

    private static void appendData(File file, List<Map<String, Object>> data, CsvSchema schema) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {

            String headerLine = String.join(",", schema.getColumnNames());

            writer.print(headerLine);
            writer.println();

            String csvData = mapper.writerFor(List.class)
                    .with(schema)
                    .writeValueAsString(data)
                    .trim();

            writer.print(csvData);
            writer.println();
        }
    }

    private static List<Map<String, Object>> convertToMaps(List<Data> data) {
        return data.stream()
                .map(Data::content)
                .collect(Collectors.toList());
    }

    private static Set<String> collectHeaders(List<Map<String, Object>> records) {
        return records.getFirst().keySet();
    }

    private static CsvSchema buildSchema(Set<String> headers) {
        CsvSchema.Builder schemaBuilder = CsvSchema.builder();
        headers.forEach(schemaBuilder::addColumn);
        return schemaBuilder.build();
    }
}