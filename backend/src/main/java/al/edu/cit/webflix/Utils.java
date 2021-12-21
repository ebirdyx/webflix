package al.edu.cit.webflix;

import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Utils {
    public static String surroundWithSingleQuotes(String s) {
        return "'" + escapeSingleQuotes(s) + "'";
    }

    public static String escapeSingleQuotes(String s) {
        return s.replace("'", "\\'");
    }

    public static String inputStreamToString(InputStream is) {
        return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining());
    }

    public static String readFileFromResources(String filename) throws IOException {
        File file = new ClassPathResource(filename).getFile();
        return inputStreamToString(new FileInputStream(file));
    }
}
