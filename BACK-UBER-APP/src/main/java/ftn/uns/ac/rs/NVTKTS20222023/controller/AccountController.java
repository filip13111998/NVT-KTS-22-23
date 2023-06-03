package ftn.uns.ac.rs.NVTKTS20222023.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.*;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.CitizenProfileDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.DriverProfileDTO;
import ftn.uns.ac.rs.NVTKTS20222023.exception.TwoPasswordsNotSameException;
import ftn.uns.ac.rs.NVTKTS20222023.model.Admin;
import ftn.uns.ac.rs.NVTKTS20222023.repository.CitizenRepository;
import ftn.uns.ac.rs.NVTKTS20222023.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


//POINT : 2.2
@RestController
@RequestMapping(value = "/auth",
                produces = MediaType.APPLICATION_JSON_VALUE
)
public class AccountController {

    @Autowired
    private AccountService as;

    @PostMapping("/register-citizen")
    public ResponseEntity<Boolean> registerCitizen(@RequestBody RegisterCitizenDTO rcdto){

        try{

            Boolean answer = as.registerCitizen(rcdto);

            return ResponseEntity.ok(answer);

        } catch (TwoPasswordsNotSameException exc) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong passwords match!", exc);

        }

    }

    @PostMapping("/register-driver")
    public ResponseEntity<Boolean> registerDriver(@RequestBody RegisterDriverDTO rddto){

        Boolean answer = as.registerDriver(rddto);

        return ResponseEntity.ok(answer);
    }

    @GetMapping("/verify/{username}")
    public ResponseEntity<Boolean> verify(@PathVariable String username){

            Boolean answer = as.verify(username);

            return ResponseEntity.ok(answer);
    }

    @PostMapping("/reset-citizen")
    public ResponseEntity<Boolean> passwordResetCitizen(@RequestBody ResetPasswordDTO rpdto){

        try{

            Boolean answer = as.passwordResetCitizen(rpdto);

            return ResponseEntity.ok(answer);

        } catch (TwoPasswordsNotSameException exc) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong passwords match!", exc);

        }

    }

    @PostMapping("/reset-driver")
    public ResponseEntity<Boolean> passwordResetDriver(@RequestBody ResetPasswordDTO rpdto){

        try{

            Boolean answer = as.passwordResetDriver(rpdto);

            return ResponseEntity.ok(answer);

        } catch (TwoPasswordsNotSameException exc) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong passwords match!", exc);

        }

    }

    @PostMapping("/reset-admin")
    public ResponseEntity<Boolean> passwordResetAdmin(@RequestBody ResetPasswordDTO rpdto){

        try{

            Boolean answer = as.passwordResetAdmin(rpdto);

            return ResponseEntity.ok(answer);

        } catch (TwoPasswordsNotSameException exc) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong passwords match!", exc);

        }

    }

    @PostMapping("/change-state")
    public ResponseEntity<Boolean> changeStateDriver(@RequestBody ChangeDriverStateDTO cdsdto){

        Boolean answer = as.changeStateDriver(cdsdto);

        return ResponseEntity.ok(answer);

    }

    @GetMapping("/logout-driver/{username}")
    public ResponseEntity<Boolean> logoutDriver(@PathVariable String username){

        Boolean answer = as.logout(username);

        return ResponseEntity.ok(answer);

    }

    @PostMapping("/update-admin")
    public ResponseEntity<Boolean> updateAdminProfile(
            @RequestParam(value = "image",required = false) MultipartFile file,
            @RequestParam(value = "user" , required = false) String dto) {

        ObjectMapper mapper = new ObjectMapper();
        AdminUpdateDTO audto = new AdminUpdateDTO();
        try {
            audto = mapper.readValue(dto, AdminUpdateDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        System.out.println(dto.getCity());
        as.updateAdminProfile(audto, file);

        return ResponseEntity.ok(true);
    }

    @PostMapping("/update-driver")
    public ResponseEntity<Boolean> updateDriverProfile(
            @RequestParam(value = "image",required = false) MultipartFile file,
            @RequestParam(value = "user" , required = false) String dto) {

        ObjectMapper mapper = new ObjectMapper();
        DriverUpdateDTO dudto = new DriverUpdateDTO();
        try {
            dudto = mapper.readValue(dto, DriverUpdateDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        System.out.println(dto.getCity());
        as.updateDriverProfile(dudto, file);

        return ResponseEntity.ok(true);
    }

    @PostMapping("/update-citizen")
    public ResponseEntity<Boolean> updateCitizenProfile(
            @RequestParam(value = "image",required = false) MultipartFile file,
            @RequestParam(value = "user" , required = false) String dto) {

        ObjectMapper mapper = new ObjectMapper();
        CitizenUpdateDTO cudto = new CitizenUpdateDTO();
        try {
            cudto = mapper.readValue(dto, CitizenUpdateDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        System.out.println(dto.getCity());
        as.updateCitizenProfile(cudto, file);

        return ResponseEntity.ok(true);
    }

    @GetMapping("/get-citizen/{username}")
    public ResponseEntity<CitizenProfileDTO> getCitizenProfile(@PathVariable("username") String username) {

        CitizenProfileDTO profile = as.getCitizenProfile(username);

        return ResponseEntity.ok(profile);
    }

    @GetMapping("/get-driver/{username}")
    public ResponseEntity<DriverProfileDTO> getDriverProfile(@PathVariable("username") String username) {

        DriverProfileDTO profile = as.getDriverProfile(username);

        return ResponseEntity.ok(profile);
    }

    @GetMapping("/get-drivers")
    public ResponseEntity<List<DriverProfileDTO>> getDriverProfiles() {

        List<DriverProfileDTO> profiles = as.getDriverProfile();

        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/admin-accept-driver-profile/{username}")
    public ResponseEntity<Boolean> adminAcceptDriverUpdate(@PathVariable("username") String username) {

        Boolean answer = as.adminAcceptDriverUpdate(username);

        return ResponseEntity.ok(answer);
    }

}
