package ftn.uns.ac.rs.NVTKTS20222023.controller;

import ftn.uns.ac.rs.NVTKTS20222023.dto.response.FavoriteRideTableViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.service.AdminService;
import ftn.uns.ac.rs.NVTKTS20222023.service.FavoriteRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/favorite",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FavoriteRideController {

    @Autowired
    private FavoriteRideService frs;

    @GetMapping("/add/{username}/{rideId}")
    public ResponseEntity<Boolean> addRideToFavorite(@PathVariable("username") String username,
                                                          @PathVariable("rideId") Long rideId) {

        return ResponseEntity.ok(frs.addRideToFavorite(username,rideId));

    }

    @GetMapping("/all/{username}")
    public ResponseEntity<List<FavoriteRideTableViewDTO>> getAllFavorite(@PathVariable("username") String username) {
        return ResponseEntity.ok(frs.findAll(username));
    }

}
