package ftn.uns.ac.rs.NVTKTS20222023.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "marks_rides")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
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
