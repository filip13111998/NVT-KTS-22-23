package ftn.uns.ac.rs.NVTKTS20222023.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CitizenProfileDTO {

    private String username; //e-mail

    private String password;

    private String firstName;

    private String lastName;

    private String city;

    private String phone;

    private Long tokens;

    private String comment;

}
