package ftn.uns.ac.rs.NVTKTS20222023.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DriverBlockDTO {

    private String username;

    private String firstName;

    private String lastName;

    private String city;

    private String phone;

    private boolean block;

}
