package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.MarkerDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RideSaveDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RoutePartDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideNotificationDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.*;
import ftn.uns.ac.rs.NVTKTS20222023.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

@Service
@Transactional
public class RideService {

    @Autowired
    private CitizenRepository cr;

    @Autowired
    private RideRepository rr;

    @Autowired
    private DriverService ds;

    @Autowired
    private LocationService ls;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private LocationRepository lr;

    @Autowired
    private DriverRepository dr;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private CitizenService cs;

    public Boolean saveRide(RideSaveDTO rideSaveDTO) {

        Ride Ride = createBasicRide(rideSaveDTO);

        boolean answer =  addUsersToRide(Ride.getId() , rideSaveDTO.getUsers());

        if(!answer){
            return answer;
        }

        answer = addRouteToRide(Ride.getId() , rideSaveDTO.getRoutePartInterface());

        return answer;
    }

    public Ride createBasicRide(RideSaveDTO rideSaveDTO){

        Ride ride = new Ride();

        ride.setPetFriendly(rideSaveDTO.isPets());

        ride.setBabyFriendly(rideSaveDTO.isBaby());

        ride.setType(rideSaveDTO.getCar_type());

        ride.setStatus("CREATE");

        ride.setMeters(rideSaveDTO.getDistance());

        ride.setName(rideSaveDTO.getName());

        ride.setPrice(rideSaveDTO.getPrice());

        ride.setPaid("");

        ride = this.setStartDate(ride , rideSaveDTO.getMinutes());

        ride = this.setEndDate(ride , rideSaveDTO.getMinutes());

        rr.save(ride);

        return ride;
    }

    public boolean addUsersToRide(Long id , List<String>users){

        Optional<Ride> rideOptional = rr.findById(id);

        if(rideOptional.isPresent()){

            Ride ride = rideOptional.get();

            List<Citizen> citizenList = users.stream()
                .map(c -> cr.findByUsername(c))
                .filter(c->c!=null)
                .filter(c->c.isBlock() == false)
                .collect(Collectors.toList());

            if(citizenList.size() != users.size()){

                rr.delete(ride);

                return false;

            }

            ride.setCitizens(citizenList);

            List<Citizen> citizensToUpdate = new ArrayList<>();

            for (Citizen citizen : citizenList) {

                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.execute(() ->
                        template.convertAndSend("/topic/citizen/notification/" + citizen.getUsername().split("\\@")[0],
                                RideNotificationDTO.builder()
                                        .text("NOVA VOZNJA : " + ride.getName())
                                        .price(ride.getPrice()/citizenList.size())
                                        .id(ride.getId())
                                        .build())
                );

                citizen.getRides().add(ride);

                citizensToUpdate.add(citizen);

            }

            cr.saveAll(citizensToUpdate);

            rr.save(ride);

            cs.blockAllCitizensByUsernames(citizensToUpdate);

            return true;

        }

        return false;

    }


    public boolean addRouteToRide(Long id , List<RoutePartDTO> parts){

        Optional<Ride> rideOptional = rr.findById(id);

        if(rideOptional.isPresent()) {

            Ride ride = rideOptional.get();

            List<Route> routes = new ArrayList<>();

            for (RoutePartDTO routePartDto : parts) {

                Route route = new Route();

                List<Location> locations = new ArrayList<>();

                route.setRouteIndex(Long.parseLong(routePartDto.getId() + ""));

                for (MarkerDTO markerDto : routePartDto.getCoordinates()) {

                    Location location = new Location();

                    location.setLatitude(markerDto.getLatitude());

                    location.setLongitude(markerDto.getLongitude());

                    locations.add(location);

                }

                route.setLocations(locations);

                lr.saveAll(locations);

                route.setRide(ride);

                routes.add(route);

            }

            ride.setRoutes(routes);

            routeRepository.saveAll(routes);

            rr.save(ride);

            return true;

        }

        return false;
    }

    public Ride setStartDate(Ride ride ,  int offset){
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.add(Calendar.MINUTE,  offset);
        ride.setStart(calendarStart.getTime().getTime());
        return ride;
    }

    public Ride setEndDate(Ride ride ,  int offset){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, offset + ride.getAllLocations().size());
        ride.setEndDate(calendar.getTime().getTime());
        return ride;
    }

    public boolean citizenAcceptRide(String username ,  Long id){

        Optional<Ride> r = rr.findById(id);

        Ride ride = r.get();

        if (ride == null) {
            return false;
        }

        if(ride.getPaid() == ""){
            ride.setPaid(username);
        }else{
            if(!ride.getPaid().contains(username)) {
                ride.setPaid(ride.getPaid() + "|" + username);
            }
        }

        if(ride.getPaid().split("\\|").length == ride.getCitizens().size()){
            return findDriver(id);
        }

        return false;
    }

    public boolean findDriver(Long id) {

        Optional<Ride> r = rr.findById(id);

        Ride ride = r.get();

        if(isRideExpired(ride)){

            return false;

        }

//        List<Driver> drivers = ds.findAllActiveDrivers().stream()
//                .filter(
//                        driver ->
//                                driver.getVehicle().isBabyFriendly() == ride.isBabyFriendly() &&
//                                driver.getVehicle().isPetFriendly() == ride.isPetFriendly() &&
//                                driver.getVehicle().getType() == ride.getType()
//                )
//                .collect(Collectors.toList());
        List<Driver> drivers = filterDriversForRide(ride);

        if(drivers.size() == 0){

            ride.setStatus("REJCET");

            rr.save(ride);

            cs.unblockAllCitizensByUsernames(ride.getCitizens());

            return false;

        }

        if(this.isAllDriversBusy(drivers)){

            ride.setStatus("REJCET");

            rr.save(ride);

            cs.unblockAllCitizensByUsernames(ride.getCitizens());

            return false;

        }

        List<Driver> currentDrivers = this.getAllCurentFreeDrivers(drivers);

        if(currentDrivers.size()>0){

            //izaberi najblizeg iz liste currentDrivers
            Driver closestDriver = drivers.stream()
                    .min(Comparator.comparingDouble(d -> ls.getDistance(d.getVehicle().getLocation() ,ride.getAllLocations().get(0) )))
                    .orElse(null);

            if(closestDriver != null){
//                assignDriverToRide(ride, closestDriver);
//                completeRide(ride, closestDriver);
                ride.setStatus("PAID");
//
                ride.setDriver(closestDriver);
//
                closestDriver.setFutureRide(ride);

                //make fake ride
                ds.makeFakeRide(closestDriver.getUsername(),closestDriver.getVehicle().getLocation(), ride.getAllLocations().get(0));

                rr.save(ride);

                dr.save(closestDriver);

                payRide(ride.getId(),closestDriver.getId());

                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.execute(() ->
                        template.convertAndSend("/topic/driver/notification/" + closestDriver.getUsername(),
                                RideNotificationDTO.builder()
                                        .text("NOVA VOZNJA : " + ride.getName())
                                        .price(ride.getPrice())
                                        .id(ride.getId())
                                        .build())
                );

                return true;
            }

        }
        List<Driver> futureDrivers = this.getAllFutureFreeDrivers(drivers);
        if(futureDrivers.size()>0){
            System.out.println("USO u FUTURE RIDE");
            //izaberi najblizeg iz liste futureDrivers
            Driver closestDriver = drivers.stream()
                    .min(Comparator.comparingInt(d -> d.getCurrentRide().getAllLocations().size()-d.getCounter()))
                    .orElse(null);
            if(closestDriver != null){

                ride.setStatus("PAID");
                ride.setDriver(closestDriver);
                closestDriver.setFutureRide(ride);

                rr.save(ride);
                dr.save(closestDriver);

                payRide(ride.getId(),closestDriver.getId());

                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.execute(() ->
                        template.convertAndSend("/topic/driver/notification/" + closestDriver.getUsername(),
                                RideNotificationDTO.builder()
                                        .text("NOVA VOZNJA : " + ride.getName())
                                        .price(ride.getPrice())
                                        .id(ride.getId())
                                        .build())
                );

                return true;
            }

        }

        return false;
    }


//    public void rejectRide(Ride ride) {
//        ride.setStatus("REJECT");
//        rr.save(ride);
//    }



    public boolean isRideExpired(Ride ride) {
        long timeDifference = ride.getStart() - (new Date()).getTime();
        return timeDifference > 15 * 60 * 1000;
    }

    public List<Driver> filterDriversForRide(Ride ride) {
        return ds.findAllActiveDrivers().stream()
                .filter(driver ->
                        driver.getVehicle().isBabyFriendly() == ride.isBabyFriendly() &&
                                driver.getVehicle().isPetFriendly() == ride.isPetFriendly() &&
                                driver.getVehicle().getType() == ride.getType()
                )
                .collect(Collectors.toList());
    }

    public boolean payRide(Long id, Long driverId){

        Optional<Ride> r = rr.findById(id);

        Ride ride = r.get();

        List<Citizen> citizens = ride.getCitizens().stream()
                                            .filter(c-> c.getTokens()>ride.getPrice()/ride.getCitizens().size())
                                            .collect(Collectors.toList());

        if(citizens.size()<ride.getCitizens().size()){

            ride.setStatus("REJECT");

            cs.unblockAllCitizensByUsernames(ride.getCitizens());

            rr.save(ride);

            Driver driver = dr.findById(driverId).orElse(null);

            if(driver == null){

                return false;

            }

            driver.setFutureRide(null);

            driver.getRides().add(ride);

            dr.save(driver);

            return false;
        }

        List<Citizen> citizensToUpdate = new ArrayList<>();

        for (Citizen citizen : ride.getCitizens()) {

            citizen.setTokens((long) (citizen.getTokens() - ride.getPrice()/ride.getCitizens().size()));

            citizensToUpdate.add(citizen);

        }

        cr.saveAll(citizensToUpdate);

        return true;
    }

    public boolean isAllDriversBusy(List<Driver> drivers){

        List<Driver> freeDrivers = drivers.stream().filter(d->d.getFutureRide() == null).collect(Collectors.toList());

        if(freeDrivers.size() == 0){

            return true;

        }

        return false;

    }

    public List<Driver> getAllCurentFreeDrivers(List<Driver> drivers){

        return drivers.stream().filter(d->d.getCurrentRide() == null).collect(Collectors.toList());

    }

    public List<Driver> getAllFutureFreeDrivers(List<Driver> drivers){

        return drivers.stream().filter(d->d.getFutureRide() == null).collect(Collectors.toList());

    }


//    public boolean acceptRide(String username, String rideid) {
//
//        Optional<Ride> r = rr.findById(Long.parseLong(rideid));
//
//        Ride ride = r.get();
//
//        if(ride != null){
//            if(ride.getPaid().contains(username)){
//                return false;
//            }
//            ride.setPaid(ride.getPaid() + "|" + username);
//
//            if(ride.getPaid().split("\\|").length == ride.getCitizens().size()){
//                this.findDriver(ride.getId());
//                return true;
//            }
//
//        }
//
//        return false;
//    }

    public boolean inCitizenList(List<Citizen> citizens,String username){

        Citizen citizen = citizens.stream().filter(c->c.getUsername().equals(username)).findFirst().get();

        if(citizen != null){

            return true;

        }

        return false;

    }

    public RideNotificationDTO citizenNewRide(String username) {

        Optional<Ride> optionalRide = rr.findAll().stream()
                .filter(r-> r.getStatus().equals("CREATE")
                        && !r.getPaid().contains(username)
                        && inCitizenList(r.getCitizens() , username)
                )
                .findFirst();

        if(optionalRide.isPresent()){

            Ride ride = optionalRide.get();

            RideNotificationDTO notification = RideNotificationDTO.builder()
                    .text("NOVA VOZNJA : " + ride.getName())
                    .price(ride.getPrice()/ride.getCitizens().size())
                    .id(ride.getId())
                    .build();

            return notification;
        }

        return RideNotificationDTO.builder().build();

    }
}
