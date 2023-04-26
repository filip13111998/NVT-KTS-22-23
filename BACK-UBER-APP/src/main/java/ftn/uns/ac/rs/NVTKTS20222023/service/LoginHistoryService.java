package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.model.Driver;
import ftn.uns.ac.rs.NVTKTS20222023.model.LoginHistory;
import ftn.uns.ac.rs.NVTKTS20222023.repository.DriverRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.LoginHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoginHistoryService {

    @Autowired
    private LoginHistoryRepository lhr;

    @Autowired
    private DriverRepository dr;

    @Transactional
    public boolean setLastLogout(String username){

        Optional<LoginHistory> loginHistory = lhr.findAll().stream()
                .filter(lh->lh.getDriver().getUsername().equals(username) && lh.getEndDate() == 0)
                .findFirst();

        LoginHistory lh = loginHistory.get();
        if(lh == null){
            return false;
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        long millisecondsSinceEpoch = zonedDateTime.toInstant().toEpochMilli();

        lh.setEndDate(millisecondsSinceEpoch);

        lhr.save(lh);

        return true;
    }

    public boolean hasDriverBeenLoggedLowerThan8HoursIn24Hours(String username) {
        Driver driver = dr.findByUsername(username);
        if (driver == null) {
            // handle the case where the driver does not exist
            return false;
        }

//        List<LoginHistory> loginHistoryList = lhr.findByDriverOrderByEndDateDesc(driver);
        List<LoginHistory> loginHistoryList = lhr.findAll().stream().filter(lh->lh.getDriver().getUsername().equals(username)).collect(Collectors.toList());
        if(loginHistoryList.size()==0){
            return true;
        }

        long now = System.currentTimeMillis();
        long timeWindow = 24 * 60 * 60 * 1000L; // 24 hours in milliseconds
        long totalDuration = 0;
        for (LoginHistory loginHistory : loginHistoryList) {
            if(loginHistory.getEndDate()==0){
                loginHistory.setEndDate(System.currentTimeMillis());
            }
            if (loginHistory.getEndDate() >= now - timeWindow) {
                totalDuration += loginHistory.getEndDate() - loginHistory.getStart();
            } else {
                break; // no need to check older login history entries
            }
        }
        System.out.println("TOTAL DURATION IS" + totalDuration);
        return totalDuration < 8 * 60 * 60 * 1000L; // 8 hours in milliseconds
    }

}
