package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.MarkerDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RideSaveDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RoutePartDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.MarkRideDetailViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideHistoryDetailViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideHistoryTableViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.*;
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

    public List<RideHistoryTableViewDTO> sortByName(String username) {

        List<Ride> rides = rideSorter.sortByName(username);

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

    public List<RideHistoryTableViewDTO> sortByStartDate(String username) {

        List<Ride> rides = rideSorter.sortByStartDate(username);

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

    public List<RideHistoryTableViewDTO> sortByEndDate(String username) {

        List<Ride> rides = rideSorter.sortByEndDate(username);

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

    public List<RideHistoryTableViewDTO> sortByPrice(String username) {

        List<Ride> rides = rideSorter.sortByPrice(username);

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

    public RideHistoryDetailViewDTO getRide(Long id) {
        Optional<Ride> rideOptional = rr.findById(id);

        Ride ride = rideOptional.get();

        if(ride == null){
            System.out.println("USO BABAB");
            return null;
        }
        System.out.println("ODJE");

        List<RoutePartDTO> routePartInterface = new ArrayList<>();

        for(Route route : ride.getRoutes()){
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
