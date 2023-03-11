package ftn.uns.ac.rs.NVTKTS20222023.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitizenUpdateDTO {
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    private String city;

    private Long tokens;
}
