package ftn.uns.ac.rs.NVTKTS20222023.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MarkDTO {

    private String username;

    private Long rideId;

    private String rideComment;

    private Long rideMark;

    private Long driverMark;

}
