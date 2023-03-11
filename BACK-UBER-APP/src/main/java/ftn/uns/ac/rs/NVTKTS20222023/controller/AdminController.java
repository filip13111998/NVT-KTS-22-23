package ftn.uns.ac.rs.NVTKTS20222023.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.CitizenUpdateDTO;
import ftn.uns.ac.rs.NVTKTS20222023.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AdminController {

    @Autowired
    private AdminService as;

    @PostMapping("/updated-driver-profiles")
    public ResponseEntity<List<String>> updateCitizenProfile() {

        return ResponseEntity.ok(as.getAllUpdatedDrivers());

    }

    @PostMapping("/updated-driver-profiles/{username}")
    public ResponseEntity<Boolean> acceptDriverUpdate(@PathVariable String username) {

        return ResponseEntity.ok(as.acceptDriverUpdate(username));

    }

}
