package ftn.uns.ac.rs.NVTKTS20222023.controller;

import ftn.uns.ac.rs.NVTKTS20222023.dto.response.CitizenBlockDTO;
import ftn.uns.ac.rs.NVTKTS20222023.service.BlockService;
import ftn.uns.ac.rs.NVTKTS20222023.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/citizen",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class CitizenController {

    @Autowired
    private CitizenService cs;

    @GetMapping("/isBlock/{username}")
    public ResponseEntity<Boolean> isBlock(@PathVariable("username") String username) {

        return ResponseEntity.ok(cs.isCitizenBlock(username));

    }
}
