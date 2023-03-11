package ftn.uns.ac.rs.NVTKTS20222023.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rides2")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //RIDE ADDITIONAL PARAMS
    private boolean petFriendly;

    private boolean babyFriendly;

    private String type; //VIECHLE TYPE

    //RIDE STATUS:  CREATE  /  PAID  /  START /  DRIVE   /   END  /  FINISH  / REJECT
    private String status;

    //KILOMETERS
    private Long meters; // Duzina rute

    //ROUTE
    private String name; //ROUTE NAME -> EXAMPLE: 'KIS ERNEA 40 - NOVOSADSKI SAJAM 50' ili 'ja dam neki naziv'

    //DATE WHEN IT WAS CREATED
    private Long start;
    //DATE WHEN IT WAS FINISHED
    private Long endDate;

    @OneToMany(mappedBy = "ride" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Route> routes = new ArrayList<Route>();

    //USERS
    @ManyToMany(mappedBy = "rides" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Citizen> citizens = new ArrayList<Citizen>();

    private String paid; //SPLIT FIRE USERNAME STRING. EXAMPLE : pera123@gmail.com|zika@hotmail.com...



    //IS FUTURE RIDE
    private boolean future;

    //PANIC TASTER. Bilo koji korisnik da pritisne panik ovaj flef se postavlja na true. admin moze kasnije
    //da dobavi sve voznje koje su true i da tako kazni vozaca.
    private boolean panic;

    //Komentar koji se ostavlja na REJECT voznje. Pre nego sto putnici udju u vozilo vozac odbija voznju i ostavlja komentar opciono.
    private String comment;

    //DRIVER
    @ManyToOne
    private Driver driver;

    //MARKS FOR RIDE
    @OneToMany(mappedBy = "ride",cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<MarkRide> marks = new ArrayList<MarkRide>();

    //MARKS FOR RIDE
    @OneToMany(mappedBy = "ride",cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<Comment>();

    public Ride(){

    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPetFriendly() {
        return petFriendly;
    }

    public void setPetFriendly(boolean petFriendly) {
        this.petFriendly = petFriendly;
    }

    public boolean isBabyFriendly() {
        return babyFriendly;
    }

    public void setBabyFriendly(boolean babyFriendly) {
        this.babyFriendly = babyFriendly;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMeters() {
        return meters;
    }

    public void setMeters(Long meters) {
        this.meters = meters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Citizen> getCitizens() {
        return citizens;
    }

    public void setCitizens(List<Citizen> citizens) {
        this.citizens = citizens;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public boolean isFuture() {
        return future;
    }

    public void setFuture(boolean future) {
        this.future = future;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<MarkRide> getMarks() {
        return marks;
    }

    public void setMarks(List<MarkRide> marks) {
        this.marks = marks;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
