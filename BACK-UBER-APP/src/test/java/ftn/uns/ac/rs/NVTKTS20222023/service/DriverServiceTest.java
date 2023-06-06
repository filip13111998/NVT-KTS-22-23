package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideNotificationDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.VehicleMapViewDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.*;
import ftn.uns.ac.rs.NVTKTS20222023.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DriverServiceTest {

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

    public DriverServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void activeDriversIncrementCounter_ShouldUpdateDriverAndRideStatus_WhenRideStatusIsFake() {
        Driver driver = new Driver();
        driver.setUsername("zika");
        driver.setCounter(0);
        driver.setActive(true);
        driver.setBlock(false);

        Vehicle vehicle = new Vehicle();
        vehicle.setBusy(true);

        driver.setVehicle(vehicle);

        Ride ride = new Ride();
        ride.setStatus("FAKE");
        ride.setId(1l);

        Location locationStart = new Location();
        Location locationEnd = new Location();

        List<Location> locations = new ArrayList<>();

        locations.add(locationStart);
        locations.add(locationEnd);

        Route route = new Route();
        route.setLocations(locations);

        List<Route> routes = new ArrayList<>();
        ride.setRoutes(routes);
        driver.setCurrentRide(ride);
        List<Driver> drivers = new ArrayList<>();
        drivers.add(driver);

        when(dr.findAll()).thenReturn(drivers);
        when(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours(anyString())).thenReturn(true);

        // Act
        boolean result = ds.activeDriversIncrementCounter();

        // Assert
        assertTrue(result);
        assertEquals(0, driver.getCounter());
        assertEquals("END", ride.getStatus());
        assertNull(driver.getCurrentRide());
        verify(rr).delete(any());

    }


    @Test
    void activeDriversIncrementCounter_ShouldUpdateDriverAndRideStatus_WhenCounterReachedEnd() {
        // Arrange
        Driver driver = new Driver();
        driver.setUsername("zika");
        driver.setCounter(1);
        driver.setActive(true);
        driver.setBlock(false);

        Vehicle vehicle = new Vehicle();
        vehicle.setBusy(false);

        driver.setVehicle(vehicle);

        Ride ride = new Ride();
        ride.setStatus("START");

        Location locationStart = new Location();
        Location locationMid = new Location();
        Location locationEnd = new Location();

        List<Location> locations = new ArrayList<>();

        locations.add(locationStart);
        locations.add(locationMid);
        locations.add(locationEnd);

        Route route = new Route();
        route.setLocations(locations);

        List<Route> routes = new ArrayList<>();
        routes.add(route);

        ride.setRoutes(routes);
        driver.setCurrentRide(ride);
        List<Driver> drivers = new ArrayList<>();
        drivers.add(driver);
        System.out.println("LOC " + ride.getAllLocations());
        when(dr.findAll()).thenReturn(drivers);
        when(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours(anyString())).thenReturn(true);

        // Act
        boolean result = ds.activeDriversIncrementCounter();

        // Assert
        assertTrue(result);
        assertEquals(2, driver.getCounter());
        assertEquals("START", ride.getStatus());
        assertEquals(true, vehicle.isBusy());
//        verify(dr, times(1)).save(driver);
//        verify(rr, times(1)).delete(driver.getCurrentRide());
    }


    @Test
    void activeDriversIncrementCounter_ShouldUpdateDriverAndRideStatus_WhenRideIsNull() {
        // Arrange
        Driver driver = new Driver();
        driver.setCurrentRide(null);
        driver.setCounter(4);
        List<Driver> drivers = new ArrayList<>();
        drivers.add(driver);

        when(dr.findAll()).thenReturn(drivers);

        // Act
        boolean result = ds.activeDriversIncrementCounter();

        // Assert
        assertTrue(result);
        assertEquals(4, driver.getCounter());
        verifyNoInteractions(rr);

    }

    @Test
    void activeDriversIncrementCounter_ShouldUpdateDriverAndRideStatus() {
        // Arrange
        Driver driver = new Driver();
        driver.setUsername("zika");
        driver.setCounter(0);
        driver.setActive(true);
        driver.setBlock(false);

        Vehicle vehicle = new Vehicle();
        vehicle.setBusy(true);

        driver.setVehicle(vehicle);

        Ride ride = new Ride();
        ride.setStatus("START");

        Location locationStart = new Location();
        Location locationEnd = new Location();

        List<Location> locations = new ArrayList<>();

        locations.add(locationStart);
        locations.add(locationEnd);

        Route route = new Route();
        route.setLocations(locations);

        List<Route> routes = new ArrayList<>();
        ride.setRoutes(routes);
        driver.setCurrentRide(ride);
        List<Driver> drivers = new ArrayList<>();
        drivers.add(driver);

        when(dr.findAll()).thenReturn(drivers);
        when(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours(anyString())).thenReturn(true);

        // Act
        boolean result = ds.activeDriversIncrementCounter();

        // Assert
        assertTrue(result);
        assertEquals(0, driver.getCounter());
        assertEquals("END", ride.getStatus());
        assertTrue(driver.getVehicle().isBusy());

    }



    @Test
    void testFinishRide_DriverNotFound() {
        // Mock the findByUsername method to return null
        when(dr.findByUsername(Mockito.anyString())).thenReturn(null);

        // Invoke the finishRide method
        boolean result = ds.finishRide("username");

        // Assert the result
        assertFalse(result);
        // Assert other conditions if needed
        // ...
    }

    @Test
    void testFinishRide_NoCurrentRideFutureRide() {
        // Mock the findByUsername method to return a driver
        Driver driver = new Driver();
        when(dr.findByUsername(Mockito.anyString())).thenReturn(driver);

        // Invoke the finishRide method
        boolean result = ds.finishRide("username");

        // Assert the result
        assertFalse(result);
        // Assert other conditions if needed
        // ...
    }

    @Test
    void testFinishRide_NoCurrentRide() {
        // Mock the findByUsername method to return a driver
        Driver driver = new Driver();

        Ride futureRide = new Ride();
        futureRide.setStatus("END");
        driver.setFutureRide(futureRide);
        driver.getFutureRide().setCitizens(new ArrayList<>());


        when(dr.findByUsername(Mockito.anyString())).thenReturn(driver);

        // Invoke the finishRide method
        boolean result = ds.finishRide("username");

        // Assert the result
        assertFalse(result);
        // Assert other conditions if needed
        // ...
    }

    @Test
    void testFinishRide_ValidFinishWithFutureRide() {
        // Mock the findByUsername method to return a driver
        Driver driver = new Driver();
        driver.setUsername("zika");

        Vehicle vehicle = new Vehicle();
        vehicle.setBusy(true);
        vehicle.setLocation(Location.builder().longitude(21.35).latitude(43.33).build());

        driver.setVehicle(vehicle);
        // Set up the driver's current ride and vehicle
        Ride currentRide = new Ride();
        currentRide.setStatus("END");
        driver.setCurrentRide(currentRide);
        driver.getCurrentRide().setCitizens(new ArrayList<>());


        // Set up the future ride
        Ride futureRide = new Ride();

        // Set up the start location
        Location startLocation = new Location();
        startLocation.setLongitude(25.32);
        startLocation.setLatitude(19.23);

        // Set up the end location
        Location endLocation = new Location();
        endLocation.setLongitude(22.32);
        endLocation.setLatitude(12.23);
        // ...

        List<Location> locations = new ArrayList<>();
        locations.add(startLocation);
        locations.add(endLocation);  // Add the end location to the list

        Route route = new Route();
        route.setLocations(locations);
        List<Route> routes = new ArrayList<>();
        routes.add(route);

        futureRide.setRoutes(routes);
        driver.setFutureRide(futureRide);

        // Mock the unblockAllCitizensByUsernames method
        when(dr.findByUsername("zika")).thenReturn(driver);
        when(cs.unblockAllCitizensByUsernames(driver.getCurrentRide().getCitizens())).thenReturn(true);

        // Invoke the finishRide method
        boolean result = ds.finishRide("zika");

        // Assert the result
        assertTrue(result);
        assertNotNull(driver.getCurrentRide());
        assertTrue(vehicle.isBusy());
        // Assert other changes made to the driver or vehicle
        // ...

        // If there are any method calls that should have been made on mock objects, you can verify them using Mockito
        verify(dr,times(2)).save(driver);
        verify(vr,times(2)).save(vehicle);
        // ...
    }

    @Test
    void testFinishRide_ValidFinish() {
        // Mock the findByUsername method to return a driver
        Driver driver = new Driver();

        // Set up the driver's current ride and vehicle
        Ride currentRide = new Ride();
        currentRide.setStatus("END");
        driver.setCurrentRide(currentRide);
        driver.getCurrentRide().setCitizens(new ArrayList<>());

        Vehicle vehicle = new Vehicle();
        vehicle.setBusy(true);
        driver.setVehicle(vehicle);

        // Mock the getAllLocations method to return some locations
        List<Location> allLocations = new ArrayList<>();
        Location startLocation = new Location();
        // Set up the start location
        // ...
        allLocations.add(startLocation);
        // Set up other locations if needed
        // ...
        Route route = new Route();
        route.setLocations(allLocations);
        List<Route> routes = new ArrayList<>();
        routes.add(route);

        currentRide.setRoutes(routes);

        // Mock the unblockAllCitizensByUsernames method
//        doNothing().when(cs).unblockAllCitizensByUsernames(Mockito.anyList());
        when(dr.findByUsername(Mockito.anyString())).thenReturn(driver);
        when(cs.unblockAllCitizensByUsernames(driver.getCurrentRide().getCitizens())).thenReturn(true);

        // Invoke the finishRide method
        boolean result = ds.finishRide("username");

        // Assert the result
        assertTrue(result);
        assertNull(driver.getCurrentRide());
        assertFalse(vehicle.isBusy());
        // Assert other changes made to the driver or vehicle
        // ...
        // Assert other changes made to the driver or vehicle
        // For example, if there are additional fields that should be modified:
        assertEquals(0, driver.getCounter());
        // ...

        // If there are any method calls that should have been made on mock objects, you can verify them using Mockito
        verify(dr).save(driver);
        verify(vr).save(vehicle);
        // ...

    }

    @Test
    void testGetAllVehicleMapViewDTOWhenNoActiveDrivers() {
        // Mock the findAllActiveDrivers method to return an empty list
        when(dr.findAll()).thenReturn(Collections.emptyList());
        when(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours(anyString())).thenReturn(false);

        List<VehicleMapViewDTO> result = ds.getAllVehicleMapViewDTO();

        // Assert the expected size of the result list
        assertEquals(0, result.size());
    }

    @Test
    void testGetAllVehicleMapViewDTO() {
        // Mock the findAllActiveDrivers method
        List<Driver> mockDrivers = new ArrayList<>();
        // Add some mock drivers to the list
        Driver driver1 = new Driver();
        driver1.setUsername("zika");
        driver1.setActive(true);
        driver1.setBlock(false);
        mockDrivers.add(driver1);
        // ...

        Vehicle vehicle = new Vehicle();
        vehicle.setBusy(true);
        vehicle.setType("CAR");
        vehicle.setBabyFriendly(true);
        vehicle.setPetFriendly(false);
        vehicle.setName("AUDI");

        driver1.setVehicle(vehicle);

        when(dr.findAll()).thenReturn(mockDrivers);

        // Mock the hasDriverBeenLoggedLowerThan8HoursIn24Hours method
        when(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours("zika")).thenReturn(true);

        List<VehicleMapViewDTO> result = ds.getAllVehicleMapViewDTO();



        // Add more assertions for the individual fields of the VehicleMapViewDTO objects
        // For example:
        VehicleMapViewDTO firstDto = result.get(0);

        // Assert the expected size of the result list
        assertEquals(mockDrivers.size(), result.size());

        assertNull(firstDto.getId());
        assertNotNull(firstDto.isBabyFriendly());
        assertNotNull(firstDto.isPetFriendly());

        // You can also assert specific values if you know them based on the mocked data
        // For example:
        assertEquals("AUDI", firstDto.getName());
        assertEquals(false, firstDto.isPetFriendly());
        assertEquals(true, firstDto.isBabyFriendly());
        assertEquals(true, firstDto.isOccupied());
        assertEquals("CAR", firstDto.getType());
    }

    @Test
    public void testStartRide_WithValidUsernameAndNoFutureRide_ReturnsFalse() {
        // Set up the username
        String username = "John";

        // Create a driver with no future ride
        Driver driver = new Driver();

        // Stub the findByUsername method of the mock DriverRepository
        when(dr.findByUsername(username)).thenReturn(driver);

        // Call the method to be tested
        boolean result = ds.startRide(username);

        // Assert the result
        assertFalse(result);

    }

    @Test
    public void testStartRide_WithValidUsernameAndCurrentRide_ReturnsFalse() {
        // Set up the username
        String username = "John";


        // Create a driver with a current ride
        Driver driver = new Driver();
        Ride currentRide = new Ride();
        driver.setCurrentRide(currentRide);

        // Stub the findByUsername method of the mock DriverRepository
        when(dr.findByUsername(username)).thenReturn(driver);

        // Call the method to be tested
        boolean result = ds.startRide(username);

        // Assert the result
        assertFalse(result);

    }


    @Test
    public void testStartRide_WithInvalidUsername_ReturnsFalse() {
        // Set up the invalid username
        String username = "InvalidUser";

        // Stub the findByUsername method of the mock DriverRepository to return null
        when(dr.findByUsername(username)).thenReturn(null);

        // Call the method to be tested
        boolean result = ds.startRide(username);

        // Assert the result
        assertFalse(result);

    }


    @Test
    public void testStartRide_WithValidUsernameAndPaidFutureRide_ReturnsTrue() {
        // Set up the username
        String username = "John";

        // Create a driver with a paid future ride
        Driver driver = new Driver();
        Ride futureRide = new Ride();
        futureRide.setStatus("PAID");
        driver.setFutureRide(futureRide);

        // Stub the findByUsername method of the mock DriverRepository
        when(dr.findByUsername(username)).thenReturn(driver);

        // Call the method to be tested
        boolean result = ds.startRide(username);

        // Assert the result
        assertTrue(result);
        // Add additional assertions to verify the changes made to the driver and future ride
    }


    @Test
    public void testRejectRide_WithNoFutureRide_ReturnsTrue() {
        // Set up the username and message
        String username = "John";
        String message = "Ride rejected";

        // Create a driver with no future ride
        Driver driver = new Driver();

        // Stub the findByUsername method of the mock DriverRepository
        when(dr.findByUsername(username)).thenReturn(driver);

        // Call the method to be tested
        boolean result = ds.rejectRide(username, message);

        // Assert the result
        assertTrue(result);
        // Add additional assertions to verify that no changes were made
    }

    @Test
    public void testRejectRide_WithInvalidUsername_ReturnsFalse() {
        // Set up the username and message
        String username = "John";
        String message = "Ride rejected";

        // Stub the findByUsername method of the mock DriverRepository
        when(dr.findByUsername(username)).thenReturn(null);

        // Call the method to be tested
        boolean result = ds.rejectRide(username, message);

        // Assert the result
        assertFalse(result);
        // Add additional assertions as needed
    }

    @Test
    public void testRejectRide_WithValidUsernameAndFutureRide_ReturnsTrue() {
        // Set up the username and message
        String username = "John";
        String message = "Ride rejected";
        // Stub the findByUsername method of the mock CitizenRepository

        Citizen alice = new Citizen();
        alice.setUsername("Alice");
        alice.setTokens(100l);
        Citizen bob = new Citizen();
        bob.setUsername("Bob");
        bob.setTokens(20l);
        Citizen charlie = new Citizen();
        charlie.setUsername("Charlie");
        charlie.setTokens(40l);

        // Create a driver with a future ride
        Driver driver = new Driver();

        Vehicle vehicle = new Vehicle();

        driver.setVehicle(vehicle);

        Ride futureRide = new Ride();

        futureRide.setPaid("Alice|Bob|Charlie");
        futureRide.setCitizens(Arrays.asList(alice,bob,charlie));
        futureRide.setPrice(10.0);

        driver.setFutureRide(futureRide);

        // Stub the findByUsername method of the mock DriverRepository
        when(dr.findByUsername(username)).thenReturn(driver);

        when(cr.findByUsername("Alice")).thenReturn(alice);
        when(cr.findByUsername("Bob")).thenReturn(bob);
        when(cr.findByUsername("Charlie")).thenReturn(charlie);

        // Call the method to be tested
        boolean result = ds.rejectRide(username, message);

        // Assert the result
        assertTrue(result);
        // Verify that the citizens' tokens are updated
        assertEquals(103l, alice.getTokens());
        assertEquals(23l, bob.getTokens());
        assertEquals(43l, charlie.getTokens());
        // Verify that the future ride status and comment are updated
        assertEquals("REJECT", futureRide.getStatus());
        assertEquals(message, futureRide.getComment());
        // Verify that the driver's current ride is set to null
        assertNull(driver.getFutureRide());
        // Verify that the vehicle is set to free
        assertFalse(vehicle.isBusy());
    }

    @Test
    public void testFindFakeRoute_WithStartLocationBeforeEndLocation_ReturnsFakeRouteLocations() {
        // Set up the start and end locations
        Location startLocation = new Location();
        startLocation.setLongitude(40.0);
        startLocation.setLatitude(30.0);
        Location endLocation = new Location();
        endLocation.setLongitude(20.0);
        endLocation.setLatitude(10.0);
        // Call the method to be tested
        List<Location> fakeRoute = ds.findFakeRoute(startLocation, endLocation);

        // Assert the result
        assertNotNull(fakeRoute);
        assertEquals(10, fakeRoute.size());

        // Check the longitude values
        double longitudeStep = (endLocation.getLongitude() - startLocation.getLongitude()) / 10;
        double longitude = startLocation.getLongitude();
        for (int i = 0; i < fakeRoute.size(); i++) {
            Location location = fakeRoute.get(i);
            assertEquals(longitude, location.getLongitude(), 5);
            longitude += longitudeStep;
        }

        // Check the latitude values
        double latitudeStep = (endLocation.getLatitude() - startLocation.getLatitude()) / 10;
        double latitude = startLocation.getLatitude();
        for (int i = 0; i < fakeRoute.size(); i++) {
            Location location = fakeRoute.get(i);
            assertEquals(latitude, location.getLatitude(), 5);
            latitude += latitudeStep;
        }
    }

    @Test
    public void testFindFakeRoute_WithSameStartAndEndLocation_ReturnsSingleLocation() {
        // Set up the start and end locations as the same location
        Location startLocation = new Location();
        startLocation.setLongitude(10.0);
        startLocation.setLatitude(20.0);
        Location endLocation = new Location();
        endLocation.setLongitude(10.0);
        endLocation.setLatitude(20.0);

        // Call the method to be tested
        List<Location> fakeRoute = ds.findFakeRoute(startLocation, endLocation);

        // Assert the result
        assertNotNull(fakeRoute);
        assertEquals(10, fakeRoute.size());

        // Check the longitude and latitude values
        Location location = fakeRoute.get(0);
        assertEquals(startLocation.getLongitude(), location.getLongitude(), 5);
        assertEquals(startLocation.getLatitude(), location.getLatitude(), 5);
    }

    @Test
    public void testFindFakeRoute_WithNegativeCoordinates_ReturnsFakeRouteLocations() {
        // Set up the start and end locations with negative coordinates
        Location startLocation = new Location();
        startLocation.setLongitude(-10.0);
        startLocation.setLatitude(-20.0);
        Location endLocation = new Location();
        endLocation.setLongitude(-30.0);
        endLocation.setLatitude(-40.0);

        // Call the method to be tested
        List<Location> fakeRoute = ds.findFakeRoute(startLocation, endLocation);

        // Assert the result
        assertNotNull(fakeRoute);
        assertEquals(10, fakeRoute.size());

        // Check the longitude values
        double longitudeStep = (endLocation.getLongitude() - startLocation.getLongitude()) / 10;
        double longitude = startLocation.getLongitude();
        for (int i = 0; i < fakeRoute.size(); i++) {
            Location location = fakeRoute.get(i);
            assertEquals(longitude, location.getLongitude(), 5);
            longitude += longitudeStep;
        }

        // Check the latitude values
        double latitudeStep = (endLocation.getLatitude() - startLocation.getLatitude()) / 10;
        double latitude = startLocation.getLatitude();
        for (int i = 0; i < fakeRoute.size(); i++) {
            Location location = fakeRoute.get(i);
            assertEquals(latitude, location.getLatitude(), 5);
            latitude += latitudeStep;
        }
    }

    @Test
    public void testFindFakeRoute_WithStartLocationAfterEndLocation_ReturnsFakeRouteLocations() {
        // Set up the start and end locations
        Location startLocation = new Location();
        startLocation.setLongitude(20.0);
        startLocation.setLatitude(10.0);
        Location endLocation = new Location();
        endLocation.setLongitude(40.0);
        endLocation.setLatitude(30.0);
        // Call the method to be tested
        List<Location> fakeRoute = ds.findFakeRoute(startLocation, endLocation);

        // Assert the result
        assertNotNull(fakeRoute);
        assertEquals(10, fakeRoute.size());

        // Check the longitude values
        double longitudeStep = (endLocation.getLongitude() - startLocation.getLongitude()) / 10;
        double longitude = startLocation.getLongitude();
        for (int i = 0; i < fakeRoute.size(); i++) {
            Location location = fakeRoute.get(i);
            assertEquals(longitude, location.getLongitude(), 5);
            longitude += longitudeStep;
        }

        // Check the latitude values
        double latitudeStep = (endLocation.getLatitude() - startLocation.getLatitude()) / 10;
        double latitude = startLocation.getLatitude();
        for (int i = 0; i < fakeRoute.size(); i++) {
            Location location = fakeRoute.get(i);
            assertEquals(latitude, location.getLatitude(), 5);
            latitude += latitudeStep;
        }
    }

    @Test
    public void testNewRide_WithNoRidesByUsername_ReturnsEmptyRideNotificationDTO() {
        // Create an empty list of rides
        List<Ride> rides = new ArrayList<>();

        // Stub the findAll method of the mock RideRepository
        when(rr.findAll()).thenReturn(rides);

        // Call the method to be tested
        RideNotificationDTO rideNotificationDTO = ds.newRide("John");

        // Assert the result
        assertNotNull(rideNotificationDTO);
        assertNull(rideNotificationDTO.getText());
        assertEquals(0.0, rideNotificationDTO.getPrice(), 0.01);
        assertNull(rideNotificationDTO.getId());
    }

    @Test
    public void testNewRide_WithNoPaidRideByUsername_ReturnsEmptyRideNotificationDTO() {
        // Create an empty list of rides
        List<Ride> rides = new ArrayList<>();
        Driver driver = new Driver();
        driver.setUsername("John");
        Ride ride = new Ride();
        ride.setName("Ride1");
        ride.setPrice(10.0);
        ride.setDriver(driver);
        ride.setStatus("PENDING");
        rides.add(ride);

        // Stub the findAll method of the mock RideRepository
        when(rr.findAll()).thenReturn(rides);

        // Call the method to be tested
        RideNotificationDTO rideNotificationDTO = ds.newRide("John");

        // Assert the result
        assertNotNull(rideNotificationDTO);
        assertNull(rideNotificationDTO.getText());
        assertEquals(0.0, rideNotificationDTO.getPrice(), 0.01);
        assertNull(rideNotificationDTO.getId());
    }

    @Test
    public void testNewRide_WithPaidRideByUsername_ReturnsRideNotificationDTO() {
        // Create a list of rides
        List<Ride> rides = new ArrayList<>();
        Driver driver = new Driver();
        driver.setUsername("John");
        Ride ride = new Ride();//"Ride1", 10.0, driver, "PAID"
        ride.setName("Ride1");
        ride.setPrice(10.0);
        ride.setDriver(driver);
        ride.setStatus("PAID");
        rides.add(ride);

        // Stub the findAll method of the mock RideRepository
        when(rr.findAll()).thenReturn(rides);

        // Call the method to be tested
        RideNotificationDTO rideNotificationDTO = ds.newRide("John");

        // Assert the result
        assertEquals("NOVA VOZNJA : Ride1", rideNotificationDTO.getText());
        assertEquals(10.0, rideNotificationDTO.getPrice(), 0.01);
        assertEquals(ride.getId(), rideNotificationDTO.getId());
    }

    @Test
    public void testFindAllActiveDrivers_WithActiveAndNotBlockedDrivers_ReturnsFilteredDrivers() {
        // Create a list of drivers with different statuses
        List<Driver> drivers = new ArrayList<>();
        drivers.add(Driver.builder().username("John").active(true).block(false).build());
        drivers.add(Driver.builder().username("Alice").active(true).block(true).build());
        drivers.add(Driver.builder().username("Bob").active(false).block(false).build());
        drivers.add(Driver.builder().username("Eve").active(false).block(true).build());

        // Stub the findAll method of the mock DriverRepository
        when(dr.findAll()).thenReturn(drivers);

        // Stub the hasDriverBeenLoggedLowerThan8HoursIn24Hours method of the mock LoginHistoryService
        when(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours("John")).thenReturn(true);
        when(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours("Alice")).thenReturn(false);
        when(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours("Bob")).thenReturn(true);
        when(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours("Eve")).thenReturn(true);

        // Call the method to be tested
        List<Driver> activeDrivers = ds.findAllActiveDrivers();

        // Assert the result
        assertEquals(1, activeDrivers.size());
        assertEquals("John", activeDrivers.get(0).getUsername());
    }

    @Test
    public void testFindAllActiveDrivers_WithNoActiveDrivers_ReturnsEmptyList() {
        // Create a list of drivers with all inactive drivers
        List<Driver> drivers = new ArrayList<>();
        drivers.add(Driver.builder().username("Bob").active(false).block(false).build());
        drivers.add(Driver.builder().username("Eve").active(false).block(true).build());

        // Stub the findAll method of the mock DriverRepository
        when(dr.findAll()).thenReturn(drivers);

        // Call the method to be tested
        List<Driver> activeDrivers = ds.findAllActiveDrivers();

        // Assert the result
        assertTrue(activeDrivers.isEmpty());
    }

    @Test
    public void testFindAllActiveDrivers_WithActiveDriversButNoDriverLoggedLowerThan8Hours_ReturnsEmptyList() {
        // Create a list of drivers with different statuses
        List<Driver> drivers = new ArrayList<>();
        drivers.add(Driver.builder().username("John").active(true).block(false).build());
        drivers.add(Driver.builder().username("Alice").active(true).block(false).build());
        drivers.add(Driver.builder().username("Bob").active(true).block(false).build());

        // Stub the findAll method of the mock DriverRepository
        when(dr.findAll()).thenReturn(drivers);

        // Stub the hasDriverBeenLoggedLowerThan8HoursIn24Hours method of the mock LoginHistoryService
        when(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours("John")).thenReturn(false);
        when(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours("Alice")).thenReturn(false);
        when(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours("Bob")).thenReturn(false);

        // Call the method to be tested
        List<Driver> activeDrivers = ds.findAllActiveDrivers();

        // Assert the result
        assertTrue(activeDrivers.isEmpty());
    }


}
