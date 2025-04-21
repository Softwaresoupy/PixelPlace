import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GoogleMapsService {
    private static final String API_KEY = Config.getGoogleMapsKey();

    public static List<Place> searchPlaces(String query, String type) throws IOException, InterruptedException {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = String.format(
            "https://maps.googleapis.com/maps/api/place/textsearch/json?" +
            "query=%s&type=%s&key=%s",
            encodedQuery, type, API_KEY
        );

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(request, HttpResponse.BodyHandlers.ofString());

        return parsePlaceResponse(response.body());
    }

    private static List<Place> parsePlaceResponse(String json) {
        List<Place> places = new ArrayList<>();
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonArray results = root.getAsJsonArray("results");

        for (JsonElement element : results) {
            JsonObject place = element.getAsJsonObject();
            places.add(new Place(
                place.get("name").getAsString(),
                place.get("place_id").getAsString(),
                place.get("formatted_address").getAsString()
            ));
        }
        return places;
    }

    public static String getStaticMapUrl(String placeId) {
        return String.format(
            "https://maps.googleapis.com/maps/api/staticmap?" +
            "center=place_id:%s&zoom=15&size=600x300&" +
            "markers=color:red|place_id:%s&key=%s",
            placeId, placeId, API_KEY
        );
    }

    public static class Place {
        private final String name;
        private final String placeId;
        private final String address;

        public Place(String name, String placeId, String address) {
            this.name = name;
            this.placeId = placeId;
            this.address = address;
        }

        // Getters...
        @Override
        public String toString() {
            return name + " (" + address + ")";
        }
    }
}
