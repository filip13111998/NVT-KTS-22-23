package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.MarkerDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RideSaveDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RoutePartDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.MarkRideDetailViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideHistoryDetailViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideHistoryTableViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.*;
import ftn.uns.ac.rs.NVTKTS20222023.repository.CitizenRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.DriverRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.RideRepository;
import ftn.uns.ac.rs.NVTKTS20222023.sorter.RideSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RideHistoryService {

    @Autowired
    private RideSorter rideSorter;

    @Autowired
    private RideRepository rr;

    @Autowired
    private RideService rideService;

    @Autowired
    private CitizenRepository cr;

//    @Autowired
//    private DriverRepository dr;

    //CITIZEN

    public List<RideHistoryTableViewDTO> sortByNameCitizen(String username) {

        List<Ride> rides = rideSorter.sortByNameCitizen(username);

        if(rides == null){
            return new ArrayList<>();
        }

        Citizen citizen = cr.findByUsername(username);

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .favorite(citizen.getFavorite().contains(r))
                                .build())
                .collect(Collectors.toList());

    }

    public List<RideHistoryTableViewDTO> sortByStartDateCitizen(String username) {

        List<Ride> rides = rideSorter.sortByStartDateCitizen(username);

        if(rides == null){
            return new ArrayList<>();
        }

        Citizen citizen = cr.findByUsername(username);

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .favorite(citizen.getFavorite().contains(r))
                                .build())
                .collect(Collectors.toList());

    }

    public List<RideHistoryTableViewDTO> sortByEndDateCitizen(String username) {

        List<Ride> rides = rideSorter.sortByEndDateCitizen(username);

        if(rides == null){
            return new ArrayList<>();
        }

        Citizen citizen = cr.findByUsername(username);

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .favorite(citizen.getFavorite().contains(r))
                                .build())
                .collect(Collectors.toList());

    }

    public List<RideHistoryTableViewDTO> sortByPriceCitizen(String username) {

        List<Ride> rides = rideSorter.sortByPriceCitizen(username);

        if(rides == null){
            return new ArrayList<>();
        }

        Citizen citizen = cr.findByUsername(username);

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .favorite(citizen.getFavorite().contains(r))
                                .build())
                .collect(Collectors.toList());

    }

    //DRIVER

    public List<RideHistoryTableViewDTO> sortByNameDriver(String username) {

        List<Ride> rides = rideSorter.sortByNameDriver(username);

        if(rides == null){
            return new ArrayList<>();
        }

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .build())
                .collect(Collectors.toList());

    }

    public List<RideHistoryTableViewDTO> sortByStartDateDriver(String username) {

        List<Ride> rides = rideSorter.sortByStartDateDriver(username);

        if(rides == null){
            return new ArrayList<>();
        }

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .build())
                .collect(Collectors.toList());

    }

    public List<RideHistoryTableViewDTO> sortByEndDateDriver(String username) {

        List<Ride> rides = rideSorter.sortByEndDateDriver(username);

        if(rides == null){
            return new ArrayList<>();
        }

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .build())
                .collect(Collectors.toList());

    }

    public List<RideHistoryTableViewDTO> sortByPriceDriver(String username) {

        List<Ride> rides = rideSorter.sortByPriceDriver(username);

        if(rides == null){
            return new ArrayList<>();
        }

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .build())
                .collect(Collectors.toList());

    }

    //ADMIN
    public List<RideHistoryTableViewDTO> sortByNameCitizenAdmin() {

        List<Ride> rides = rr.findAll();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getName().compareTo(r2.getName());
            }
        });

        if(rides == null){
            return new ArrayList<>();
        }

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .user(r.getCitizens().stream().map(Citizen::getUsername).collect(Collectors.toList()).toString())
                                .build())
                .collect(Collectors.toList());

    }

    public List<RideHistoryTableViewDTO> sortByStartDateCitizenAdmin( ) {

        List<Ride> rides = rr.findAll();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getStart().compareTo(r2.getStart());
            }
        });

        if(rides == null){
            return new ArrayList<>();
        }

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .user(r.getCitizens().stream().map(Citizen::getUsername).collect(Collectors.toList()).toString())
                                .build())
                .collect(Collectors.toList());

    }

    public List<RideHistoryTableViewDTO> sortByEndDateCitizenAdmin( ) {

        List<Ride> rides = rr.findAll();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getEndDate().compareTo(r2.getEndDate());
            }
        });

        if(rides == null){
            return new ArrayList<>();
        }

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .user(r.getCitizens().stream().map(Citizen::getUsername).collect(Collectors.toList()).toString())
                                .build())
                .collect(Collectors.toList());

    }

    public List<RideHistoryTableViewDTO> sortByPriceCitizenAdmin( ) {

        List<Ride> rides = rr.findAll();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getMeters().compareTo(r2.getMeters());
            }
        });

        if(rides == null){
            return new ArrayList<>();
        }

       return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .user(r.getCitizens().stream().map(Citizen::getUsername).collect(Collectors.toList()).toString())
                                .build())
                .collect(Collectors.toList());

    }

    public List<RideHistoryTableViewDTO> sortByNameDriverAdmin( ) {

        List<Ride> rides = rr.findAll();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getName().compareTo(r2.getName());
            }
        });

        if(rides == null){
            return new ArrayList<>();
        }

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .user(r.getDriver().getUsername())
                                .build())
                .collect(Collectors.toList());

    }

    public List<RideHistoryTableViewDTO> sortByStartDateDriverAdmin( ) {

        List<Ride> rides = rr.findAll();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getStart().compareTo(r2.getStart());
            }
        });

        if(rides == null){
            return new ArrayList<>();
        }

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .user(r.getDriver().getUsername())
                                .build())
                .collect(Collectors.toList());

    }

    public List<RideHistoryTableViewDTO> sortByEndDateDriverAdmin( ) {

        List<Ride> rides = rr.findAll();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getEndDate().compareTo(r2.getEndDate());
            }
        });

        if(rides == null){
            return new ArrayList<>();
        }

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .user(r.getDriver().getUsername())
                                .build())
                .collect(Collectors.toList());

    }

    public List<RideHistoryTableViewDTO> sortByPriceDriverAdmin( ) {

        List<Ride> rides = rr.findAll();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getMeters().compareTo(r2.getMeters());
            }
        });

        if(rides == null){
            return new ArrayList<>();
        }

        return rides.stream()
                .map(r->
                        RideHistoryTableViewDTO.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .price(r.getPrice())
                                .start(r.getStart())
                                .end(r.getEndDate())
                                .user(r.getDriver().getUsername())
                                .build())
                .collect(Collectors.toList());

    }


    public RideHistoryDetailViewDTO getRide(Long id) {
        Optional<Ride> rideOptional = rr.findById(id);

        Ride ride = rideOptional.get();

        if(ride == null){
            return null;
        }

        List<RoutePartDTO> routePartInterface = new ArrayList<>();

        for(Route route : ride.getRoutes()){
            List<MarkerDTO> locations = new ArrayList<>();
            RoutePartDTO rpdto = new RoutePartDTO();
            rpdto.setId(route.getRouteIndex());

            MarkerDTO markerStartDTO = MarkerDTO.builder()
                    .latitude(route.getLocations().get(0).getLatitude())
                    .longitude(route.getLocations().get(0).getLongitude())
                    .build();
            locations.add(markerStartDTO);

            MarkerDTO markerEndDTO = MarkerDTO.builder()
                    .latitude(route.getLocations().get(route.getLocations().size()-1).getLatitude())
                    .longitude(route.getLocations().get(route.getLocations().size()-1).getLongitude())
                    .build();
            locations.add(markerEndDTO);
//            for(Location location : route.getLocations()){
//                MarkerDTO markerDTO = MarkerDTO.builder()
//                        .latitude(location.getLatitude())
//                        .longitude(location.getLongitude())
//                        .build();
//                locations.add(markerDTO);
//            }
            rpdto.setCoordinates(locations);
            routePartInterface.add(rpdto);

        }

        List<MarkRideDetailViewDTO> markRideDetailViewDTOS = new ArrayList<>();

        for(MarkRide markRide : ride.getMarks()){

            markRideDetailViewDTOS.add(MarkRideDetailViewDTO.builder().mark(markRide.getMark()).username(markRide.getCitizen().getUsername()).build());

        }

        return RideHistoryDetailViewDTO.builder()
                .id(ride.getId())
                .citizens(ride.getCitizens().stream().map(Citizen::getUsername).collect(Collectors.toList()))
                .routes(routePartInterface)
                .driverfirstName(ride.getDriver().getFirstName())
                .driverLastName(ride.getDriver().getLastName())
                .marks(markRideDetailViewDTOS)
                .start(ride.getStart())
                .end(ride.getEndDate())
                .name(ride.getName())
                .price(ride.getPrice())
                .build();
//        return null;
    }

    public boolean cloneRide(Long id, int minutes) {


        Optional<Ride> rideOptional = rr.findById(id);

        Ride rideCopy = rideOptional.get();

        if(rideCopy == null){
            System.out.println("GADNO");
            return false;
        }

        List<RoutePartDTO> routePartInterface = new ArrayList<>();

        for(Route route : rideCopy.getRoutes()){
            List<MarkerDTO> locations = new ArrayList<>();
            RoutePartDTO rpdto = new RoutePartDTO();
            rpdto.setId(route.getRouteIndex());
            for(Location location : route.getLocations()){
                MarkerDTO markerDTO = MarkerDTO.builder()
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude())
                        .build();
                locations.add(markerDTO);
            }
            rpdto.setCoordinates(locations);
            routePartInterface.add(rpdto);

        }

        RideSaveDTO rsdto = RideSaveDTO.builder()
                .name(rideCopy.getName())
                .pets(rideCopy.isPetFriendly())
                .baby(rideCopy.isBabyFriendly())
                .car_type(rideCopy.getType())
                .price((long) rideCopy.getPrice())
                .distance(rideCopy.getMeters())
                .users(Arrays.asList(rideCopy.getPaid().split("\\|")))
                .routePartInterface(routePartInterface)
                .favorite(false)
                .minutes(minutes)
                .build();

//        rr.save(ride);

        return rideService.saveRide(rsdto);
    }
}
