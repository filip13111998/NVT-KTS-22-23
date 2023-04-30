package ftn.uns.ac.rs.NVTKTS20222023.controller;


import ftn.uns.ac.rs.NVTKTS20222023.dto.response.ReportRideDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideHistoryTableViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.service.ReportService;
import ftn.uns.ac.rs.NVTKTS20222023.service.RideHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/report",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/number/{start}/{end}")
    public ResponseEntity<ReportRideDTO> rideNumber(@PathVariable String start, @PathVariable String end) {

        return ResponseEntity.ok(reportService.rideNumber(Long.parseLong(start) ,Long.parseLong(end)));

    }

    @GetMapping("/meters/{start}/{end}")
    public ResponseEntity<ReportRideDTO> rideMeters(@PathVariable String start, @PathVariable String end) {

        return ResponseEntity.ok(reportService.rideMeters(Long.parseLong(start) ,Long.parseLong(end)));

    }

    @GetMapping("/price/{start}/{end}")
    public ResponseEntity<ReportRideDTO> ridePrice(@PathVariable String start, @PathVariable String end) {

        return ResponseEntity.ok(reportService.ridePrice(Long.parseLong(start) ,Long.parseLong(end)));

    }

}
