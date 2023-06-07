package ftn.uns.ac.rs.NVTKTS20222023.controller;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RideSaveDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideNotificationDTO;
import ftn.uns.ac.rs.NVTKTS20222023.service.LoginHistoryService;
import ftn.uns.ac.rs.NVTKTS20222023.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class RideController {

    @Autowired
    private RideService rs;

    @Autowired
    private LoginHistoryService lhs;

    @PostMapping("/ride/save")
    public ResponseEntity<Boolean> saveRide(@RequestBody RideSaveDTO rideSaveDTO) {
        // handle the request and return a response
        return ResponseEntity.ok(rs.saveRide(rideSaveDTO));
    }

//    @PostMapping("/ride/accept/{username}/{rideid}")
//    public ResponseEntity<?> paidRide(@PathVariable String username , @PathVariable String rideid) {
//        // handle the request and return a response
//        return ResponseEntity.ok(rs.acceptRide(username ,rideid ));
//    }

    @GetMapping("/ride/history/{username}")
    public ResponseEntity<Boolean> historyRide(@PathVariable String username ) {
        // handle the request and return a response
        return ResponseEntity.ok(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours(username));
    }

    @GetMapping("/ride/history/{username}/{id}")
    public ResponseEntity<Boolean> citizenAcceptRide(@PathVariable String username,@PathVariable Long id ) {
        // handle the request and return a response
        return ResponseEntity.ok(rs.citizenAcceptRide(username,id));
    }

    @GetMapping("/ride/new/{username}")
    public ResponseEntity<RideNotificationDTO> citizenNewRide(@PathVariable("username") String username ) {
        // handle the request and return a response
        return ResponseEntity.ok(rs.citizenNewRide(username));
    }



}
