package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.response.CitizenBlockDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.DriverBlockDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import ftn.uns.ac.rs.NVTKTS20222023.model.Driver;
import ftn.uns.ac.rs.NVTKTS20222023.model.Ride;
import ftn.uns.ac.rs.NVTKTS20222023.repository.CitizenRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BlockService {

    @Autowired
    private CitizenRepository cr;

    @Autowired
    private DriverRepository dr;

    public List<CitizenBlockDTO> findAllCitizens() {

        List<CitizenBlockDTO> cbdto_list = cr.findAll().stream()
                .map(c->CitizenBlockDTO.builder()
                        .username(c.getUsername())
                        .city(c.getCity())
                        .firstName(c.getFirstName())
                        .lastName(c.getLastName())
                        .phone(c.getPhone())
                        .block(c.isBlock())
                        .build())
                .collect(Collectors.toList());

        return cbdto_list;
    }

    public List<DriverBlockDTO> findAllDrivers() {

        List<DriverBlockDTO> dbdto_list = dr.findAll().stream()
                .map(c->DriverBlockDTO.builder()
                        .username(c.getUsername())
                        .city(c.getCity())
                        .firstName(c.getFirstName())
                        .lastName(c.getLastName())
                        .phone(c.getPhone())
                        .block(c.isBlock())
                        .build())
                .collect(Collectors.toList());

        return dbdto_list;
    }

    public boolean blockCitizen(String username,String comment) {

        Citizen citizen = cr.findByUsername(username);

        if(citizen == null){
            return false;
        }

        citizen.setBlock(!citizen.isBlock()); // toggle
        citizen.setComment(comment);

        return true;
    }

    public boolean blockDriver(String username,String comment) {

        Driver driver = dr.findByUsername(username);

        if(driver == null){
            return false;
        }

        driver.setBlock(!driver.isBlock()); // toggle
        driver.setComment(comment);

        return true;

    }


}
