package ftn.uns.ac.rs.NVTKTS20222023.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ftn.uns.ac.rs.NVTKTS20222023.model.Driver;
import ftn.uns.ac.rs.NVTKTS20222023.model.Location;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LocationService {

    public static final String API_KEY = "5b3ce3597851110001cf6248e6804a5ca1304e419e008bc5b233592b";

    public static final String API_KEY_MAP_QUEST = "6tArMOJHcXSVjIlhJQJsaVcZyPEb40n0";

    public double getDistance(Location l1 , Location l2){
        return Math.sqrt(Math.pow(l1.getLongitude()-l2.getLongitude() , 2) + Math.pow(l1.getLatitude()-l2.getLatitude(),2));
    }

    public List<Location> getLocationsForWaypoints(Location l1 , Location l2) {

        List<Location> locationList = new ArrayList<>();

        String origin = l1.getLongitude() + "," + l1.getLatitude();
        System.out.println("ORIGIN: " + origin);
        String destination = l2.getLongitude() + "," + l2.getLatitude();
        System.out.println("DEST: " + destination);
        try {
            // Build the API request URL
            String apiUrl = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=" + API_KEY + "&start=" + origin + "&end=" + destination;

            // Send the API request and get the response
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            System.out.println("DOSAO");
            System.out.println(connection.getInputStream());
            System.out.println("KRAJJ");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            System.out.println("PROSAO");

            String inputLine;

            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {

                response.append(inputLine);

            }

            in.close();

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());

            JSONArray features = jsonResponse.getJSONArray("features");

            JSONObject feature = features.getJSONObject(0);

            JSONObject geometry = feature.getJSONObject("geometry");

            JSONArray coordinates = geometry.getJSONArray("coordinates");


            for (int i = 0; i < coordinates.length(); i += 5) {

                JSONArray coord = coordinates.getJSONArray(i);

                double longitude = coord.getDouble(0);

                double latitude = coord.getDouble(1);

                Location location = new Location();

                location.setLongitude(longitude);

                location.setLatitude(latitude);

                locationList.add(location);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return locationList;
    }


    public List<Location> getLocationsForWaypointsMapQuest(Location l1 , Location l2) {

        List<Location> locationList = new ArrayList<>();

        String origin = l1.getLatitude() + "," + l1.getLongitude();
        System.out.println("ORIGIN API_KEY_MAP_QUEST: " + origin);
        String destination = l2.getLatitude() + "," + l2.getLongitude() ;
        System.out.println("DEST API_KEY_MAP_QUEST: " + destination);
        try {
            // Build the API request URL
            String apiUrl = "http://www.mapquestapi.com/directions/v2/route?key=" + API_KEY_MAP_QUEST + "&from=" + origin + "&to=" + destination;
            System.out.println("URL IS" + apiUrl);
            // Send the API request and get the response
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            System.out.println("DOSAO API_KEY_MAP_QUEST");
            System.out.println(connection.getInputStream());
            System.out.println("KRAJJ API_KEY_MAP_QUEST");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            System.out.println("PROSAO");

            String inputLine;

            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {

                response.append(inputLine);

            }

            in.close();
            System.out.println(response);

            // Parse the JSON string
            Gson gson = new Gson();

            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

            // Get the "legs" array from the JSON object
            JsonArray legsArray = jsonObject.getAsJsonObject("route").getAsJsonArray("legs");

            for (JsonElement legElement : legsArray) {
                JsonObject legObject = legElement.getAsJsonObject();

                // Get the "maneuvers" array from the leg
                JsonArray maneuversArray = legObject.getAsJsonArray("maneuvers");

                // Iterate over each maneuver
                for (JsonElement maneuverElement : maneuversArray) {
                    JsonObject maneuverObject = maneuverElement.getAsJsonObject();

                    // Get the latitude and longitude coordinates from the maneuver
                    JsonObject startPoint = maneuverObject.getAsJsonObject("startPoint");
                    double latitude = startPoint.get("lat").getAsDouble();
                    double longitude = startPoint.get("lng").getAsDouble();

                    // Print the coordinates
                    System.out.println("Latitude: " + latitude);
                    System.out.println("Longitude: " + longitude);
                    System.out.println();

                    Location location = new Location();

                    location.setLongitude(longitude);

                    location.setLatitude(latitude);

                    locationList.add(location);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return locationList;
    }

}
