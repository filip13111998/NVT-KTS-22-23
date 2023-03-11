package ftn.uns.ac.rs.NVTKTS20222023.model;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Citizen citizen;

    @ManyToOne
    private Ride ride;

    private String comment;

}
