package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.response.FavoriteRideTableViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import ftn.uns.ac.rs.NVTKTS20222023.model.Ride;
import ftn.uns.ac.rs.NVTKTS20222023.repository.CitizenRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FavoriteRideService {

    @Autowired
    private CitizenRepository cr;

    @Autowired
    private RideRepository rr;

    public Boolean addRideToFavorite(String username, Long rideId) {

        Citizen citizen = cr.findByUsername(username);

        Ride ride = rr.findById(rideId).orElse(null);

        if(citizen == null || ride == null ){
            return false;
        }

        if(!citizen.getRides().contains(ride)){
            return false;
        }

        citizen.getFavorite().add(ride);

        return true;
    }

    public List<FavoriteRideTableViewDTO> findAll(String username) {

        Citizen citizen = cr.findByUsername(username);

        if(citizen == null){
            return new ArrayList<>();
        }

        List<FavoriteRideTableViewDTO> frtwdto_list = citizen.getFavorite().stream()
                .map(r-> FavoriteRideTableViewDTO.builder()
                        .id(r.getId())
                        .babyFriendly(r.isBabyFriendly())
                        .petFriendly(r.isPetFriendly())
                        .type(r.getType())
                        .meters(r.getMeters())
                        .name(r.getName())
                        .price(r.getPrice())
                        .build())

                .collect(Collectors.toList());

        return frtwdto_list;
    }
}
