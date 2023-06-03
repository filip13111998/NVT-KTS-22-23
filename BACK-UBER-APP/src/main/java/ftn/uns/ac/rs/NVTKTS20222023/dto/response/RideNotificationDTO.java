package ftn.uns.ac.rs.NVTKTS20222023.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RideNotificationDTO {

    private Long id;

    private double price;

    private String text;

}
