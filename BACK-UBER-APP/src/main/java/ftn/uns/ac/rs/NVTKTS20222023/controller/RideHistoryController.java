package ftn.uns.ac.rs.NVTKTS20222023.controller;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.MarkDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideHistoryDetailViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideHistoryTableViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.service.RideHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/history",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class RideHistoryController {

    @Autowired
    private RideHistoryService rhs;

    @GetMapping("/sort/name/{username}")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByName(@PathVariable String username) {

        return ResponseEntity.ok(rhs.sortByName(username));

    }

    @GetMapping("/sort/start/{username}")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByStartDate(@PathVariable String username ) {

        return ResponseEntity.ok(rhs.sortByStartDate(username));

    }

    @GetMapping("/sort/end/{username}")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByEndDate(@PathVariable String username) {

        return ResponseEntity.ok(rhs.sortByEndDate(username));

    }

    @GetMapping("/sort/price/{username}")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByPrice(@PathVariable String username) {

        return ResponseEntity.ok(rhs.sortByPrice(username));

    }

    @GetMapping("/sort/get/{id}")
    public ResponseEntity<RideHistoryDetailViewDTO> getRide(@PathVariable String id) {

        return ResponseEntity.ok(rhs.getRide(Long.parseLong(id)));

    }

    @GetMapping("/sort/clone/{id}/{minutes}")
    public ResponseEntity<Boolean> cloneRide(@PathVariable Long id , @PathVariable int minutes) {

        return ResponseEntity.ok(rhs.cloneRide(id,minutes));

    }



}
