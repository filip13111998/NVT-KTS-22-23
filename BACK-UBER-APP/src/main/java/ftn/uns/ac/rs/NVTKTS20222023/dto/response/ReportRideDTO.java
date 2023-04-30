package ftn.uns.ac.rs.NVTKTS20222023.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReportRideDTO {

    List<DayDTO> dayDTOList = new ArrayList<>();

    private long sumData;

    private long averageData;

}
