package ftn.uns.ac.rs.NVTKTS20222023.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUpdateDTO {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    private String city;

//    private Long tokens;

//    private String image; // Image name + I need to sent and multipart file which is that picture.

}
