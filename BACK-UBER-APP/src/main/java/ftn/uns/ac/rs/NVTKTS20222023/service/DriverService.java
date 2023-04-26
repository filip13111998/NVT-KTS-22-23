package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.response.VehicleMapViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.*;
import ftn.uns.ac.rs.NVTKTS20222023.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    public List<Driver> findAllActiveDrivers() {

        //GET ALL AVAILABLE DRIVERS WHICH IS ACTIVE AND NOT BLOCK
        List<Driver> availableDrivers = dr.findAll().stream().filter(d-> d.isActive() == true && d.isBlock() == false).collect(Collectors.toList());

        availableDrivers = availableDrivers.stream().filter(d->lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours(d.getUsername())).collect(Collectors.toList());

//        return availableDrivers;
        return availableDrivers;
    }




    public boolean activeDriversIncrementCounter() {

        List<Driver> drivers = this.findAllActiveDrivers();
        System.out.println("sizeee" + drivers.size());
        for(Driver driver : drivers) {
            //MZD nije current ride vec fake...proveriti booelan atribut if true onca current inace fejk vozis baki.
            Ride ride = driver.getCurrentRide();
            if(ride == null){
                continue;
            }

            if(driver.getCounter() == ride.getAllLocations().size()){

                if(ride.getStatus().equals("START") || ride.getStatus().equals("FAKE")){
                    if(ride.getStatus().equals("FAKE")){
                        driver.setCurrentRide(null);
                        driver.setCounter(0);
                    }
                    ride.setStatus("END");
                }

                continue;
            }

            Location location = ride.getAllLocations().get(driver.getCounter());

            driver.setCounter(driver.getCounter()+1);

            driver.getVehicle().setLocation(location);

            dr.save(driver);
        }

        return true;
    }

    public List<VehicleMapViewDTO> getAllVehicleMapViewDTO(){


        //GET ALL AVAILABLE DRIVERS WHICH IS ACTIVE AND NOT BLOCK
       List<Driver> availableDrivers = this.findAllActiveDrivers();

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

        if(driver.getFutureRide() == null || driver.getCurrentRide() != null){
            System.out.println("UDJO");
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

            startLocation = driver.getCurrentRide().getAllLocations().get(driver.getCurrentRide().getAllLocations().size()-1);

            driver.getCurrentRide().setStatus("FINISH");

            driver.getRides().add(driver.getCurrentRide());

            driver.setCurrentRide(null);

            driver.setCounter(0);

            dr.save(driver);
        }

        //OVDE IDE LOGIKA ZA AUTOMATSKO UCITAVANJE NOVE RUTE..KONKRETNO OVDE UCITAVAM FAKE RIDE I POSLE TOGA.
        if(driver.getFutureRide() != null){
            endLocation = driver.getFutureRide().getAllLocations().get(0);

            this.makeFakeRide(username , startLocation , endLocation);
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
                    citizen.setTokens((long) (citizen.getTokens() + driver.getFutureRide().getPrice()/users.size()));
                }
            }


            driver.getFutureRide().setStatus("REJECT");

            driver.getFutureRide().setComment(message);

            driver.getRides().add(driver.getFutureRide());

            driver.setFutureRide(null);


        }

        dr.save(driver);

        return true;
    }

    public boolean makeFakeRide(String username , Location startLocation , Location endLocation) {

        System.out.println("FAKE RIDE");
        System.out.println("KOORDINATES:::: " + startLocation.getLongitude() + "," + startLocation.getLatitude()
                + "----" + endLocation.getLongitude() + "," + endLocation.getLatitude());

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

            //1.
            List<Location> locations = new ArrayList<>();
            locations.add(Location.builder().longitude(11.1111).latitude(11.1111).build());
            locations.add(Location.builder().longitude(33.3333).latitude(33.3333).build());
            locations.add(Location.builder().longitude(55.5555).latitude(55.5555).build());
            locations.add(Location.builder().longitude(77.7777).latitude(77.7777).build());
            locations.add(Location.builder().longitude(99.9999).latitude(99.9999).build());

            //LAZIRAJ OVO OVDE...napravi listo svojih lokacija...da ne trosis api.
            //2.
//        route.setLocations(ls.getLocationsForWaypoints(startLocation,endLocation));

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


}
