package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.model.Driver;
import ftn.uns.ac.rs.NVTKTS20222023.model.LoginHistory;
import ftn.uns.ac.rs.NVTKTS20222023.repository.DriverRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.LoginHistoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoginHistoryServiceTest {
    @Mock
    private LoginHistoryRepository loginHistoryRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private LoginHistoryService loginHistoryService;

    public LoginHistoryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void setLastLogout_ValidUsername_ReturnsTrue() {
        // Arrange
        String username = "testUser";
        Driver driver = new Driver();
        driver.setUsername(username);
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setDriver(driver);
        List<LoginHistory> loginHistories = new ArrayList<>();
        loginHistories.add(loginHistory);

        when(loginHistoryRepository.findAll()).thenReturn(loginHistories);

        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        long millisecondsSinceEpoch = zonedDateTime.toInstant().toEpochMilli();

        // Act
        boolean result = loginHistoryService.setLastLogout(username);

        // Assert
        assertTrue(result);
        assertTrue(millisecondsSinceEpoch <= loginHistory.getEndDate());
        verify(loginHistoryRepository, times(1)).save(loginHistory);
    }

    @Test
    void setLastLogout_InvalidUsername_ReturnsFalse() {
        // Arrange
        String username = "testUser";
        List<LoginHistory> loginHistories = new ArrayList<>();

        when(loginHistoryRepository.findAll()).thenReturn(loginHistories);

        // Act
        boolean result = loginHistoryService.setLastLogout(username);

        // Assert
        assertFalse(result);
        verify(loginHistoryRepository, never()).save(any(LoginHistory.class));
    }

    @Test
    void testHasDriverBeenLoggedLowerThan8HoursIn24Hours_DriverNotFound() {
        String username = "nonexistent";
        when(driverRepository.findByUsername(username)).thenReturn(null);

        boolean result = loginHistoryService.hasDriverBeenLoggedLowerThan8HoursIn24Hours(username);

        assertFalse(result);
        verify(driverRepository, times(1)).findByUsername(username);
        verifyNoInteractions(loginHistoryRepository);
    }

    @Test
    void testHasDriverBeenLoggedLowerThan8HoursIn24Hours_NoLoginHistory() {
        String username = "existing";
        Driver driver = new Driver();
        driver.setUsername(username);
        when(driverRepository.findByUsername(username)).thenReturn(driver);
        when(loginHistoryRepository.findAll()).thenReturn(new ArrayList<>());

        boolean result = loginHistoryService.hasDriverBeenLoggedLowerThan8HoursIn24Hours(username);

        assertTrue(result);
        verify(driverRepository, times(1)).findByUsername(username);
        verify(loginHistoryRepository, times(1)).findAll();
    }

    @Test
    void testHasDriverBeenLoggedLowerThan8HoursIn24Hours_Under8Hours() {
        String username = "existing";
        Driver driver = new Driver();
        driver.setUsername(username);
        when(driverRepository.findByUsername(username)).thenReturn(driver);

        long currentTime = System.currentTimeMillis();

        List<LoginHistory> loginHistoryList = new ArrayList<>();
        loginHistoryList.add(LoginHistory.builder()
                .driver(driver)
                .start(currentTime - 6 * 60 * 60 * 1000L)
                .endDate(currentTime - 5 * 60 * 60 * 1000L)
                .build());
        loginHistoryList.add(LoginHistory.builder()
                .driver(driver)
                .start(currentTime - 4 * 60 * 60 * 1000L)
                .endDate(currentTime - 3 * 60 * 60 * 1000L)
                .build());

        when(loginHistoryRepository.findAll()).thenReturn(loginHistoryList);

        boolean result = loginHistoryService.hasDriverBeenLoggedLowerThan8HoursIn24Hours(username);

        assertTrue(result);
        verify(driverRepository, times(1)).findByUsername(username);
        verify(loginHistoryRepository, times(1)).findAll();
    }

    @Test
    void testHasDriverBeenLoggedLowerThan8HoursIn24Hours_Over8Hours() {
        String username = "existing";
        Driver driver = new Driver();
        driver.setUsername(username);
        when(driverRepository.findByUsername(username)).thenReturn(driver);

        long currentTime = System.currentTimeMillis();

        List<LoginHistory> loginHistoryList = new ArrayList<>();
        loginHistoryList.add(LoginHistory.builder()
                .driver(driver)
                .start(currentTime - 12 * 60 * 60 * 1000L)
                .endDate(currentTime - 4 * 60 * 60 * 1000L)
                .build());
        loginHistoryList.add(LoginHistory.builder()
                .driver(driver)
                .start(currentTime - 2 * 60 * 60 * 1000L)
                .endDate(currentTime - 1 * 60 * 60 * 1000L)
                .build());

        when(loginHistoryRepository.findAll()).thenReturn(loginHistoryList);

        boolean result = loginHistoryService.hasDriverBeenLoggedLowerThan8HoursIn24Hours(username);

        assertFalse(result);
        verify(driverRepository, times(1)).findByUsername(username);
        verify(loginHistoryRepository, times(1)).findAll();
    }

}
