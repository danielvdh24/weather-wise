package app.weatherwise;

// import toolkits to decode JSON
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// import toolkits to establish HTTP connections and retrieve data from URLs
import java.net.HttpURLConnection;
import java.net.URL;

// other imports
import java.util.Scanner;
import java.io.IOException;

public class APIService {

    private static APIService instance;

    public APIService() {
    }

    // returns the single instance of the APIService class, creating one if none exists
    public static APIService getInstance() {
        if (instance == null) {
            instance = new APIService();
        }
        return instance;
    }

    // creates an HTTP request to specified query and returns response as a String
    private String makeHttpRequest(String inputUrl) throws IOException {
        URL url = new URL(inputUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // open a connection to the url using HTTP
        urlConnection.setRequestMethod("GET"); // set request method to GET (to retrieve data)
        urlConnection.connect(); // establish connection to url

        int responseCode = urlConnection.getResponseCode();
        String responseMessage = urlConnection.getResponseMessage();
        if (responseCode != 200) { // if responseCode != 200 (200 is a success response code) then throw exception
            throw new RuntimeException("Connection failed with HTTP response code: " + responseCode + "\n" + responseMessage);
        } else {
            StringBuilder weatherInfo = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) { // read the JSON data from the API response
                weatherInfo.append(scanner.nextLine()); // append to weatherInfo
            }
            scanner.close();

            return weatherInfo.toString();
        }
    }

    // parse the response and returns as an equivalent JSONObject object
    public JSONObject getJSONObject(String url) throws IOException, ParseException {
        String weatherInfo = makeHttpRequest(url);
        JSONParser parse = new JSONParser();
        return (JSONObject) parse.parse(weatherInfo);
    }

    // parse the response and returns as an equivalent JSONArray object
    public JSONArray getJSONArray(String url) throws IOException, ParseException {
        String weatherInfo = makeHttpRequest(url);
        JSONParser parse = new JSONParser();
        return (JSONArray) parse.parse(weatherInfo);
    }
}