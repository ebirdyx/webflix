package al.edu.cit.webflix.loader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import al.edu.cit.webflix.loader.models.movies.XMLMovies;
import al.edu.cit.webflix.loader.models.people.XMLPeople;
import al.edu.cit.webflix.loader.models.people.XMLPerson;
import org.springframework.boot.CommandLineRunner;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

class DatabaseLoader implements CommandLineRunner {
    private static Connection conn = null;

    private static String readXMLFileFromResources(String filename) {
        // get resources folder path
        Path root = FileSystems.getDefault().getPath("resources").toAbsolutePath();
        File file = root.resolve(filename).toFile();

        String content = null;
        try {
            content = inputStreamToString(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    // XML -> Object
    private static Object deserializeObjectFromXML(String content, Class type) {
        XmlMapper xmlMapper = new XmlMapper();
        Object obj = null;
        try {
            xmlMapper.setDefaultUseWrapper(false);
            obj = xmlMapper.readValue(content, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return obj;
    }

    private static void runQueryOnDatabase(String query) {
        try {
            if (conn == null)
                conn = DriverManager.getConnection("jdbc:mysql://localhost/webflix?" +
                        "user=root&password=rWfZmagGgFbdus4E0PsJREfC");

            Statement stmt = conn.createStatement(); //way to send queries to db
            ResultSet rs = stmt.executeQuery(query);  // response from db

            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    if (i > 1) System.out.print(",  ");
                    System.out.print(rs.getMetaData().getColumnName(i) + ": " +
                            rs.getString(i));
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadPeople() {
        String xml = readXMLFileFromResources("people_latin1.xml");
        XMLPeople people = (XMLPeople) deserializeObjectFromXML(xml, XMLPeople.class);

        for (XMLPerson person : people.people) {
            String query = "insert into People (id, name, dob, bio, photo, birth_city, birth_state, birth_country) " +
                    "values(" + person.id + ", " + person.getName() + ", " + person.getBirth().getSQLDob() +
                    ", " + person.getEscapedBio() + ", " + person.getPhoto() + ", " + person.getBirth().getCityOfBirth() + ", " +
                    person.getBirth().getStateOfBirth() + ", " + person.getBirth().getCountryOfBirth() + ");";

            System.out.println(query);

            runQueryOnDatabase(query);
        }
    }

    public static void loadMovies() {
        String xml = readXMLFileFromResources("movies_latin1.xml");
        XMLMovies movies = (XMLMovies) deserializeObjectFromXML(xml, XMLMovies.class);

//        Set<String> genres = new HashSet<>();
//
//        movies.movies.stream()
//                .forEach(xmlMovie -> xmlMovie.genres.stream()
//                        .forEach(genre -> genres.add(genre)));
//
//        genres.stream().forEach(genre -> {
//            String query = "insert into Genre(name) values('" + genre + "');";
//            runQueryOnDatabase(query);
//        });

//        Set<String> countries = new HashSet<>();
//
//        movies.movies.stream()
//                .forEach(xmlMovie -> xmlMovie.countries.stream()
//                        .forEach(country -> countries.add(country)));
//
//        countries.stream().forEach(country -> {
//            String query = "insert into ProductionCountry(name) values('" + country + "');";
//            runQueryOnDatabase(query);
//        });

        Set<String> languages = new HashSet<>();

        movies.movies.stream()
                .forEach(xmlMovie -> {
                    if (xmlMovie.language == null) return;
                    languages.add(xmlMovie.language);
                });

        languages.stream().forEach(language -> {
            String query = "insert into MovieLanguages(name) values('" + language + "');";
            runQueryOnDatabase(query);
        });

        movies.movies.stream().forEach(xmlMovie -> {
            int languageIndex = 0;
            for (String language : languages) {
                languageIndex++;
                if (language.equals(xmlMovie.language)) break;
            }

            String movieQuery = "insert into Movie(id, title, publishing_year, duration, synopsis,  cover, language_id, director_id)"
                    + "values(" + xmlMovie.id + ", " + xmlMovie.getTitle() + ", " + xmlMovie.year + ", " + xmlMovie.duration + ", "
                    + xmlMovie.getEscapedSynopsy() + ", " + xmlMovie.getEscapedCover() + ", " + languageIndex +", " +
                    xmlMovie.getDirector().id + "');";
        });
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

    @Override
    public void run(String... args) throws Exception {
        //        loadPeople();
        loadMovies();

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}