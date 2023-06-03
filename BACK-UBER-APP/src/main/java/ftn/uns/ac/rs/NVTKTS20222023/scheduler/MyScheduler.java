package ftn.uns.ac.rs.NVTKTS20222023.scheduler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.VehicleMapViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Driver;
import ftn.uns.ac.rs.NVTKTS20222023.model.Location;
import ftn.uns.ac.rs.NVTKTS20222023.model.Ride;
import ftn.uns.ac.rs.NVTKTS20222023.repository.RideRepository;
import ftn.uns.ac.rs.NVTKTS20222023.service.DriverService;
import ftn.uns.ac.rs.NVTKTS20222023.service.LocationService;
import ftn.uns.ac.rs.NVTKTS20222023.service.RideService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class MyScheduler {

    @Autowired
    private DriverService ds;

    @Autowired
    private LocationService ls;

    @Autowired
    private RideRepository rr;

    @Autowired
    private RideService rs;

    @Autowired
    private SimpMessagingTemplate template;

//    @PostConstruct
    @Scheduled(fixedRate = 5000)
    public void moveCar() throws IOException {

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        ds.activeDriversIncrementCounter();

        List<VehicleMapViewDTO> lvmvdto = ds.getAllVehicleMapViewDTO();

        executor.execute(() -> template.convertAndSend("/topic/vehicle", lvmvdto));

//        for(VehicleMapViewDTO e : lvmvdto){
//
//            System.out.println(e);
//
//        }

    }

    @Scheduled(fixedRate = 60000)
    public void setDriver() throws IOException {
        System.out.println("FUTURE RIDE");
        List<Ride> rides = rr.findAll().stream()

                .filter(r-> r.getStatus().equals("CREATE")
                        && r.getPaid().split("\\|").length == r.getCitizens().size()
                        && (r.getStart()-(new Date()).getTime()) <= 15*60*1000
                        && r.getDriver() == null
                )
                .collect(Collectors.toList());

//        List<Ride> rides = new ArrayList<>();
//        for(Ride r: rr.findAll()){
//            System.out.println(r.getPaid().split("\\|").length == r.getCitizens().size());
//            System.out.println((r.getStart()-(new Date()).getTime()) <= 15*60*1000);
//            System.out.println(r.getDriver() == null);
//        }
//        System.out.println(rides.size());

        rides.stream().forEach(r->rs.findDriver(r.getId()));
//        ds.activeDriversIncrementCounter();
//        List<VehicleMapViewDTO> lvmvdto = ds.getAllVehicleMapViewDTO();
//        for(VehicleMapViewDTO e : lvmvdto){
//            System.out.println(e);
//        }


    }

}
