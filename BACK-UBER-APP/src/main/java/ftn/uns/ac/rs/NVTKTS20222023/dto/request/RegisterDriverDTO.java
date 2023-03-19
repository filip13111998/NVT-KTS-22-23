package ftn.uns.ac.rs.NVTKTS20222023.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterDriverDTO {
    private String username; //e-mail

    private String password;

    private String confirm;

    private String firstName;

    private String lastName;

    private String city;

    private String phone;

    private String name;

    private String type;

    private boolean petFriendly;

    private boolean babyFriendly;

//    private VehicleDTO vehicle;

}
