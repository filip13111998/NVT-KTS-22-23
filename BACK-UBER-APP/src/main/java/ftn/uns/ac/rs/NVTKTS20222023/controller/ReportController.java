package ftn.uns.ac.rs.NVTKTS20222023.controller;


import ftn.uns.ac.rs.NVTKTS20222023.dto.response.ReportRideDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideHistoryTableViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.service.ReportService;
import ftn.uns.ac.rs.NVTKTS20222023.service.RideHistoryService;
import ftn.uns.ac.rs.NVTKTS20222023.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/report",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping("/number/{start}/{end}/{username}/{role}")
    public ResponseEntity<ReportRideDTO> rideNumber( @PathVariable String start, @PathVariable String end ,  @PathVariable String username ,  @PathVariable String role) {
        System.out.println("STIGOO");
        return ResponseEntity.ok(reportService.rideNumber(Long.parseLong(start) ,Long.parseLong(end)  , username , role ));

    }

    @GetMapping("/meters/{start}/{end}/{username}/{role}")
    public ResponseEntity<ReportRideDTO> rideMeters(@PathVariable String start, @PathVariable String end, @PathVariable String username ,  @PathVariable String role) {

        return ResponseEntity.ok(reportService.rideMeters(Long.parseLong(start) ,Long.parseLong(end), username , role));

    }

    @GetMapping("/price/{start}/{end}/{username}/{role}")
    public ResponseEntity<ReportRideDTO> ridePrice( @PathVariable String start, @PathVariable String end, @PathVariable String username ,  @PathVariable String role) {

        return ResponseEntity.ok(reportService.ridePrice(Long.parseLong(start) ,Long.parseLong(end) , username , role));

    }

}
