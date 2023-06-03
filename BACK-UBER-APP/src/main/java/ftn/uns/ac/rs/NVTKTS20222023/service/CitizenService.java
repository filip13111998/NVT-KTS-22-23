package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import ftn.uns.ac.rs.NVTKTS20222023.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CitizenService {

    @Autowired
    private CitizenRepository cr;

    public boolean blockAllCitizensByUsernames(List<Citizen> citizens){

        for(Citizen c : citizens){
            c.setBlock(true);
        }
        cr.saveAll(citizens);

        return true;
    }

    public boolean unblockAllCitizensByUsernames(List<Citizen> citizens){
        List<Citizen> updatedCitizens = new ArrayList<>();
        for(Citizen c : citizens){
            c.setBlock(false);
            updatedCitizens.add(c);
        }
        cr.saveAll(updatedCitizens);

        return true;

    }

    public boolean isCitizenBlock(String username){
        return cr.findByUsername(username).isBlock();
    }
}
