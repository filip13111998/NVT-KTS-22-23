package ftn.uns.ac.rs.NVTKTS20222023.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "marks_drivers")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MarkDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Citizen citizen;

    @ManyToOne
    private Driver driver;

    private Long mark;

}
