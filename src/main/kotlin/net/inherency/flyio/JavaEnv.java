package net.inherency.flyio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JavaEnv {

    public static String GOOGLE_AUTH_JSON_FILE_LOCATION = readFromEnv("googleAuthJsonLocation");
    public static String GOOGLE_AUTH_JSON = readFromEnv("googleAuthJson");

    private static String readFromEnv(String configKey) {
        String value = System.getenv(configKey);
        if (value != null && !value.equals("file")) {
            return value;
        } else {
            String fromFile = readFromFile(configKey);
            if (fromFile != null) {
                return fromFile;
            }
            throw new RuntimeException("Could not find config: " + configKey);
        }
    }

    private static String readFromFile(String configKey) {
        if (configKey.equals("googleAuthJson")) {
            System.out.println("Reading google auth from file");
            String fileLocation = GOOGLE_AUTH_JSON_FILE_LOCATION;
            try {
                return Files.readString(Paths.get(fileLocation));
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }

        } else {
            return null;
        }
    }

}
