package ftn.uns.ac.rs.NVTKTS20222023.controller;

import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideNotificationDTO;
import ftn.uns.ac.rs.NVTKTS20222023.service.AdminService;
import ftn.uns.ac.rs.NVTKTS20222023.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/driver",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class DriverController {

    @Autowired
    private DriverService ds;

    @GetMapping("/start/{username}")
    public ResponseEntity<Boolean> startRide(@PathVariable String username) {

        return ResponseEntity.ok(ds.startRide(username));

    }

    @GetMapping("/finish/{username}")
    public ResponseEntity<Boolean> finishRide(@PathVariable String username) {
        System.out.println("FINISH");
        return ResponseEntity.ok(ds.finishRide(username));

    }

    @GetMapping("/reject/{username}/{message}")
    public ResponseEntity<Boolean> rejectRide(@PathVariable String username , @PathVariable String message) {

        return ResponseEntity.ok(ds.rejectRide(username,message));

    }

    @GetMapping("/new/{username}")
    public ResponseEntity<RideNotificationDTO> newRide(@PathVariable String username) {

        return ResponseEntity.ok(ds.newRide(username));

    }

}
