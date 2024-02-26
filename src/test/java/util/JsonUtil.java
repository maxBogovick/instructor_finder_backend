package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class JsonUtil {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static String loadJsonFromFile(String filePath) throws IOException {
    File file = Paths.get("src","test","resources", filePath).toFile();
    return objectMapper.writeValueAsString(objectMapper.readValue(file, Object.class));
  }
}
