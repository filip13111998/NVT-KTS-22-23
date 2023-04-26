package ftn.uns.ac.rs.NVTKTS20222023.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FavoriteRideTableViewDTO {

    private Long id;

    private boolean petFriendly;

    private boolean babyFriendly;

    private String type;

    private Long meters;

    private String name;

    private double price;

}
