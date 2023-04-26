package ftn.uns.ac.rs.NVTKTS20222023.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RideSaveDTO {
    private String name;
    private boolean pets;
    private boolean baby;
    private String car_type;
    private Long price;
    private Long distance;
    private List<String> users;
    private List<RoutePartDTO> routePartInterface;
    private boolean favorite;
//    private boolean future;
    private int minutes;
}
