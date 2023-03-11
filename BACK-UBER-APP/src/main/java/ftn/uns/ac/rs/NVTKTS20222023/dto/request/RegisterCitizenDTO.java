package ftn.uns.ac.rs.NVTKTS20222023.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterCitizenDTO {
    private String username; //e-mail

    private String password;

    private String confirm;

    private String firstName;

    private String lastName;

    private String city;

    private String phone;
}
