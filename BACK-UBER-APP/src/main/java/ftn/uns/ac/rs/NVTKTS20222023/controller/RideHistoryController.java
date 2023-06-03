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

    @GetMapping("/sort/citizen/name/{username}")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByNameCitizen(@PathVariable String username) {

        return ResponseEntity.ok(rhs.sortByNameCitizen(username));

    }

    @GetMapping("/sort/citizen/start/{username}")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByStartDateCitizen(@PathVariable String username ) {

        return ResponseEntity.ok(rhs.sortByStartDateCitizen(username));

    }

    @GetMapping("/sort/citizen/end/{username}")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByEndDateCitizen(@PathVariable String username) {

        return ResponseEntity.ok(rhs.sortByEndDateCitizen(username));

    }

    @GetMapping("/sort/citizen/price/{username}")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByPriceCitizen(@PathVariable String username) {

        return ResponseEntity.ok(rhs.sortByPriceCitizen(username));

    }

    @GetMapping("/sort/driver/name/{username}")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByNameDriver(@PathVariable String username) {

        return ResponseEntity.ok(rhs.sortByNameDriver(username));

    }

    @GetMapping("/sort/driver/start/{username}")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByStartDateDriver(@PathVariable String username ) {

        return ResponseEntity.ok(rhs.sortByStartDateDriver(username));

    }

    @GetMapping("/sort/driver/end/{username}")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByEndDateDriver(@PathVariable String username) {

        return ResponseEntity.ok(rhs.sortByEndDateDriver(username));

    }

    @GetMapping("/sort/driver/price/{username}")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByPriceDriver(@PathVariable String username) {

        return ResponseEntity.ok(rhs.sortByPriceDriver(username));

    }

    //ADMIN SORT

    @GetMapping("/sort/citizen/admin/name")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByNameCitizenAdmin() {

        return ResponseEntity.ok(rhs.sortByNameCitizenAdmin());

    }

    @GetMapping("/sort/citizen/admin/start")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByStartDateCitizen() {

        return ResponseEntity.ok(rhs.sortByStartDateCitizenAdmin());

    }

    @GetMapping("/sort/citizen/admin/end")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByEndDateCitizen() {

        return ResponseEntity.ok(rhs.sortByEndDateCitizenAdmin());

    }

    @GetMapping("/sort/citizen/admin/price")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByPriceCitizenAdmin( ) {

        return ResponseEntity.ok(rhs.sortByPriceCitizenAdmin());

    }

    @GetMapping("/sort/driver/admin/name")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByNameDriverAdmin() {

        return ResponseEntity.ok(rhs.sortByNameDriverAdmin());

    }

    @GetMapping("/sort/driver/admin/start")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByStartDateDriverAdmin() {

        return ResponseEntity.ok(rhs.sortByStartDateDriverAdmin());

    }

    @GetMapping("/sort/driver/admin/end")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByEndDateDriverAdmin() {

        return ResponseEntity.ok(rhs.sortByEndDateDriverAdmin());

    }

    @GetMapping("/sort/driver/admin/price")
    public ResponseEntity<List<RideHistoryTableViewDTO>> sortByPriceDriverAdmin() {

        return ResponseEntity.ok(rhs.sortByPriceDriverAdmin());

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RideHistoryDetailViewDTO> getRide(@PathVariable String id) {

        return ResponseEntity.ok(rhs.getRide(Long.parseLong(id)));

    }

    @GetMapping("/sort/clone/{id}/{minutes}")
    public ResponseEntity<Boolean> cloneRide(@PathVariable Long id , @PathVariable int minutes) {

        return ResponseEntity.ok(rhs.cloneRide(id,minutes));

    }



}
