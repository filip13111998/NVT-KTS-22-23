package ftn.uns.ac.rs.NVTKTS20222023.controller;

import ftn.uns.ac.rs.NVTKTS20222023.dto.response.CitizenBlockDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.DriverBlockDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import ftn.uns.ac.rs.NVTKTS20222023.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/block",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class BlockController {

    @Autowired
    private BlockService bs;

    @GetMapping("/citizens")
    public ResponseEntity<List<CitizenBlockDTO>> findAllCitizens() {

        return ResponseEntity.ok(bs.findAllCitizens());

    }

    @GetMapping("/drivers")
    public ResponseEntity<List<DriverBlockDTO>> findAllDrivers() {

        return ResponseEntity.ok(bs.findAllDrivers());

    }

    @GetMapping("/citizen/{username}/{comment}")
    public ResponseEntity<Boolean> blockCitizen(@PathVariable("username") String username,
                                                @PathVariable("comment") String comment) {

        return ResponseEntity.ok(bs.blockCitizen(username,comment));

    }

    @GetMapping("/driver/{username}/{comment}")
    public ResponseEntity<Boolean> blockDriver(@PathVariable("username") String username,
                                               @PathVariable("comment") String comment) {

        return ResponseEntity.ok(bs.blockDriver(username,comment));

    }

}
