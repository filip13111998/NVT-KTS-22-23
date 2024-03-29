package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.model.Admin;
import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import ftn.uns.ac.rs.NVTKTS20222023.model.Driver;
import ftn.uns.ac.rs.NVTKTS20222023.model.LoginHistory;
import ftn.uns.ac.rs.NVTKTS20222023.repository.AdminRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.CitizenRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.DriverRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.LoginHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private DriverRepository dr;
    @Autowired
    private AdminRepository ar;
    @Autowired
    private CitizenRepository cr;

    @Autowired
    private LoginHistoryRepository lhr;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("FIND USER");
        Driver driver = dr.findByUsername(username);
//        System.out.println(user);
        if (driver != null) {
//            System.out.println("USAO U DRIVER");
            driver.setActive(true);

            LocalDateTime localDateTime = LocalDateTime.now();
            ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
            long millisecondsSinceEpoch = zonedDateTime.toInstant().toEpochMilli();
            lhr.save(LoginHistory.builder().driver(driver).start(millisecondsSinceEpoch).build());

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
