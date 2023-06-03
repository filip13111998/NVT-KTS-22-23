package ftn.uns.ac.rs.NVTKTS20222023.sorter;

import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import ftn.uns.ac.rs.NVTKTS20222023.model.Driver;
import ftn.uns.ac.rs.NVTKTS20222023.model.Ride;
import ftn.uns.ac.rs.NVTKTS20222023.repository.AdminRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.CitizenRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.DriverRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RideSorter {

    @Autowired
    private CitizenRepository cr;

    @Autowired
    private DriverRepository dr;

    public List<Ride> sortByNameCitizen(String username){

        Citizen citizen = cr.findByUsername(username);

        if (citizen == null){
            return null;
        }

        List<Ride> rides = citizen.getRides();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getName().compareTo(r2.getName());
            }
        });
        return rides;
    }
    public List<Ride> sortByStartDateCitizen(String username){

        Citizen citizen = cr.findByUsername(username);

        if (citizen == null){
            return null;
        }

        List<Ride> rides = citizen.getRides();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getStart().compareTo(r2.getStart());
            }
        });
        return rides;
    }

    public List<Ride> sortByEndDateCitizen(String username){

        Citizen citizen = cr.findByUsername(username);

        if (citizen == null){
            return null;
        }

        List<Ride> rides = citizen.getRides();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getEndDate().compareTo(r2.getEndDate());
            }
        });
        return rides;
    }

    public List<Ride> sortByPriceCitizen(String username){

        Citizen citizen = cr.findByUsername(username);

        if (citizen == null){
            return null;
        }

        List<Ride> rides = citizen.getRides();

//        List<Ride> rides = cr.findAll().stream().filter(c->c.getUsername().equals(username)).findFirst().get().getRides();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getMeters().compareTo(r2.getMeters());
            }
        });
        return rides;
    }



    //DRIVER
    public List<Ride> sortByNameDriver(String username){

        Driver driver = dr.findByUsername(username);

        if (driver == null){
            return null;
        }

        List<Ride> rides = driver.getRides();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getName().compareTo(r2.getName());
            }
        });
        return rides;
    }
    public List<Ride> sortByStartDateDriver(String username){

        Driver driver = dr.findByUsername(username);

        if (driver == null){
            return null;
        }

        List<Ride> rides = driver.getRides();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getStart().compareTo(r2.getStart());
            }
        });
        return rides;
    }

    public List<Ride> sortByEndDateDriver(String username){

        Driver driver = dr.findByUsername(username);

        if (driver == null){
            return null;
        }

        List<Ride> rides = driver.getRides();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getEndDate().compareTo(r2.getEndDate());
            }
        });
        return rides;
    }

    public List<Ride> sortByPriceDriver(String username){

        Driver driver = dr.findByUsername(username);

        if (driver == null){
            return null;
        }

        List<Ride> rides = driver.getRides();

//        List<Ride> rides = cr.findAll().stream().filter(c->c.getUsername().equals(username)).findFirst().get().getRides();

        Collections.sort(rides, new Comparator<Ride>() {
            @Override
            public int compare(Ride r1, Ride r2) {
                return r1.getMeters().compareTo(r2.getMeters());
            }
        });
        return rides;
    }






}
