package ua.com.alevel.hw2.service.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class MyFileReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyFileReader.class);

    public Optional<Map<String, Object>> readPlaneFromFile(String fileName) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new FileNotFoundException();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> lines = new ArrayList<>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            bufferedReader.close();
            LOGGER.info("Object was read from file successfully");
            return Optional.of(parser(lines));
        }
        catch (FileNotFoundException e) {
            LOGGER.warn("File not found: {}", fileName);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    protected abstract Map<String, Object> parser(List<String> lines);
}
