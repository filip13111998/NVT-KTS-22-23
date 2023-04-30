package ftn.uns.ac.rs.NVTKTS20222023.controller;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.MessageDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.CitizenBlockDTO;
import ftn.uns.ac.rs.NVTKTS20222023.service.BlockService;
import ftn.uns.ac.rs.NVTKTS20222023.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/chat",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ChatRestController {

    @Autowired
    private ChatService cs;

    @GetMapping("/citizens")
    public ResponseEntity<List<String>> findAllCitizens() {

        return ResponseEntity.ok(cs.getCitizens());

    }

    @GetMapping("/drivers")
    public ResponseEntity<List<String>> findAllDrivers() {

        return ResponseEntity.ok(cs.getDrivers());

    }

    @GetMapping("/citizens/{username}")
    public ResponseEntity<List<MessageDTO>> findAllMessagesOfCitizen(@PathVariable("username") String username) {

        return ResponseEntity.ok(cs.findAllMessagesOfCitizen(username));

    }

    @GetMapping("/drivers/{username}")
    public ResponseEntity<List<MessageDTO>> findAllMessagesOfDriver(@PathVariable("username") String username) {

        return ResponseEntity.ok(cs.findAllMessagesOfDriver(username));

    }

}
