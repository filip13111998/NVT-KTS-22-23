package ftn.uns.ac.rs.NVTKTS20222023.service;

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


    public double getDistance(Location l1 , Location l2){
        return Math.sqrt(Math.pow(l1.getLongitude()-l2.getLongitude() , 2) + Math.pow(l1.getLatitude()-l2.getLatitude(),2));
    }

    public List<Location> getLocationsForWaypoints(Location l1 , Location l2) {


//        String origin = "8.6821,49.4146";
//        String destination = "8.5357,49.0069";
        List<Location> locationList = new ArrayList<>();
        String origin = l1.getLatitude() + "," + l1.getLongitude();
        String destination = l2.getLatitude() + "," + l2.getLongitude();


        try {
            // Build the API request URL
            String apiUrl = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=" + API_KEY + "&start=" + origin + "&end=" + destination;

            // Send the API request and get the response
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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
//            System.out.println(locationList);
            System.out.println(locationList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return locationList;
    }


}
