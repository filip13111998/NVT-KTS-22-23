package ftn.uns.ac.rs.NVTKTS20222023.dto.response;

import ftn.uns.ac.rs.NVTKTS20222023.model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VehicleMapViewDTO {

    private Long id;

    private String name;

    private String type;

    private boolean petFriendly;

    private boolean babyFriendly;

    private boolean occupied;

    private Location location;

}
