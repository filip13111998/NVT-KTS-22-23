package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.MarkerDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RideSaveDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RoutePartDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.*;
import ftn.uns.ac.rs.NVTKTS20222023.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.*;
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


    public Boolean saveRide(RideSaveDTO rideSaveDTO) {

        Ride ride = new Ride();
        ride.setPetFriendly(rideSaveDTO.isPets());
        ride.setBabyFriendly(rideSaveDTO.isBaby());
        ride.setType(rideSaveDTO.getCar_type());
        ride.setStatus("CREATE");
        ride.setMeters(rideSaveDTO.getDistance());
        ride.setName(rideSaveDTO.getName());
        ride.setPrice(rideSaveDTO.getPrice());

        ride = this.setStartDate(ride , rideSaveDTO.getMinutes());


//        ride.setEndDate(new Date().getTime());

        List<Citizen> citizenList = rideSaveDTO.getUsers().stream()
                .map(c -> cr.findByUsername(c))
                .filter(c->c!=null)
                .filter(c->c.isBlock() == false)
                .collect(Collectors.toList());
        System.out.println(citizenList.size());

        if(citizenList.size() != rideSaveDTO.getUsers().size()){
            return false;
        }

        ride.setCitizens(citizenList);

        List<Route> routes = new ArrayList<>();

        for (RoutePartDTO routePartDto : rideSaveDTO.getRoutePartInterface()) {
            Route route = new Route();
            List<Location> locations = new ArrayList<>();
            route.setRouteIndex(Long.parseLong(routePartDto.getId()+""));
            route.setLocations(locations);
            for (MarkerDTO markerDto : routePartDto.getCoordinates()) {
                Location location = new Location();
                location.setLatitude(markerDto.getLatitude());
                location.setLongitude(markerDto.getLongitude());
                locations.add(location);
            }
            lr.saveAll(locations);
            route.setRide(ride);
            routes.add(route);
        }

        ride.setRoutes(routes);

        ride = this.setEndDate(ride , rideSaveDTO.getMinutes());

        routeRepository.saveAll(routes);

        if(rideSaveDTO.getUsers().size() != 1){
            //send notifikactions to citizens
            ride.setPaid(rideSaveDTO.getUsers().get(0));
            rr.save(ride);
            return true;
        }

        this.findDriver(ride.getId());

        rr.save(ride);

        return true;
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

        if(ride!= null){
            if(!ride.getPaid().contains(username)){
                ride.setPaid(ride.getPaid()+ "|" + username);
                if(ride.getPaid().split("\\|").length == ride.getCitizens().size()){
                    return findDriver(id);
                }
            }
        }

        return false;
    }

    public boolean findDriver(Long id) {

        Optional<Ride> r = rr.findById(id);

        Ride ride = r.get();

        if((ride.getStart()-(new Date()).getTime()) >15*60*1000){
            return false;
        }

        List<Driver> drivers = ds.findAllActiveDrivers().stream()
                .filter(
                        driver ->
                                driver.getVehicle().isBabyFriendly() == ride.isBabyFriendly() &&
                                driver.getVehicle().isPetFriendly() == ride.isPetFriendly() &&
                                driver.getVehicle().getType() == ride.getType()
                )
                .collect(Collectors.toList());

        if(drivers.size() == 0){
            ride.setStatus("REJCET");
            rr.save(ride);
            return false;
        }
        if(this.isAllDriversBusy(drivers)){
            ride.setStatus("REJCET");
            rr.save(ride);
            return false;
        }
        List<Driver> currentDrivers = this.getAllCurentFreeDrivers(drivers);
        if(currentDrivers.size()>0){
            //izaberi najblizeg iz liste currentDrivers
            Driver closestDriver = drivers.stream()
                    .min(Comparator.comparingDouble(d -> ls.getDistance(d.getVehicle().getLocation() ,ride.getAllLocations().get(0) )))
                    .orElse(null);

            if(closestDriver != null){
                ride.setStatus("PAID");
                ride.setDriver(closestDriver);
                closestDriver.setFutureRide(ride);

                //make fake ride
                ds.makeFakeRide(closestDriver.getUsername(),closestDriver.getVehicle().getLocation(), ride.getAllLocations().get(ride.getAllLocations().size()-1));

                rr.save(ride);
                dr.save(closestDriver);

                payRide(ride.getId(),closestDriver.getId());

                return true;
            }

        }
        List<Driver> futureDrivers = this.getAllFutureFreeDrivers(drivers);
        if(futureDrivers.size()>0){
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

                return true;
            }

        }

        return false;
    }

    public boolean payRide(Long id, Long driverId){

        Optional<Ride> r = rr.findById(id);

        Ride ride = r.get();

        List<Citizen> citizens = ride.getCitizens().stream()
                                            .filter(c-> c.getTokens()>ride.getPrice()/ride.getCitizens().size())
                                            .collect(Collectors.toList());
        if(citizens.size()<ride.getCitizens().size()){
            ride.setStatus("REJECT");
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

        ride.getCitizens().stream().forEach(c-> {
            c.setTokens((long) (c.getTokens() - ride.getPrice()/ride.getCitizens().size()));
        });

        cr.saveAll(ride.getCitizens());

        return true;
    }

    public boolean isAllDriversBusy(List<Driver> drivers){
        List<Driver> freeDrivers = drivers.stream().filter(d->d.getFutureRide() != null).collect(Collectors.toList());
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


    public boolean acceptRide(String username, String rideid) {

        Optional<Ride> r = rr.findById(Long.parseLong(rideid));

        Ride ride = r.get();

        if(ride != null){
            if(ride.getPaid().contains(username)){
                return false;
            }
            ride.setPaid(ride.getPaid() + "|" + username);

            if(ride.getPaid().split("\\|").length == ride.getCitizens().size()){
                this.findDriver(ride.getId());
                return true;
            }

        }

        return false;
    }

    public boolean inCitizenList(List<Citizen> citizens,String username){

        Citizen citizen = citizens.stream().filter(c->c.getUsername().equals(username)).findFirst().get();

        if(citizen != null){
            return true;
        }
        return false;
    }

    public Ride citizenNewRide(String username) {

        Ride ride = rr.findAll().stream()
                .filter(r-> r.getStatus().equals("CREATE")
                        && r.getPaid().contains(username)
                        && !inCitizenList(r.getCitizens() , username)
                )
                .findFirst().get();

        return ride;
    }
}
