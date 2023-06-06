package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.MarkerDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RideSaveDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RoutePartDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import ftn.uns.ac.rs.NVTKTS20222023.model.Driver;
import ftn.uns.ac.rs.NVTKTS20222023.model.Ride;
import ftn.uns.ac.rs.NVTKTS20222023.model.Vehicle;
import ftn.uns.ac.rs.NVTKTS20222023.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RideServiceTest {

    @Mock
    private RideRepository rr;

    @Mock
    private CitizenRepository cr;

    @Mock
    private DriverRepository dr;

    @Mock
    private CitizenService cs;

    @Mock
    private LocationRepository lr;

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private DriverService driverService;

    @InjectMocks
    private RideService rideService;

    public RideServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPayRide_DriverNotNull() {
        // Arrange
        Long rideId = 1L;
        Long driverId = 1L;

        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setPrice(10.0);

        List<Citizen> citizens = new ArrayList<>();
        Citizen citizen1 = new Citizen();
        citizen1.setTokens(15L); // Sufficient tokens
        citizens.add(citizen1);

        Citizen citizen2 = new Citizen();
        citizen2.setTokens(3l); // Sufficient tokens
        citizens.add(citizen2);

        ride.setCitizens(citizens);

        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        when(cs.unblockAllCitizensByUsernames(anyList())).thenReturn(true);
        when(dr.findById(driverId)).thenReturn(Optional.of(new Driver()));

        // Act
        boolean paymentStatus = rideService.payRide(rideId, driverId);

        // Assert
        assertFalse(paymentStatus);
        assertEquals("REJECT", ride.getStatus()); // Ride status should be set to "REJECT"

        verify(rr, times(1)).findById(rideId);
        verify(cs, times(1)).unblockAllCitizensByUsernames(anyList());
        verify(rr, times(1)).save(ride); // rr.save(ride) should not be called
        verify(dr, times(1)).save(any(Driver.class)); // Verify that driver is saved with the updated ride

        // Additional assertions if necessary
    }

    @Test
    public void testPayRide_InsufficientTokens() {
        // Arrange
        Long rideId = 1L;
        Long driverId = 1L;

        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setPrice(10.0);

        List<Citizen> citizens = new ArrayList<>();
        Citizen citizen1 = new Citizen();
        citizen1.setTokens(5L); // Tokens are less than required
        citizens.add(citizen1);

        Citizen citizen2 = new Citizen();
        citizen2.setTokens(2L); // Tokens are less than required
        citizens.add(citizen2);

        ride.setCitizens(citizens);

        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        when(cs.unblockAllCitizensByUsernames(anyList())).thenReturn(true);
        when(dr.findById(driverId)).thenReturn(Optional.empty());
        // Act
        boolean paymentStatus = rideService.payRide(rideId, driverId);

        // Assert
        assertFalse(paymentStatus);
        assertEquals("REJECT", ride.getStatus()); // Ride status should be set to "REJECT"

        verify(rr, times(1)).findById(rideId);
        verify(cs, times(1)).unblockAllCitizensByUsernames(anyList());
        verify(rr, times(1)).save(ride);
        verifyNoInteractions(cr);
    }


    @Test
    public void testPayRide_InvalidRideId() {
        // Arrange
        Long rideId = 1L;
        Long driverId = 1L;

        when(rr.findById(rideId)).thenReturn(Optional.empty());

        // Act
        boolean paymentStatus = rideService.payRide(rideId, driverId);

        // Assert
        assertFalse(paymentStatus);

        verify(rr, times(1)).findById(rideId);
        verifyNoInteractions(cs);
        verifyNoInteractions(dr);
        verifyNoInteractions(cr);
    }

    @Test
    public void testPayRide_SuccessfulPayment() {
        // Arrange
        Long rideId = 1L;
        Long driverId = 1L;

        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setPrice(10.0);

        List<Citizen> citizens = new ArrayList<>();
        Citizen citizen1 = new Citizen();
        citizen1.setTokens(10L);
        citizens.add(citizen1);



        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        when(cs.unblockAllCitizensByUsernames(anyList())).thenReturn(true);
        when(dr.findById(driverId)).thenReturn(Optional.of(new Driver()));
        when(cr.saveAll(anyList())).thenReturn(citizens);

        // Act
        boolean paymentStatus = rideService.payRide(rideId, driverId);

        // Assert
        assertTrue(paymentStatus);
        assertEquals(10L, citizen1.getTokens()); // Tokens should be deducted

        verify(rr, times(1)).findById(rideId);
        verify(cr, times(1)).saveAll(anyList());

    }

    @Test
    public void testIsAllDriversBusy_EmptyDriverList() {
        // Prepare test data
        List<Driver> drivers = Collections.emptyList();

        // Call the method
        boolean result = rideService.isAllDriversBusy(drivers);

        // Assert the result
        assertTrue(result);
    }

    @Test
    public void testIsAllDriversBusy_AllFreeDrivers() {
        // Prepare test data
        Driver driver1 = new Driver();
        driver1.setFutureRide(null);

        Driver driver2 = new Driver();
        driver2.setFutureRide(null);

        Driver driver3 = new Driver();
        driver3.setFutureRide(null);

        List<Driver> drivers = Arrays.asList(driver1, driver2, driver3);

        // Call the method
        boolean result = rideService.isAllDriversBusy(drivers);

        // Assert the result
        assertFalse(result);
    }

    @Test
    public void testIsAllDriversBusy_SomeFreeDrivers() {
        // Prepare test data
        Driver driver1 = new Driver();
        driver1.setFutureRide(new Ride());

        Driver driver2 = new Driver();
        driver2.setFutureRide(null);

        Driver driver3 = new Driver();
        driver3.setFutureRide(new Ride());

        List<Driver> drivers = Arrays.asList(driver1, driver2, driver3);

        // Call the method
        boolean result = rideService.isAllDriversBusy(drivers);

        // Assert the result
        assertFalse(result);
    }

    @Test
    public void testIsAllDriversBusy_NoFreeDrivers() {
        // Prepare test data
        Driver driver1 = new Driver();
        driver1.setFutureRide(new Ride());

        Driver driver2 = new Driver();
        driver2.setFutureRide(new Ride());

        Driver driver3 = new Driver();
        driver3.setFutureRide(new Ride());

        List<Driver> drivers = Arrays.asList(driver1, driver2, driver3);

        // Call the method
        boolean result = rideService.isAllDriversBusy(drivers);

        // Assert the result
        assertTrue(result);
    }


    @Test
    public void testGetAllCurentFreeDrivers_DriversWithCurrentRide() {
        // Prepare test data
        Driver driver1 = new Driver();
        driver1.setCurrentRide(new Ride());

        Driver driver2 = new Driver();
        driver2.setCurrentRide(new Ride());

        Driver driver3 = new Driver();
        driver3.setCurrentRide(new Ride());

        List<Driver> drivers = Arrays.asList(driver1, driver2, driver3);

        // Call the method
        List<Driver> result = rideService.getAllCurentFreeDrivers(drivers);

        // Assert the result
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllCurentFreeDrivers_EmptyDriverList() {
        // Prepare test data
        List<Driver> drivers = Collections.emptyList();

        // Call the method
        List<Driver> result = rideService.getAllCurentFreeDrivers(drivers);

        // Assert the result
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllCurentFreeDrivers_DriversWithNoCurrentRide() {
        // Prepare test data
        Driver driver1 = new Driver();
        driver1.setCurrentRide(null);

        Driver driver2 = new Driver();
        driver2.setCurrentRide(null);

        Driver driver3 = new Driver();
        driver3.setCurrentRide(null);

        List<Driver> drivers = Arrays.asList(driver1, driver2, driver3);

        // Call the method
        List<Driver> result = rideService.getAllCurentFreeDrivers(drivers);

        // Assert the result
        assertEquals(3, result.size());
        assertTrue(result.contains(driver1));
        assertTrue(result.contains(driver2));
        assertTrue(result.contains(driver3));
    }

    @Test
    public void testGetAllCurrentFreeDrivers_MixedDriversWithAndWithoutCurrentRide() {
        // Prepare test data
        Driver driver1 = new Driver();
        driver1.setCurrentRide(new Ride());

        Driver driver2 = new Driver();
        driver2.setCurrentRide(null);

        Driver driver3 = new Driver();
        driver3.setCurrentRide(new Ride());

        Driver driver4 = new Driver();
        driver4.setCurrentRide(null);

        List<Driver> drivers = Arrays.asList(driver1, driver2, driver3, driver4);

        // Call the method
        List<Driver> result = rideService.getAllCurentFreeDrivers(drivers);

        // Assert the result
        assertEquals(2, result.size());
        assertTrue(result.contains(driver2));
        assertTrue(result.contains(driver4));
        assertFalse(result.contains(driver1));
        assertFalse(result.contains(driver3));
    }

    @Test
    public void testGetAllCurrentFreeDrivers_AllDriversWithCurrentRide() {
        // Prepare test data
        Driver driver1 = new Driver();
        driver1.setCurrentRide(new Ride());

        Driver driver2 = new Driver();
        driver2.setCurrentRide(new Ride());

        Driver driver3 = new Driver();
        driver3.setCurrentRide(new Ride());

        List<Driver> drivers = Arrays.asList(driver1, driver2, driver3);

        // Call the method
        List<Driver> result = rideService.getAllCurentFreeDrivers(drivers);

        // Assert the result
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllFutureFreeDrivers_AllDriversWithFutureRide() {
        // Prepare test data
        Driver driver1 = new Driver();
        driver1.setFutureRide(new Ride());

        Driver driver2 = new Driver();
        driver2.setFutureRide(new Ride());

        Driver driver3 = new Driver();
        driver3.setFutureRide(new Ride());

        List<Driver> drivers = Arrays.asList(driver1, driver2, driver3);

        // Call the method
        List<Driver> result = rideService.getAllFutureFreeDrivers(drivers);

        // Assert the result
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllFutureFreeDrivers_MixedDriversWithAndWithoutFutureRide() {
        // Prepare test data
        Driver driver1 = new Driver();
        driver1.setFutureRide(new Ride());

        Driver driver2 = new Driver();
        driver2.setFutureRide(null);

        Driver driver3 = new Driver();
        driver3.setFutureRide(new Ride());

        Driver driver4 = new Driver();
        driver4.setFutureRide(null);

        List<Driver> drivers = Arrays.asList(driver1, driver2, driver3, driver4);

        // Call the method
        List<Driver> result = rideService.getAllFutureFreeDrivers(drivers);

        // Assert the result
        assertEquals(2, result.size());
        assertTrue(result.contains(driver2));
        assertTrue(result.contains(driver4));
        assertFalse(result.contains(driver1));
        assertFalse(result.contains(driver3));
    }

    @Test
    public void testGetAllFutureFreeDrivers_EmptyDriverList() {
        // Prepare test data
        List<Driver> drivers = Collections.emptyList();

        // Call the method
        List<Driver> result = rideService.getAllFutureFreeDrivers(drivers);

        // Assert the result
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllFutureFreeDrivers_DriversWithFutureRide() {
        // Prepare test data
        Driver driver1 = new Driver();
        driver1.setFutureRide(new Ride());

        Driver driver2 = new Driver();
        driver2.setFutureRide(new Ride());

        Driver driver3 = new Driver();
        driver3.setFutureRide(new Ride());

        List<Driver> drivers = Arrays.asList(driver1, driver2, driver3);

        // Call the method
        List<Driver> result = rideService.getAllFutureFreeDrivers(drivers);

        // Assert the result
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllFutureFreeDrivers_DriversWithNoFutureRide() {
        // Prepare test data
        Driver driver1 = new Driver();
        driver1.setFutureRide(null);

        Driver driver2 = new Driver();
        driver2.setFutureRide(null);

        Driver driver3 = new Driver();
        driver3.setFutureRide(null);

        List<Driver> drivers = Arrays.asList(driver1, driver2, driver3);

        // Call the method
        List<Driver> result = rideService.getAllFutureFreeDrivers(drivers);

        // Assert the result
        assertEquals(3, result.size());
        assertTrue(result.contains(driver1));
        assertTrue(result.contains(driver2));
        assertTrue(result.contains(driver3));
    }

    @Test
    public void testFilterDriversForRide_NoMatchingDriversFound() {
        // Prepare test data
        Ride ride = new Ride();
        ride.setBabyFriendly(true);
        ride.setPetFriendly(true);
        ride.setType("Sedan");

        Driver driver1 = new Driver();
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setBabyFriendly(false);
        vehicle1.setPetFriendly(true);
        vehicle1.setType("Sedan");
        driver1.setVehicle(vehicle1);

        Driver driver2 = new Driver();
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setBabyFriendly(true);
        vehicle2.setPetFriendly(false);
        vehicle2.setType("Sedan");
        driver2.setVehicle(vehicle2);

        Driver driver3 = new Driver();
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setBabyFriendly(true);
        vehicle3.setPetFriendly(true);
        vehicle3.setType("SUV");
        driver3.setVehicle(vehicle3);

        Mockito.when(driverService.findAllActiveDrivers()).thenReturn(Arrays.asList(driver1, driver2, driver3));

        // Call the method
        List<Driver> result = rideService.filterDriversForRide(ride);

        // Assert the result
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFilterDriversForRide_EmptyDriverList() {
        // Prepare test data
        Ride ride = new Ride();
        ride.setBabyFriendly(true);
        ride.setPetFriendly(true);
        ride.setType("Sedan");

        Mockito.when(driverService.findAllActiveDrivers()).thenReturn(Collections.emptyList());

        // Call the method
        List<Driver> result = rideService.filterDriversForRide(ride);

        // Assert the result
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFilterDriversForRide_MatchingDriversFound() {
        // Prepare test data
        Ride ride = new Ride();
        ride.setBabyFriendly(true);
        ride.setPetFriendly(true);
        ride.setType("Sedan");

        Driver driver1 = new Driver();
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setBabyFriendly(true);
        vehicle1.setPetFriendly(true);
        vehicle1.setType("Sedan");
        driver1.setVehicle(vehicle1);

        Driver driver2 = new Driver();
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setBabyFriendly(true);
        vehicle2.setPetFriendly(true);
        vehicle2.setType("Sedan");
        driver2.setVehicle(vehicle2);

        Driver driver3 = new Driver();
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setBabyFriendly(true);
        vehicle3.setPetFriendly(true);
        vehicle3.setType("SUV");
        driver3.setVehicle(vehicle3);

        Mockito.when(driverService.findAllActiveDrivers()).thenReturn(Arrays.asList(driver1, driver2, driver3));


        // Call the method
        List<Driver> result = rideService.filterDriversForRide(ride);

        // Assert the result
        assertEquals(2, result.size());
        assertTrue(result.contains(driver1));
        assertTrue(result.contains(driver2));
        assertFalse(result.contains(driver3));
    }

    @Test
    public void testIsRideExpired_RideJustExpired() {
        // Prepare test data
        Ride ride = new Ride();
        long currentTime = new Date().getTime();
        long startTime = currentTime - 15 * 60 * 1000;
        ride.setStart(startTime);

        // Call the method
        boolean result = rideService.isRideExpired(ride);

        // Assert the result
        assertFalse(result);
    }

    @Test
    public void testIsRideExpired_RideExpired() {
        // Prepare test data
        Ride ride = new Ride();
        long currentTime = new Date().getTime();
        long startTime = currentTime + 20 * 60 * 1000;
        ride.setStart(startTime);

        // Call the method
        boolean result = rideService.isRideExpired(ride);

        // Assert the result
        assertTrue(result);
    }

    @Test
    public void testIsRideExpired_RideNotExpired() {
        // Prepare test data
        Ride ride = new Ride();
        long currentTime = new Date().getTime();
        long startTime = currentTime + 10 * 60 * 1000;
        ride.setStart(startTime);

        // Call the method
        boolean result = rideService.isRideExpired(ride);

        // Assert the result
        assertFalse(result);
    }

    @Test
    public void testInCitizenList_NullUsername() {
        // Prepare test data
        List<Citizen> citizens = new ArrayList<>();
        citizens.add(Citizen.builder().username("user1").firstName("John").lastName("Doe").build());
        citizens.add(Citizen.builder().username("user2").firstName("Jane").lastName("Smith").build());
        String username = null;

        // Call the method
        boolean result = rideService.inCitizenList(citizens, username);

        // Assert the result
        assertFalse(result);
    }

    @Test
    public void testInCitizenList_EmptyCitizenList() {
        // Prepare test data
        List<Citizen> citizens = new ArrayList<>();
        String username = "user1";

        // Call the method
        boolean result = rideService.inCitizenList(citizens, username);

        // Assert the result
        assertFalse(result);
    }

    @Test
    public void testInCitizenList_CitizenDoesNotExist() {
        // Prepare test data
        List<Citizen> citizens = new ArrayList<>();
        citizens.add(Citizen.builder().username("user1").firstName("John").lastName("Doe").build());
        citizens.add(Citizen.builder().username("user2").firstName("Jane").lastName("Smith").build());
        String username = "user3";

        // Call the method
        boolean result = rideService.inCitizenList(citizens, username);

        // Assert the result
        assertFalse(result);
    }

    @Test
    public void testInCitizenList_CitizenExists() {
        // Prepare test data
        List<Citizen> citizens = new ArrayList<>();
        citizens.add(Citizen.builder().username("user1").firstName("John").lastName("Doe").build());
        citizens.add(Citizen.builder().username("user2").firstName("Jane").lastName("Smith").build());
        String username = "user1";

        // Call the method
        boolean result = rideService.inCitizenList(citizens, username);
        // Assert the result
        assertTrue(result);

    }

    @Test
    public void testSetEndDate_Success() {
        // Mock data
        Ride ride = new Ride();
        int offset = 30;

        // Call the method
        Ride result = rideService.setEndDate(ride, offset);

        // Verify the assertions
        assertNotNull(result);
        assertNotNull(result.getEndDate());

        // Calculate expected end date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, offset + ride.getAllLocations().size());
        Date expectedEndDate = calendar.getTime();

        assertEquals(expectedEndDate.getTime(), result.getEndDate(),10);
    }

    @Test
    public void testSetEndDate_NegativeOffset() {
        // Mock data
        Ride ride = new Ride();
        int offset = -30;

        // Call the method
        Ride result = rideService.setEndDate(ride, offset);

        // Verify the assertions
        assertNotNull(result);
        assertNotNull(result.getEndDate());

        // Calculate expected end date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, offset + ride.getAllLocations().size());
        Date expectedEndDate = calendar.getTime();

        assertEquals(expectedEndDate.getTime(), result.getEndDate(),10);
    }

    @Test
    public void testSetEndDate_ZeroOffset() {
        // Mock data
        Ride ride = new Ride();
//        ride.setAllLocations(new ArrayList<>());
        int offset = 0;

        // Call the method
        Ride result = rideService.setEndDate(ride, offset);


        // Calculate expected end date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, offset + ride.getAllLocations().size());
        Date expectedEndDate = calendar.getTime();

        // Verify the assertions
        assertNotNull(result);
        assertNotNull(result.getEndDate());

        assertEquals(expectedEndDate.getTime(), result.getEndDate());
    }

    @Test
    public void testSetStartDate_ZeroOffset() {
        // Mock data
        Ride ride = new Ride();
        int offset = 0;

        // Call the method
        Ride result = rideService.setStartDate(ride, offset);


        // Calculate expected start date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, offset);
        Date expectedStartDate = calendar.getTime();

        // Verify the assertions
        assertNotNull(result);
        assertNotNull(result.getStart());

        assertEquals(expectedStartDate.getTime(), result.getStart(),10);
    }

    @Test
    public void testSetStartDate_NegativeOffset() {
        // Mock data
        Ride ride = new Ride();
        int offset = -30;

        // Call the method
        Ride result = rideService.setStartDate(ride, offset);

        // Verify the assertions
        assertNotNull(result);
        assertNotNull(result.getStart());

        // Calculate expected start date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, offset);
        Date expectedStartDate = calendar.getTime();

        assertEquals(expectedStartDate.getTime(), result.getStart(),10);
    }

    @Test
    public void testSetStartDate_Success() {
        // Mock data
        Ride ride = new Ride();
        int offset = 30;

        // Call the method
        Ride result = rideService.setStartDate(ride, offset);

        // Calculate expected start date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, offset);
        Date expectedStartDate = calendar.getTime();

        // Verify the assertions
        assertNotNull(result);
        assertNotNull(result.getStart());

        assertEquals(expectedStartDate.getTime(), result.getStart(),10);
    }

    @Test
    public void testCreateBasicRide_NullRideSaveDTO() {
        // Call the method
        Ride result = rideService.createBasicRide(null);

        // Verify the calls and assertions
        verifyNoInteractions(rr);

        assertNull(result);
    }

    @Test
    public void testCreateBasicRide_ZeroDistance() {
        // Mock data
        RideSaveDTO rideSaveDTO = new RideSaveDTO();
        rideSaveDTO.setDistance(0l);
        rideSaveDTO.setPrice(0l);

        // Mock repository methods
        when(rr.save(any(Ride.class))).thenReturn(new Ride());

        // Call the method
        Ride result = rideService.createBasicRide(rideSaveDTO);

        // Verify the calls and assertions
        verify(rr, times(1)).save(any(Ride.class));

        assertNotNull(result);
        assertEquals(0l, result.getMeters());
    }

    @Test
    public void testCreateBasicRide_EmptyRideSaveDTO() {
        // Mock data
        RideSaveDTO rideSaveDTO = new RideSaveDTO();
        rideSaveDTO.setPrice(0l);
        // Call the method
        Ride result = rideService.createBasicRide(rideSaveDTO);

        // Verify the calls and assertions
//        verifyNoInteractions(rr);

        assertNotNull(result);
        assertFalse(result.isPetFriendly());
        assertFalse(result.isBabyFriendly());
        assertNull(result.getType());
        assertEquals("CREATE", result.getStatus());
        assertEquals(null, result.getMeters());
        assertNull(result.getName());
        assertEquals(0.0, result.getPrice(), 0.001);
    }

    @Test
    public void testCreateBasicRide_Success() {
        // Mock data
        RideSaveDTO rideSaveDTO = new RideSaveDTO();
        rideSaveDTO.setPets(true);
        rideSaveDTO.setBaby(true);
        rideSaveDTO.setCar_type("SUV");
        rideSaveDTO.setDistance(1000l);
        rideSaveDTO.setName("Test Ride");
        rideSaveDTO.setPrice(20l);
        rideSaveDTO.setMinutes(30);

        // Mock repository methods
        when(rr.save(any(Ride.class))).thenReturn(new Ride());

        // Call the method
        Ride result = rideService.createBasicRide(rideSaveDTO);

        // Verify the calls and assertions
        verify(rr, times(1)).save(any(Ride.class));

        assertNotNull(result);
        assertEquals(true, result.isPetFriendly());
        assertEquals(true, result.isBabyFriendly());
        assertEquals("SUV", result.getType());
        assertEquals("CREATE", result.getStatus());
        assertEquals(1000, result.getMeters());
        assertEquals("Test Ride", result.getName());
        assertEquals(20.0, result.getPrice(), 0.001);
        // Additional assertions for setStartDate and setEndDate results can be added
    }


    @Test
    public void testAddRouteToRide_NullRide() {
        // Mock data
        Long rideId = 1L;
        List<RoutePartDTO> parts = new ArrayList<>();

        // Mock repository methods
        when(rr.findById(rideId)).thenReturn(Optional.empty());

        // Call the method
        boolean result = rideService.addRouteToRide(rideId, parts);

        // Verify the calls and assertions
        verify(rr, times(1)).findById(rideId);
        verifyNoMoreInteractions(routeRepository, lr);

        assertFalse(result);
    }

    @Test
    public void testAddRouteToRide_EmptyCoordinatesList() {
        // Mock data
        Long rideId = 1L;
        List<RoutePartDTO> parts = new ArrayList<>();

        // Create a ride
        Ride ride = new Ride();
        ride.setId(rideId);

        // Create route part with empty coordinates
        RoutePartDTO part1 = new RoutePartDTO();
        part1.setId(1L);
        part1.setCoordinates(new ArrayList<>());
        parts.add(part1);

        // Mock repository methods
        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        when(lr.saveAll(any())).thenReturn(new ArrayList<>());
        when(routeRepository.saveAll(any())).thenReturn(new ArrayList<>());

        // Call the method
        boolean result = rideService.addRouteToRide(rideId, parts);

        // Verify the calls and assertions
        verify(rr, times(1)).findById(rideId);
        verify(rr, times(1)).save(ride);
        verify(routeRepository, times(1)).saveAll(any());
        verify(lr, times(1)).saveAll(any());

        assertTrue(result);
        assertEquals(1, ride.getRoutes().size());
        assertEquals(ride, ride.getRoutes().get(0).getRide());
        assertEquals(0, ride.getRoutes().get(0).getLocations().size());
    }

    @Test
    public void testAddRouteToRide_EmptyPartsList() {
        // Mock data
        Long rideId = 1L;
        List<RoutePartDTO> parts = new ArrayList<>();

        // Mock repository methods
        when(rr.findById(rideId)).thenReturn(Optional.empty());

        // Call the method
        boolean result = rideService.addRouteToRide(rideId, parts);

        // Verify the calls and assertions
        verify(rr, times(1)).findById(rideId);
        verifyNoMoreInteractions(routeRepository, lr);

        assertFalse(result);
    }

    @Test
    public void testAddRouteToRide_Success() {
        // Mock data
        Long rideId = 1L;
        List<RoutePartDTO> parts = new ArrayList<>();

        // Create a ride
        Ride ride = new Ride();
        ride.setId(rideId);

        // Create route parts
        RoutePartDTO part1 = new RoutePartDTO();
        part1.setId(1L);
        List<MarkerDTO> coordinates1 = new ArrayList<>();
        coordinates1.add(new MarkerDTO(37.7749, -122.4194));
        part1.setCoordinates(coordinates1);
        parts.add(part1);

        // Mock repository methods
        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        when(lr.saveAll(any())).thenReturn(new ArrayList<>());
        when(routeRepository.saveAll(any())).thenReturn(new ArrayList<>());

        // Call the method
        boolean result = rideService.addRouteToRide(rideId, parts);

        // Verify the calls and assertions
        verify(rr, times(1)).findById(rideId);
        verify(rr, times(1)).save(ride);
        verify(routeRepository, times(1)).saveAll(any());
        verify(lr, times(1)).saveAll(any());

        assertTrue(result);
        assertEquals(1, ride.getRoutes().size());
        assertEquals(ride, ride.getRoutes().get(0).getRide());
    }

    @Test
    public void testAddRouteToRide_RideNotFound() {
        // Mock data
        Long rideId = 1L;
        List<RoutePartDTO> parts = new ArrayList<>();

        // Mock repository methods
        when(rr.findById(rideId)).thenReturn(Optional.empty());

        // Call the method
        boolean result = rideService.addRouteToRide(rideId, parts);

        // Verify the calls and assertions
        verify(rr, times(1)).findById(rideId);
        verifyNoMoreInteractions(routeRepository, lr);

        assertFalse(result);
    }


    @Test
    void testAddUsersToRide_Success() {
        // Prepare test data
        Long rideId = 1L;
        List<String> users = List.of("user1", "user2");

        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setName("Test Ride");
        ride.setPrice(20.0);

        Citizen citizen1 = new Citizen();
        citizen1.setUsername("user1@example.com");
        Citizen citizen2 = new Citizen();
        citizen2.setUsername("user2@example.com");

        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        when(cr.findByUsername("user1")).thenReturn(citizen1);
        when(cr.findByUsername("user2")).thenReturn(citizen2);
        when(cs.blockAllCitizensByUsernames(anyList())).thenReturn(true);

        // Perform the test
        boolean result = rideService.addUsersToRide(rideId, users);

        // Verify the result
        assertTrue(result);
        assertEquals(ride.getCitizens().size(), 2);
        assertTrue(ride.getCitizens().contains(citizen1));
        assertTrue(ride.getCitizens().contains(citizen2));

        verify(rr, times(1)).findById(rideId);
        verify(cr, times(2)).findByUsername(anyString());
        verify(cs, times(1)).blockAllCitizensByUsernames(anyList());
        verify(rr, times(1)).save(ride);
        verify(cr, times(1)).saveAll(anyList());
//        verify(cr, times(2)).save(any(Citizen.class));
    }

    @Test
    void testAddUsersToRide_RideNotFound() {
        // Prepare test data
        Long rideId = 1L;
        List<String> users = List.of("user1", "user2");

        when(rr.findById(rideId)).thenReturn(Optional.empty());

        // Perform the test
        boolean result = rideService.addUsersToRide(rideId, users);

        // Verify the result
        assertFalse(result);

        verify(rr, times(1)).findById(rideId);
        verifyNoMoreInteractions(cr);
        verifyNoMoreInteractions(cs);
        verifyNoMoreInteractions(rr);
    }


    @Test
    void testAddUsersToRide_UserNotFound() {
        // Prepare test data
        Long rideId = 1L;
        List<String> users = List.of("user1", "user2");

        Ride ride = new Ride();
        ride.setId(rideId);

        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        when(cr.findByUsername(anyString())).thenReturn(null); // Mock the findByUsername method to return null for any input

        // Perform the test
        boolean result = rideService.addUsersToRide(rideId, users);

        // Verify the result
        assertFalse(result);

        verify(rr, times(1)).findById(rideId);
        verify(cr, times(2)).findByUsername(anyString()); // Verify that the findByUsername method is called twice
        verifyNoMoreInteractions(cr);
        verifyNoMoreInteractions(cs);
        verify(rr, times(1)).delete(ride);
        verifyNoMoreInteractions(rr);
    }

    @Test
    void testAddUsersToRide_NotAllUsersFound() {
        // Prepare test data
        Long rideId = 1L;
        List<String> users = List.of("user1", "user2", "user3");

        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setName("Test Ride");
        ride.setPrice(20.0);

        Citizen citizen1 = new Citizen();
        citizen1.setUsername("user1@example.com");
        Citizen citizen3 = new Citizen();
        citizen3.setUsername("user3@example.com");

        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        when(cr.findByUsername("user1")).thenReturn(citizen1);
        when(cr.findByUsername("user2")).thenReturn(null);
        when(cr.findByUsername("user3")).thenReturn(citizen3);

        // Perform the test
        boolean result = rideService.addUsersToRide(rideId, users);

        // Verify the result
        assertFalse(result);
        assertEquals(0, ride.getCitizens().size());

        verify(rr, times(1)).findById(rideId);
        verify(cr, times(3)).findByUsername(anyString());
        verify(rr, times(1)).delete(ride);
        verifyNoMoreInteractions(cr);
        verifyNoMoreInteractions(cs);
        verifyNoMoreInteractions(rr);
    }

    @Test
    void testAddUsersToRide_BlockingCitizensFailed() {
        // Prepare test data
        Long rideId = 1L;
        List<String> users = List.of("user1", "user2");

        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setName("Test Ride");
        ride.setPrice(20.0);

        Citizen citizen1 = new Citizen();
        citizen1.setUsername("user1@example.com");
        Citizen citizen2 = new Citizen();
        citizen2.setUsername("user2@example.com");
        citizen2.setBlock(true);

        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        when(cr.findByUsername("user1")).thenReturn(citizen1);
        when(cr.findByUsername("user2")).thenReturn(citizen2);
        when(cs.blockAllCitizensByUsernames(anyList())).thenReturn(false);

        // Perform the test
        boolean result = rideService.addUsersToRide(rideId, users);

        // Verify the result
        assertFalse(result);
        assertEquals(0, ride.getCitizens().size());

        verify(rr, times(1)).findById(rideId);
        verify(cr, times(2)).findByUsername(anyString());
        verify(cs, times(0)).blockAllCitizensByUsernames(anyList());
        verify(rr, times(1)).delete(ride);
        verifyNoMoreInteractions(cr);
        verifyNoMoreInteractions(cs);
        verifyNoMoreInteractions(rr);
    }

    @Test
    void testAddUsersToRide_EmptyUserList() {
        // Prepare test data
        Long rideId = 1L;
        List<String> users = new ArrayList<>();

        // Perform the test
        boolean result = rideService.addUsersToRide(rideId, users);

        // Verify the result
        assertFalse(result);

        verify(rr, times(1)).findById(rideId);
//        verifyNoInteractions(rr);
        verifyNoInteractions(cr);
        verifyNoInteractions(cs);
    }

    @Test
    void testAddUsersToRide_CitizensAlreadyExist() {
        // Prepare test data
        Long rideId = 1L;
        List<String> users = List.of("user1", "user2");

        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setName("Test Ride");
        ride.setPrice(20.0);

        Citizen citizen1 = new Citizen();
        citizen1.setUsername("user1@example.com");
        Citizen citizen2 = new Citizen();
        citizen2.setUsername("user2@example.com");

        ride.setCitizens(List.of(citizen1, citizen2));

        when(rr.findById(rideId)).thenReturn(Optional.of(ride));

        // Perform the test
        boolean result = rideService.addUsersToRide(rideId, users);

        // Verify the result
        assertFalse(result);
        assertEquals(2, ride.getCitizens().size());

        verify(rr, times(1)).findById(rideId);
        verifyNoInteractions(cs);
    }

    @Test
    void testAddUsersToRide_NoRideId() {
        // Prepare test data
        Long rideId = null;
        List<String> users = List.of("user1", "user2");

        // Perform the test
        boolean result = rideService.addUsersToRide(rideId, users);

        // Verify the result
        assertFalse(result);

        verifyNoInteractions(cr);
        verifyNoInteractions(cs);
    }

}
