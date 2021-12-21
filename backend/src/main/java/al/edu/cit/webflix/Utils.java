package al.edu.cit.webflix;

import org.springframework.core.io.ClassPathResource;

import java.io.*;

public class Utils {
    public static String surroundWithSingleQuotes(String s) {
        return "'" + escapeSingleQuotes(s) + "'";
    }

    public static String escapeSingleQuotes(String s) {
        return s.replace("'", "\\'");
    }

    public static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public static String readFileFromResources(String filename) throws IOException {
        File file = new ClassPathResource(filename).getFile();
        return inputStreamToString(new FileInputStream(file));
    }
}
