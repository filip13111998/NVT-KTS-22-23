package ftn.uns.ac.rs.NVTKTS20222023.sorter;

import ftn.uns.ac.rs.NVTKTS20222023.converter.DateConverter;
import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import ftn.uns.ac.rs.NVTKTS20222023.model.Ride;
import ftn.uns.ac.rs.NVTKTS20222023.repository.RideRepository;
import ftn.uns.ac.rs.NVTKTS20222023.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//Tacka 2.10 -> Koristim prilikom generisanja izvestaja po datumima...start date i end date su 2 datuma koja prosledjujem ka sort metodi
@Component
public class RideDateSorter {

    @Autowired
    private RideRepository rr;

    @Autowired
    private DateConverter dc;

    @Autowired
    private TokenUtils tokenUtils;

    //public static Map<LocalDate, List<Ride>> sortRidesByDayInterval(List<Ride> rides, long startDate, long endDate)
    public Map<LocalDate, List<Ride>> sortRidesByDayInterval(long startDate, long endDate , String username , String ROLE) {
        Map<LocalDate, List<Ride>> ridesByDay = new HashMap<>();

        // Convert the start and end dates from milliseconds to LocalDate
        LocalDate start = Instant.ofEpochMilli(startDate).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = Instant.ofEpochMilli(endDate).atZone(ZoneId.systemDefault()).toLocalDate();

        // Loop through each ride and add it to the corresponding day's list
        for (Ride ride : this.getAllRidesByUsername(username , ROLE)) {
            LocalDate rideDate = Instant.ofEpochMilli(ride.getStart()).atZone(ZoneId.systemDefault()).toLocalDate();

            if (rideDate.isAfter(end)) {
                break; // Stop processing rides if we've gone past the end date
            }
            if (rideDate.isEqual(start) || rideDate.isAfter(start)) {

//                Long startDt = dc.converDateToDayStart(rideDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
//                ridesByDay.computeIfAbsent(startDt, k -> new ArrayList<>()).add(ride);
                ridesByDay.computeIfAbsent(rideDate, k -> new ArrayList<>()).add(ride);
            }
        }

        return ridesByDay;
    }

    public List<Ride> getAllRidesByUsername(String username,String ROLE){
//        System.out.println(tokenUtils.getUsernameFromToken(token));
//        Claims claims = tokenUtils.getAllClaimsFromToken(token);
//        String role = claims.get("roles" , String.class);
//        String username = claims.get("sub" , String.class);
        if(ROLE.equals("ROLE_CITIZEN")){
            return this.getAllRidesByCitizenUsername(username);
        }
        if(ROLE.equals("ROLE_DRIVER")){
            System.out.println("DRIVER");
            return this.getAllRidesByDriverUsername(username);
        }
        System.out.println("ADMIN");
        return this.getAllRidesByAdminUsername();
    }

    public List<Ride> getAllRidesByCitizenUsername(String username){

        List<Ride> rides =  rr.findAll().stream().filter(r->r.getCitizens().stream().map(Citizen::getUsername)
                        .collect(Collectors.toList()).contains(username))
                .collect(Collectors.toList());

        return rides;

    }

    public List<Ride> getAllRidesByDriverUsername(String username){

        List<Ride> rides =  rr.findAll().stream().filter(r->r.getDriver().getUsername().equals(username))
                .collect(Collectors.toList());

        return rides;

    }

    public List<Ride> getAllRidesByAdminUsername(){
        return rr.findAll();
    }

}
