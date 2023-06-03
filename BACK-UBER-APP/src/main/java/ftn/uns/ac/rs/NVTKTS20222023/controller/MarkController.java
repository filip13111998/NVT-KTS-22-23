package ftn.uns.ac.rs.NVTKTS20222023.controller;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.MarkDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.CitizenBlockDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.DriverBlockDTO;
import ftn.uns.ac.rs.NVTKTS20222023.service.BlockService;
import ftn.uns.ac.rs.NVTKTS20222023.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/mark",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class MarkController {

    @Autowired
    private MarkService ms;

    @PostMapping("/citizens")
    public ResponseEntity<Boolean> mark(@RequestBody MarkDTO mdto) {

        return ResponseEntity.ok(ms.mark(mdto));

    }

//    @GetMapping("/drivers")
//    public ResponseEntity<List<DriverBlockDTO>> findAllDrivers() {
//
//        return ResponseEntity.ok(bs.findAllDrivers());
//
//    }

}
