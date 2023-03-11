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

}
