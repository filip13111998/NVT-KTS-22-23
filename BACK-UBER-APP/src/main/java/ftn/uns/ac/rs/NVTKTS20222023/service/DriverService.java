package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideNotificationDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.VehicleMapViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.*;
import ftn.uns.ac.rs.NVTKTS20222023.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
public class DriverService {

    @Autowired
    private VehicleRepository vr;

    @Autowired
    private DriverRepository dr;

    @Autowired
    private LocationService ls;

    @Autowired
    private RideRepository rr;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private LocationRepository lr;

    @Autowired
    private LoginHistoryService lhs;

    @Autowired
    private CitizenRepository cr;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private CitizenService cs;

    public List<Driver> findAllActiveDrivers() {

        //GET ALL AVAILABLE DRIVERS WHICH IS ACTIVE AND NOT BLOCK
        List<Driver> availableDrivers = dr.findAll().stream().filter(d-> d.isActive() == true && d.isBlock() == false).collect(Collectors.toList());
        System.out.println(availableDrivers.size());
        availableDrivers = availableDrivers.stream().filter(d->lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours(d.getUsername())).collect(Collectors.toList());

        return availableDrivers;
    }




    public boolean activeDriversIncrementCounter() {

        List<Driver> drivers = this.findAllActiveDrivers();
        System.out.println(drivers);
        for(Driver driver : drivers) {

            //MZD nije current ride vec fake...proveriti booelan atribut if true onca current inace fejk vozis baki.
            Ride ride = driver.getCurrentRide();

            if(ride == null){

                continue;

            }

            if(driver.getCounter() == ride.getAllLocations().size()){

                if(ride.getStatus().equals("START") || ride.getStatus().equals("FAKE")){

                    if(ride.getStatus().equals("FAKE")){

                        System.out.println("DOLAZAK");

                        rr.delete(driver.getCurrentRide());

                        driver.setCurrentRide(null);

                        driver.setCounter(0);

                    }

                    ride.setStatus("END");

                }

//                driver.getVehicle().setBusy(true);

//                vr.save(driver.getVehicle());

                continue;
            }

            Location location = ride.getAllLocations().get(driver.getCounter());

            driver.getVehicle().setBusy(true);

            driver.setCounter(driver.getCounter()+1);

            driver.getVehicle().setLocation(location);

            dr.save(driver);
        }

        return true;
    }

    public List<VehicleMapViewDTO> getAllVehicleMapViewDTO(){


        //GET ALL AVAILABLE DRIVERS WHICH IS ACTIVE AND NOT BLOCK
        List<Driver> availableDrivers = this.findAllActiveDrivers();
//        System.out.println(availableDrivers.size());
        //GET ALL VIECHLES FROM AVAILABLE DRIVERS
        List<Vehicle> vehicles = availableDrivers.stream().map(d -> d.getVehicle()).collect(Collectors.toList());

        //MAKE RESPONSE DTO
        List<VehicleMapViewDTO> vmvdto = vehicles.stream()
                .map(v ->
                        VehicleMapViewDTO.builder()
                                .id(v.getId())
                                .babyFriendly(v.isBabyFriendly())
                                .petFriendly(v.isPetFriendly())
                                .name(v.getName())
                                .occupied(v.isBusy())
                                .type(v.getType())
                                .location(v.getLocation())
                                .build()
                )
                .collect(Collectors.toList());

        return vmvdto;


    }

    public boolean startRide(String username) {

        Driver driver = dr.findByUsername(username);

        if(driver == null){
            return false;
        }

//        System.out.println(driver.getFutureRide() == null);
//        System.out.println(driver.getCurrentRide() != null);
        if(driver.getFutureRide() == null || driver.getCurrentRide()!= null){
//            System.out.println("UDJO");
            return false;
        }

        if(driver.getFutureRide().getStatus().equals("PAID")){

            driver.getFutureRide().setStatus("START");

            driver.setCurrentRide(driver.getFutureRide());

            driver.setFutureRide(null);
        }

        dr.save(driver);

        return true;
    }

    public boolean finishRide(String username) {

        Driver driver = dr.findByUsername(username);

        Location startLocation = null;

        Location endLocation = null;

        if(driver == null){
            return false;
        }

        if(driver.getCurrentRide() == null){
            return false;
        }

        if(driver.getCurrentRide().getStatus().equals("END")){

            startLocation = driver.getVehicle().getLocation();

            cs.unblockAllCitizensByUsernames(driver.getCurrentRide().getCitizens());

            driver.getCurrentRide().setStatus("FINISH");

            driver.getRides().add(driver.getCurrentRide());

            driver.setCurrentRide(null);

            driver.setCounter(0);

            //VEHICLE SET TO FREE
            driver.getVehicle().setBusy(false);

            vr.save(driver.getVehicle());

            dr.save(driver);
        }

        //OVDE IDE LOGIKA ZA AUTOMATSKO UCITAVANJE NOVE RUTE..KONKRETNO OVDE UCITAVAM FAKE RIDE I POSLE TOGA.
        if(driver.getFutureRide() != null){

            endLocation = driver.getFutureRide().getAllLocations().get(0);

//            endLocation = driver.getFutureRide().getAllLocations().get(driver.getFutureRide().getAllLocations().size()-1);
            //VEHICLE SET TO FREE
            driver.getVehicle().setBusy(true);

            vr.save(driver.getVehicle());

            this.makeFakeRide(username , startLocation , endLocation);

            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.execute(() ->
                    template.convertAndSend("/topic/driver/notification/" + driver.getUsername(),
                            RideNotificationDTO.builder()
                                    .text("NOVA VOZNJA : " + driver.getFutureRide().getName())
                                    .price(driver.getFutureRide().getPrice())
                                    .id(driver.getFutureRide().getId())
                                    .build())
            );

        }
        return true;
    }

    public boolean rejectRide(String username,String message) {

        Driver driver = dr.findByUsername(username);

        if(driver == null){
            return false;
        }

        if(driver.getFutureRide() != null){

            List<String> users = Arrays.asList(driver.getFutureRide().getPaid().split("\\|"));

            if(users.size()>0){
                for(String user : users){
                    Citizen citizen = cr.findByUsername(user);
                    citizen.setBlock(false);
                    citizen.setTokens((long) (citizen.getTokens() + driver.getFutureRide().getPrice()/users.size()));
                }
            }

            cs.unblockAllCitizensByUsernames(driver.getFutureRide().getCitizens());

            driver.getFutureRide().setStatus("REJECT");

            driver.getFutureRide().setComment(message);

            driver.getRides().add(driver.getFutureRide());

            driver.setFutureRide(null);

            //VEHICLE SET TO FREE
            driver.getVehicle().setBusy(false);

            vr.save(driver.getVehicle());

        }

        dr.save(driver);

        return true;
    }

    public boolean makeFakeRide(String username , Location startLocation , Location endLocation) {

        System.out.println(startLocation + ":::::" + endLocation);

        Driver driver = dr.findByUsername(username);

        if(driver == null){
            return false;
        }

        if(driver.getCurrentRide() == null){
            Ride fakeRide = new Ride();

            fakeRide.setStatus("FAKE");

            List<Route> routes = new ArrayList<Route>();

            Route route = new Route();

            route.setRouteIndex(0l);

            route.setRide(fakeRide);

            List<Location> locations = findFakeRoute(startLocation , endLocation);
            //1.

//            locations.add(Location.builder().longitude(11.1111).latitude(11.1111).build());
//            locations.add(Location.builder().longitude(33.3333).latitude(33.3333).build());
//            locations.add(Location.builder().longitude(55.5555).latitude(55.5555).build());
//            locations.add(Location.builder().longitude(77.7777).latitude(77.7777).build());
//            locations.add(Location.builder().longitude(99.9999).latitude(99.9999).build());

            //LAZIRAJ OVO OVDE...napravi listo svojih lokacija...da ne trosis api.
            //2.
//            locations = ls.getLocationsForWaypointsMapQuest(startLocation,endLocation);

            route.setLocations(locations);

            routes.add(route);

            fakeRide.setRoutes(routes);

            driver.setCurrentRide(fakeRide);

            lr.saveAll(locations);

            routeRepository.save(route);

            rr.save(fakeRide);

            dr.save(driver);

        }

        return true;

    }

    public List<Location> findFakeRoute(Location startLocation , Location endLocation){

        List<Location> locations = new ArrayList<>();

        int counter = 1;

        while(counter <= 10){

            Location location = new Location();

            if(startLocation.getLongitude()>endLocation.getLongitude()){

                double percent = counter * 10.0; // Calculate the percentage based on the counter (e.g., 10% for counter = 1)

                double longitude = startLocation.getLongitude() - ( (startLocation.getLongitude() - endLocation.getLongitude())/ 100 * percent);

                location.setLongitude(longitude);

            }else{

                double percent = counter * 10.0; // Calculate the percentage based on the counter (e.g., 10% for counter = 1)

                double longitude = startLocation.getLongitude() + (endLocation.getLongitude() - startLocation.getLongitude())/ 100 * percent;

                location.setLongitude(longitude);

            }

            if(startLocation.getLatitude()>endLocation.getLatitude()){

                double percent = counter * 10.0; // Calculate the percentage based on the counter (e.g., 10% for counter = 1)

                double latitude = startLocation.getLatitude() - (startLocation.getLatitude() - endLocation.getLatitude()) / 100* percent;

                location.setLatitude(latitude);

            }else{

                double percent = counter * 10.0; // Calculate the percentage based on the counter (e.g., 10% for counter = 1)

                double latitude = startLocation.getLatitude() + (endLocation.getLatitude() - startLocation.getLatitude() )/ 100 * percent;

                location.setLatitude(latitude);

            }

            locations.add(location);

            counter+=1;

        }

        return locations;

    }


    public RideNotificationDTO newRide(String username) {

        Optional<Ride> rideOptional = rr.findAll().stream().filter(r-> r.getDriver().getUsername().equals(username) && r.getStatus().equals("PAID")).findFirst();

        if(rideOptional.isPresent()){

            Ride ride = rideOptional.get();

            return RideNotificationDTO.builder()
                    .text("NOVA VOZNJA : " + ride.getName())
                    .price(ride.getPrice())
                    .id(ride.getId())
                    .build();
        }

        return RideNotificationDTO.builder().build();
    }
}
