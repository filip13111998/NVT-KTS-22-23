package ftn.uns.ac.rs.NVTKTS20222023.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    private Location start;
//
//    @OneToOne
//    private Location end;

    @OneToMany
    private List<Location> locations = new ArrayList<>();

    private Long routeIndex;

    @ManyToOne
    private Ride ride;

    public Route() {
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Long getRouteIndex() {
        return routeIndex;
    }

    public void setRouteIndex(Long routeIndex) {
        this.routeIndex = routeIndex;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
