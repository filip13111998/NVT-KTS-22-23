package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.model.Admin;
import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import ftn.uns.ac.rs.NVTKTS20222023.model.Driver;
import ftn.uns.ac.rs.NVTKTS20222023.repository.AdminRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.CitizenRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private DriverRepository dr;
    @Autowired
    private AdminRepository ar;
    @Autowired
    private CitizenRepository cr;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("FIND USER");
        Driver driver = dr.findByUsername(username);
//        System.out.println(user);
        if (driver != null) {
//            System.out.println("USAO U DRIVER");
            driver.setActive(true);
            return driver;
//            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        Admin admin = ar.findByUsername(username);
        if (admin != null) {
            return admin;
//            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        Citizen citizen = cr.findByUsername(username);
        if (citizen != null) {
//            System.out.println("USAO U CITIZENA");
            if(citizen.isVerify()){
                return citizen;
            }
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
//            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }else{
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }

    }
}
