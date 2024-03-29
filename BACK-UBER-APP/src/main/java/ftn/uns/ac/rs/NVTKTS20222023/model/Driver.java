package ftn.uns.ac.rs.NVTKTS20222023.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "drivers")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Driver implements UserDetails {

    //ISTO KO I CITIZEN
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , nullable = false)
    private String username; //e-mail

    private String password;

    private String firstName;

    private String lastName;

    private String city;

    private String phone;

    private String image;

    private String comment;

    // RAZLICITO OD CITIZENA
    @OneToOne
    private Vehicle vehicle;

    private boolean active;

    //EDIT PROFILE : AKO JE edit false onda nista a ako je true onda admini vide to i prihvataju ili odbijaju edit

    private String repassword;

    private String refirstName;

    private String relastName;

    private String recity;

    private String rephone;

    private String reimage;

    private boolean edit;

    //BLOKIRANJE KORISNIKA
    private boolean block;  // ako je true onda je blokiran

    @OneToMany(mappedBy = "driver" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Ride> rides = new ArrayList<Ride>();

    @OneToOne
    private Ride currentRide;

    @OneToOne
    private Ride futureRide;


//    private List<Location> locations = new ArrayList<>();

    //Trenutni broj koordinate u ruti na kome ce biti vozilo
    private Integer counter;


    @OneToMany(mappedBy = "driver" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<MarkDriver> marks = new ArrayList<MarkDriver>();


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_DRIVER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Ride getCurrentRide() {
        return currentRide;
    }

    public void setCurrentRide(Ride currentRide) {
        this.currentRide = currentRide;
    }

    public Ride getFutureRide() {
        return futureRide;
    }

    public void setFutureRide(Ride futureRide) {
        this.futureRide = futureRide;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getRefirstName() {
        return refirstName;
    }

    public void setRefirstName(String refirstName) {
        this.refirstName = refirstName;
    }

    public String getRelastName() {
        return relastName;
    }

    public void setRelastName(String relastName) {
        this.relastName = relastName;
    }

    public String getRecity() {
        return recity;
    }

    public void setRecity(String recity) {
        this.recity = recity;
    }

    public String getRephone() {
        return rephone;
    }

    public void setRephone(String rephone) {
        this.rephone = rephone;
    }

    public String getReimage() {
        return reimage;
    }

    public void setReimage(String reimage) {
        this.reimage = reimage;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public List<MarkDriver> getMarks() {
        return marks;
    }

    public void setMarks(List<MarkDriver> marks) {
        this.marks = marks;
    }


}
