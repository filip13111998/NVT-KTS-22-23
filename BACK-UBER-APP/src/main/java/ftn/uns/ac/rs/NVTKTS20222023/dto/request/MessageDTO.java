package ftn.uns.ac.rs.NVTKTS20222023.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MessageDTO {

    private String sender; //Ako salje citizen onda je citizen...ili ako salje admin onda je admin.

    private String receiver; //primalac

    private String message; //Tekst poruke

    private String channel;

    private Long date; //Datum kada je poruka poslata


}
