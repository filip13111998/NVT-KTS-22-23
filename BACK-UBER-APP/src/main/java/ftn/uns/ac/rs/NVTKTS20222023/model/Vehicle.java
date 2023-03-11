package ftn.uns.ac.rs.NVTKTS20222023.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "vehicles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private boolean busy;

    private boolean petFriendly;

    private boolean babyFriendly;

    @OneToOne
    private Location location;



}
