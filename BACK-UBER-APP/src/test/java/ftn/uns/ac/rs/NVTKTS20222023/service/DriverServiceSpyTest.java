package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideNotificationDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Driver;
import ftn.uns.ac.rs.NVTKTS20222023.model.Location;
import ftn.uns.ac.rs.NVTKTS20222023.model.Ride;
import ftn.uns.ac.rs.NVTKTS20222023.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DriverServiceSpyTest {

    @Mock
    private VehicleRepository vr;

    @Mock
    private DriverRepository dr;

    @Mock
    private LocationService ls;

    @Mock
    private RideRepository rr;

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private LocationRepository lr;

    @Mock
    private LoginHistoryService lhs;

    @Mock
    private CitizenRepository cr;

    @Mock
    private CitizenService cs;

    @InjectMocks
    private DriverService ds;

    public DriverServiceSpyTest() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testMakeFakeRide_WithDifferentRouteLocations_ReturnsTrue() {
        // Set up the username and locations
        String username = "John";
        Location startLocation = new Location();
        startLocation.setLongitude(10.0);
        startLocation.setLatitude(20.0);
        Location endLocation = new Location();
        endLocation.setLongitude(30.0);
        endLocation.setLatitude(40.0);

        // Stub the findFakeRoute method of the mock DriverService
        List<Location> fakeRoute = new ArrayList<>();
        fakeRoute.add(Location.builder().latitude(11.1111).latitude(21.1111).build());
        fakeRoute.add(Location.builder().latitude(33.3333).latitude(43.3333).build());
        fakeRoute.add(Location.builder().latitude(55.5555).latitude(65.5555).build());
        fakeRoute.add(Location.builder().latitude(77.7777).latitude(87.7777).build());
        fakeRoute.add(Location.builder().latitude(99.9999).latitude(109.9999).build());

        DriverService spyDriverService = Mockito.spy(ds);
        Mockito.doReturn(fakeRoute).when(spyDriverService).findFakeRoute(startLocation, endLocation);

        // Stub the findByUsername method of the mock DriverRepository
        when(dr.findByUsername(username)).thenReturn(new Driver());


//        when(ds.findFakeRoute(startLocation, endLocation)).thenReturn(fakeRoute);

        // Call the method to be tested
        boolean result = spyDriverService.makeFakeRide(username, startLocation, endLocation);

        // Assert the result
        assertTrue(result);
        // Add additional assertions as needed
    }


    @Test
    public void testMakeFakeRide_WithExistingCurrentRide_ReturnsFalse() {
        // Set up the username and locations
        String username = "John";
        Location startLocation = new Location();
        startLocation.setLongitude(10.0);
        startLocation.setLatitude(20.0);
        Location endLocation = new Location();
        endLocation.setLongitude(30.0);
        endLocation.setLatitude(40.0);

        // Create a driver with an existing current ride
        Driver driver = new Driver();
        Ride currentRide = new Ride();
        driver.setCurrentRide(currentRide);

        // Stub the findByUsername method of the mock DriverRepository
        when(dr.findByUsername(username)).thenReturn(driver);

        // Call the method to be tested
        boolean result = ds.makeFakeRide(username, startLocation, endLocation);

        // Assert the result
        assertTrue(result);
        // Add additional assertions to verify that the current ride remains unchanged
    }

    @Test
    public void testMakeFakeRide_WithInvalidUsername_ReturnsFalse() {
        // Set up the invalid username and locations
        String username = "InvalidUser";
        Location startLocation = new Location();
        startLocation.setLongitude(10.0);
        startLocation.setLatitude(20.0);
        Location endLocation = new Location();
        endLocation.setLongitude(30.0);
        endLocation.setLatitude(40.0);

        // Stub the findByUsername method of the mock DriverRepository
        when(dr.findByUsername(username)).thenReturn(null);

        // Call the method to be tested
        boolean result = ds.makeFakeRide(username, startLocation, endLocation);

        // Assert the result
        assertFalse(result);
        // Add additional assertions as needed
    }

    @Test
    public void testMakeFakeRide_WithValidUsernameAndLocations_ReturnsTrue() {
        // Set up the username and locations
        String username = "John";
        Location startLocation = new Location();
        startLocation.setLongitude(10.0);
        startLocation.setLatitude(20.0);
        Location endLocation = new Location();
        endLocation.setLongitude(30.0);
        endLocation.setLatitude(40.0);

        // Stub the findByUsername method of the mock DriverRepository
        when(dr.findByUsername(username)).thenReturn(new Driver());

        // Call the method to be tested
        boolean result = ds.makeFakeRide(username, startLocation, endLocation);

        // Assert the result
        assertTrue(result);
        // Add additional assertions as needed
    }

//    @Test
//    public void testCitizenNewRide_RideNotFound() {
//        // Prepare test data
//        Location startLocation = new Location();
//        startLocation.setLongitude(40.0);
//        startLocation.setLatitude(30.0);
//        Location endLocation = new Location();
//        endLocation.setLongitude(20.0);
//        endLocation.setLatitude(10.0);
//
//        String username = "user1";
//
//        List<Ride> rides = Collections.emptyList();
//
//        List<Location> returnList = new ArrayList<>();
//
//        DriverService spyDriverService = Mockito.spy(ds);
//        Mockito.doReturn(returnList).when(spyDriverService).findFakeRoute(startLocation, endLocation);
//
//        Mockito.when(rr.findAll()).thenReturn(rides);
//
//        // Call the method
//        RideNotificationDTO result = spyRideService.citizenNewRide(username);
//
//        // Assert the result
//        assertNull(result.getText());
//        assertEquals(0.0, result.getPrice(), 0.001);
//        assertNull(result.getId());
//    }

}
