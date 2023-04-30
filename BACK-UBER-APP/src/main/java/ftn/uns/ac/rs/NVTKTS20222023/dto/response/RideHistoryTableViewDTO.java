package ftn.uns.ac.rs.NVTKTS20222023.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RideHistoryTableViewDTO {

    private Long id;

    private String name;

    private Long start;

    private Long end;

    private double price;

}
