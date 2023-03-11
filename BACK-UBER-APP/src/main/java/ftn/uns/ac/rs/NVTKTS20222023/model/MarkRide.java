package ftn.uns.ac.rs.NVTKTS20222023.model;

import javax.persistence.*;

@Entity
@Table(name = "marks_rides")
public class MarkRide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Citizen citizen;

    @ManyToOne
    private Ride ride;

//    @ManyToOne
//    private Driver driver;

    private Long mark;




}
