package ftn.uns.ac.rs.NVTKTS20222023.dto.response;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RoutePartDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import ftn.uns.ac.rs.NVTKTS20222023.model.MarkRide;
import ftn.uns.ac.rs.NVTKTS20222023.model.Route;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RideHistoryDetailViewDTO {

    private Long id;

    private String name;

    private Long start;

    private Long end;

    private double price;

    private String driverfirstName;

    private String driverLastName;

    private List<MarkRideDetailViewDTO> marks = new ArrayList<MarkRideDetailViewDTO>();

    private List<RoutePartDTO> routes = new ArrayList<RoutePartDTO>();

    private List<String> citizens = new ArrayList<String>();

}
