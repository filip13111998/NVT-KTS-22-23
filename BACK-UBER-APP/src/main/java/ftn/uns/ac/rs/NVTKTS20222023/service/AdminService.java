package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.model.Driver;
import ftn.uns.ac.rs.NVTKTS20222023.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminService {

    @Autowired
    private DriverRepository dr;

    public List<String> getAllUpdatedDrivers() {

        List<String> drivers = dr.findAll().stream().filter(d-> d.isEdit()).map(d->d.getUsername()).collect(Collectors.toList());

        return drivers;

    }

    public Boolean acceptDriverUpdate(String username) {

        Driver driver = dr.findByUsername(username);

        if(driver.isEdit()){
            driver.setPassword(driver.getRepassword());
            driver.setFirstName(driver.getRefirstName());
            driver.setLastName(driver.getRelastName());
            driver.setPhone(driver.getRephone());
            driver.setCity(driver.getRecity());

            return true;
        }

        return false;
    }

}
