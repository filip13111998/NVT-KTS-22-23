package ftn.uns.ac.rs.NVTKTS20222023.dto.response;

import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import ftn.uns.ac.rs.NVTKTS20222023.model.Ride;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MarkRideDetailViewDTO {

    private String username;

    private Long mark;

}
