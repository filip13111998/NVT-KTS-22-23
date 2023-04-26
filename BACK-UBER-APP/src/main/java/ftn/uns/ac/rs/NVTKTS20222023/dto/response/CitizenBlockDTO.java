package ftn.uns.ac.rs.NVTKTS20222023.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CitizenBlockDTO {

    private String username;

    private String firstName;

    private String lastName;

    private String city;

    private String phone;

}
