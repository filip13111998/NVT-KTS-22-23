package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RideSaveDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RoutePartDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideNotificationDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.*;
import ftn.uns.ac.rs.NVTKTS20222023.repository.DriverRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.RideRepository;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RideServiceSpyTest {

    @Mock
    private RideRepository rr;

    @Mock
    private CitizenService cs;

    @Mock
    private DriverService ds;

    @Mock
    private DriverRepository dr;

    @Mock
    private LocationService ls;

    @InjectMocks
    private RideService rideService;

    @Test
    public void testFindDriver_NonExistentRideId() {
        // Mock data
        Long rideId = 123L;
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setStatus("PENDING");

        // Mock repository method calls
        when(rr.findById(rideId)).thenReturn(Optional.empty());

        // Execute the method
        boolean result = rideService.findDriver(rideId);

        // Verify the results
        assertFalse(result);

    }

    @Test
    public void testFindDriver_ExpiredRide() {
        // Mock data
        Long rideId = 123L;
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setStatus("PENDING");

        RideService spyRideService = Mockito.spy(rideService);

        // Mock repository method calls
        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        Mockito.doReturn(true).when(spyRideService).isRideExpired(ride);

        // Execute the method
        boolean result = spyRideService.findDriver(rideId);

        // Verify the results
        assertFalse(result);

    }

    @Test
    public void testFindDriver_RideWithNoDrivers() {
        // Mock data
        Long rideId = 123L;
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setStatus("PENDING");

        RideService spyRideService = Mockito.spy(rideService);

        // Mock repository method calls
        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        Mockito.doReturn(false).when(spyRideService).isRideExpired(ride);
        Mockito.doReturn(Collections.emptyList()).when(spyRideService).filterDriversForRide(ride);

        // Execute the method
        boolean result = spyRideService.findDriver(rideId);

        // Verify the results
        assertFalse(result);

    }

    @Test
    public void testFindDriver_RideWithBusyDrivers() {
        // Mock data
        Long rideId = 123L;
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setStatus("PENDING");

       // Create a list of available drivers
        List<Driver> availableDrivers = new ArrayList<>();

        Driver driver1 = new Driver();
        driver1.setUsername("driver1");
        driver1.setBlock(false);
        driver1.setActive(true);

        Driver driver2 = new Driver();
        driver2.setUsername("driver2");
        driver2.setBlock(false);
        driver2.setActive(true);

        availableDrivers.add(driver1);
        availableDrivers.add(driver2);

        RideService spyRideService = Mockito.spy(rideService);

        // Mock repository method calls
        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        Mockito.doReturn(false).when(spyRideService).isRideExpired(ride);
        Mockito.doReturn(availableDrivers).when(spyRideService).filterDriversForRide(ride);
        Mockito.doReturn(true).when(spyRideService).isAllDriversBusy(availableDrivers);

        // Execute the method
        boolean result = spyRideService.findDriver(rideId);

        // Verify the results
        assertFalse(result);

    }

    @Test
    public void testFindDriver_ValidRideIdWithNoAvailableDrivers() {
        // Mock data
        Long rideId = 123L;
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setStatus("PENDING");
//        ride.setStart(new Date().getTime()); // Set the start time to the current time
        // Create a list of available drivers
        List<Driver> availableDrivers = new ArrayList<>();

        Driver driver1 = new Driver();
        driver1.setUsername("driver1");
        driver1.setBlock(false);
        driver1.setActive(true);

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setLocation(Location.builder().longitude(12.22).latitude(23.33).build());

        driver1.setVehicle(vehicle1);

        Driver driver2 = new Driver();
        driver2.setUsername("driver2");
        driver2.setBlock(false);
        driver2.setActive(true);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setLocation(Location.builder().longitude(43.33).latitude(15.33).build());

        driver2.setVehicle(vehicle2);

        availableDrivers.add(driver1);
        availableDrivers.add(driver2);

        RideService spyRideService = Mockito.spy(rideService);

        // Mock repository method calls
        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        Mockito.doReturn(false).when(spyRideService).isRideExpired(ride);
        Mockito.doReturn(availableDrivers).when(spyRideService).filterDriversForRide(ride);
        Mockito.doReturn(false).when(spyRideService).isAllDriversBusy(availableDrivers);
        Mockito.doReturn(Collections.emptyList()).when(spyRideService).getAllCurentFreeDrivers(availableDrivers);
        Mockito.doReturn(Collections.emptyList()).when(spyRideService).getAllFutureFreeDrivers(availableDrivers);
        Mockito.doReturn(true).when(spyRideService).payRide(rideId, driver1.getId());


        // Mock location service
        when(ls.getDistance(any(Location.class), any(Location.class))).thenReturn(10.0);

        // Execute the method
        boolean result = spyRideService.findDriver(rideId);

        // Verify the results
        assertFalse(result);

    }


    @Test
    public void testFindDriver_RideWithAvailableFutureDrivers() {
        // Mock data
        Long rideId = 123L;
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setStatus("PENDING");

        // Create a list of available future drivers
        List<Driver> availableFutureDrivers = new ArrayList<>();

        Driver driver1 = new Driver();
        driver1.setUsername("driver1");
        driver1.setBlock(false);
        driver1.setActive(true);
        driver1.setCounter(2);

        Driver driver2 = new Driver();
        driver2.setUsername("driver2");
        driver2.setBlock(false);
        driver2.setActive(true);
        driver2.setCounter(2);

        availableFutureDrivers.add(driver1);
        availableFutureDrivers.add(driver2);

        Location locationStart = Location.builder().longitude(55.22).latitude(23.55).build();
        Location locationEnd = Location.builder().longitude(66.22).latitude(66.55).build();

        List<Location> locations = new ArrayList<>();
        locations.add(locationStart);
        locations.add(locationEnd);

        Route route = new Route();
        route.setLocations(locations);

        List<Route> routes = new ArrayList<>();
        routes.add(route);
        ride.setRoutes(routes);

        // Mock data
        Ride ride2 = new Ride();
        ride2.setId(32l);
        ride2.setStatus("START");

        Location locationStart2 = Location.builder().longitude(77.22).latitude(88.55).build();
        Location locationEnd2 = Location.builder().longitude(44.22).latitude(88.55).build();

        List<Location> locations2 = new ArrayList<>();
        locations2.add(locationStart2);
        locations2.add(locationEnd2);

        Route route2 = new Route();
        route2.setLocations(locations2);

        List<Route> routes2 = new ArrayList<>();
        routes2.add(route2);
        ride2.setRoutes(routes2);

        driver1.setCurrentRide(ride2);

        // Mock data
        Ride ride3 = new Ride();
        ride3.setId(5454l);
        ride3.setStatus("START");

        Location locationStart3 = Location.builder().longitude(12.9).latitude(66.9).build();
        Location locationEnd3 = Location.builder().longitude(12.78).latitude(69.08).build();

        List<Location> locations3 = new ArrayList<>();
        locations3.add(locationStart3);
        locations3.add(locationEnd3);

        Route route3 = new Route();
        route3.setLocations(locations3);

        List<Route> routes3 = new ArrayList<>();
        routes3.add(route3);
        ride3.setRoutes(routes3);

        driver2.setCurrentRide(ride3);

        RideService spyRideService = Mockito.spy(rideService);

        // Mock repository method calls
        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        doReturn(false).when(spyRideService).isRideExpired(ride);
        doReturn(availableFutureDrivers).when(spyRideService).filterDriversForRide(ride);
        doReturn(false).when(spyRideService).isAllDriversBusy(anyList());
        doReturn(Collections.emptyList()).when(spyRideService).getAllCurentFreeDrivers(anyList());
        doReturn(availableFutureDrivers).when(spyRideService).getAllFutureFreeDrivers(anyList());
        doReturn(true).when(spyRideService).payRide(rideId, driver1.getId());

        // Mock location service
        when(ls.getDistance(any(Location.class), any(Location.class))).thenReturn(10.0);

        // Execute the method
        boolean result = spyRideService.findDriver(rideId);

        // Verify the results
        assertTrue(result);
        assertEquals("PAID", ride.getStatus());
        assertNotNull(ride.getDriver());
        assertEquals(driver1, ride.getDriver());
        assertEquals(ride, driver1.getFutureRide());
        verify(rr, times(1)).save(ride);
        verify(dr, times(1)).save(driver1);
        verify(spyRideService, times(1)).payRide(rideId, driver1.getId());
    }

    @Test
    public void testFindDriver_ValidRideIdWithAvailableDrivers() {
        // Mock data
        Long rideId = 123L;
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setStatus("PENDING");
//        ride.setStart(new Date().getTime()); // Set the start time to the current time
        // Create a list of available drivers
        List<Driver> availableDrivers = new ArrayList<>();

        Driver driver1 = new Driver();
        driver1.setUsername("driver1");
        driver1.setBlock(false);
        driver1.setActive(true);

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setLocation(Location.builder().longitude(12.22).latitude(23.33).build());

        driver1.setVehicle(vehicle1);

        Driver driver2 = new Driver();
        driver2.setUsername("driver2");
        driver2.setBlock(false);
        driver2.setActive(true);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setLocation(Location.builder().longitude(43.33).latitude(15.33).build());

        driver2.setVehicle(vehicle2);

        availableDrivers.add(driver1);
        availableDrivers.add(driver2);


        Location locationStart = Location.builder().longitude(55.22).latitude(23.55).build();
        Location locationEnd = Location.builder().longitude(66.22).latitude(66.55).build();

        List<Location> locations = new ArrayList<>();

        locations.add(locationStart);
        locations.add(locationEnd);

        Route route = new Route();
        route.setLocations(locations);

        List<Route> routes = new ArrayList<>();
        routes.add(route);
        ride.setRoutes(routes);
//        driver1.setCurrentRide(ride);

        RideService spyRideService = Mockito.spy(rideService);

        // Mock repository method calls
        when(rr.findById(rideId)).thenReturn(Optional.of(ride));
        Mockito.doReturn(false).when(spyRideService).isRideExpired(ride);
        Mockito.doReturn(availableDrivers).when(spyRideService).filterDriversForRide(ride);
        Mockito.doReturn(false).when(spyRideService).isAllDriversBusy(availableDrivers);
        Mockito.doReturn(availableDrivers).when(spyRideService).getAllCurentFreeDrivers(availableDrivers);
        Mockito.doReturn(true).when(spyRideService).payRide(rideId, driver1.getId());


        // Mock location service
        when(ls.getDistance(any(Location.class), any(Location.class))).thenReturn(10.0);

        // Execute the method
        boolean result = spyRideService.findDriver(rideId);

        // Verify the results
        assertTrue(result);
        assertEquals("PAID", ride.getStatus());
        assertNotNull(ride.getDriver());
        assertEquals(driver1, ride.getDriver());
        assertEquals(ride, driver1.getFutureRide());
        verify(rr, times(1)).save(ride);
        verify(dr, times(1)).save(driver1);
        verify(spyRideService, times(1)).payRide(rideId, driver1.getId());

   }


    @Test
    public void testCitizenNewRide_RideNotFound() {
        // Prepare test data
        String username = "user1";

        List<Ride> rides = Collections.emptyList();

        RideService spyRideService = Mockito.spy(rideService);
        Mockito.doReturn(false).when(spyRideService).inCitizenList(Mockito.anyList(), Mockito.eq(username));

        Mockito.when(rr.findAll()).thenReturn(rides);

        // Call the method
        RideNotificationDTO result = spyRideService.citizenNewRide(username);

        // Assert the result
        assertNull(result.getText());
        assertEquals(0.0, result.getPrice(), 0.001);
        assertNull(result.getId());
    }

    @Test
    public void testCitizenNewRide_CitizenNotInRide() {
        // Prepare test data
        String username = "user1";

        Ride ride = new Ride();
        ride.setName("Ride 1");
        ride.setPrice(20.0);
        ride.setId(1L);
        ride.setStatus("CREATE");
        ride.setPaid(String.join("|", Arrays.asList("user2", "user3")));

        List<Citizen> citizens = Arrays.asList(
                Citizen.builder().username("user2").build(),
                Citizen.builder().username("user3").build()
        );

        ride.setCitizens(citizens);

        RideService spyRideService = Mockito.spy(rideService);
        Mockito.doReturn(false).when(spyRideService).inCitizenList(citizens, username);

        Mockito.when(rr.findAll()).thenReturn(Arrays.asList(ride));

        // Call the method
        RideNotificationDTO result = spyRideService.citizenNewRide(username);

        // Assert the result
        assertNull(result.getText());
        assertEquals(0.0, result.getPrice(), 0.001);
        assertNull(result.getId());
    }

    @Test
    public void testCitizenNewRide_RideFound() {
        // Prepare test data
        String username = "user1";

        Ride ride = new Ride();
        ride.setName("Ride 1");
        ride.setPrice(20.0);
        ride.setId(1L);
        ride.setStatus("CREATE");
        ride.setPaid(String.join("|", Arrays.asList("user2", "user3")));

        List<Citizen> citizens = Arrays.asList(
                Citizen.builder().username("user1").build(),
                Citizen.builder().username("user2").build(),
                Citizen.builder().username("user3").build()
        );

        ride.setCitizens(citizens);

        RideService spyRideService = Mockito.spy(rideService);
        Mockito.doReturn(true).when(spyRideService).inCitizenList(citizens, username);

        Mockito.when(rr.findAll()).thenReturn(Arrays.asList(ride));

        // Call the method
        RideNotificationDTO result = spyRideService.citizenNewRide(username);
        System.out.println(result);
        // Assert the result
        assertEquals("NOVA VOZNJA : Ride 1", result.getText());
        assertEquals(20.0 / 3, result.getPrice(), 0.001);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testSaveRide_AddRouteToRideFails() {
        // Mock data
        RideSaveDTO rideSaveDTO = new RideSaveDTO();
        rideSaveDTO.setUsers(new ArrayList<>());
        rideSaveDTO.setRoutePartInterface(new ArrayList<>());

        Ride ride = new Ride();
        ride.setId(1l);

        RideService spyRideService = Mockito.spy(rideService);
        Mockito.doReturn(ride).when(spyRideService).createBasicRide(rideSaveDTO);
        Mockito.doReturn(true).when(spyRideService).addUsersToRide(1l, rideSaveDTO.getUsers());
        Mockito.doReturn(false).when(spyRideService).addRouteToRide(1l, rideSaveDTO.getRoutePartInterface());

        // Call the method
        Boolean result = spyRideService.saveRide(rideSaveDTO);

        // Verify the calls and assertions
        verify(spyRideService, times(1)).createBasicRide(rideSaveDTO);
        verify(spyRideService, times(1)).addUsersToRide(1l, rideSaveDTO.getUsers());
        verify(spyRideService, times(1)).addRouteToRide(1l, rideSaveDTO.getRoutePartInterface());

        assertFalse(result);
    }

    @Test
    public void testSaveRide_AddUsersToRideFails() {
        // Mock data
        RideSaveDTO rideSaveDTO = new RideSaveDTO();
        rideSaveDTO.setUsers(new ArrayList<>());
        rideSaveDTO.setRoutePartInterface(new ArrayList<>());

        Ride ride = new Ride();
        ride.setId(1l);

        RideService spyRideService = Mockito.spy(rideService);
        Mockito.doReturn(ride).when(spyRideService).createBasicRide(rideSaveDTO);
        Mockito.doReturn(false).when(spyRideService).addUsersToRide(1l, rideSaveDTO.getUsers());
        Mockito.doReturn(true).when(spyRideService).addRouteToRide(1l, rideSaveDTO.getRoutePartInterface());

        // Call the method
        Boolean result = spyRideService.saveRide(rideSaveDTO);

        // Verify the calls and assertions
        verify(spyRideService, times(1)).createBasicRide(rideSaveDTO);
        verify(spyRideService, times(1)).addUsersToRide(1l, rideSaveDTO.getUsers());
        verify(spyRideService, times(0)).addRouteToRide(anyLong(), anyList());

        assertFalse(result);
    }

    @Test
    public void testSaveRide_SuccessfulSave() {
        // Mock data
        RideSaveDTO rideSaveDTO = new RideSaveDTO();
        rideSaveDTO.setUsers(new ArrayList<>());
        rideSaveDTO.setRoutePartInterface(new ArrayList<>());

        Ride ride = new Ride();
        ride.setId(1l);

        RideService spyRideSerivce = Mockito.spy(rideService);
        Mockito.doReturn(ride).when(spyRideSerivce).createBasicRide(rideSaveDTO);
        Mockito.doReturn(true).when(spyRideSerivce).addUsersToRide(1l, rideSaveDTO.getUsers());
        Mockito.doReturn(true).when(spyRideSerivce).addRouteToRide(1l, rideSaveDTO.getRoutePartInterface());

        // Call the method
        Boolean result = spyRideSerivce.saveRide(rideSaveDTO);

        // Verify the calls and assertions
        verify(spyRideSerivce, times(1)).createBasicRide(rideSaveDTO);
        verify(spyRideSerivce, times(1)).addUsersToRide(1l, rideSaveDTO.getUsers());
        verify(spyRideSerivce, times(1)).addRouteToRide(1l, rideSaveDTO.getRoutePartInterface());

        assertTrue(result);
    }


    @Test
    public void testCitizenAcceptRide_RideFoundAndPaidByCitizen() {

        String username = "john";
        Long rideId = 1L;
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setPaid(username);
        ride.getCitizens().add(Citizen.builder().build());

        RideService spyRideSerivce = Mockito.spy(rideService);
        Mockito.doReturn(true).when(spyRideSerivce).findDriver(Mockito.any());

        // Stub the findById method of the mock repository to return the mock ride
        Mockito.when(rr.findById(Mockito.anyLong())).thenReturn(Optional.of(ride));

        // Call the citizenAcceptRide method
        boolean result = spyRideSerivce.citizenAcceptRide("john", 1L);

        // Verify the calls and assertions
        verify(rr, times(1)).findById(rideId);
        verify(rr, times(0)).save(any());
        verifyNoMoreInteractions(rr);
        // Assert the result
        assertTrue(result);


    }

    @Test
    public void testCitizenAcceptRide_RideFoundAndPaidByOtherCitizen() {
        // Mock data
        String username = "john";
        String otherUsername = "jane";
        Long rideId = 1L;
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setPaid(otherUsername);
        ride.getCitizens().add(Citizen.builder().username(username).build());
        ride.getCitizens().add(Citizen.builder().username(otherUsername).build());

        RideService spyRideSerivce = Mockito.spy(rideService);
        Mockito.doReturn(true).when(spyRideSerivce).findDriver(Mockito.any());

        // Mock repository methods
        when(rr.findById(rideId)).thenReturn(Optional.of(ride));

        // Call the method
        boolean result = spyRideSerivce.citizenAcceptRide(username, rideId);

        // Verify the calls and assertions
        verify(rr, times(1)).findById(rideId);
//        verify(rr, times(1)).save(ride);
        verifyNoMoreInteractions(rr);

        assertTrue(result);
        assertEquals(otherUsername + "|" + username, ride.getPaid());
    }

    @Test
    public void testCitizenAcceptRide_RideFoundAndNotPaid() {
        // Mock data
        String username = "john";
        Long rideId = 1L;
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setPaid("");
        ride.getCitizens().add(Citizen.builder().username(username).build());

        RideService spyRideSerivce = Mockito.spy(rideService);
        Mockito.doReturn(true).when(spyRideSerivce).findDriver(Mockito.any());

        // Mock repository methods
        when(rr.findById(rideId)).thenReturn(Optional.of(ride));

        // Call the method
        boolean result = spyRideSerivce.citizenAcceptRide(username, rideId);

        // Verify the calls and assertions
        verify(rr, times(1)).findById(rideId);
//        verify(rr, times(1)).save(ride);
        verifyNoMoreInteractions(rr);

        assertTrue(result);
        assertEquals(username, ride.getPaid());
    }

    @Test
    public void testCitizenAcceptRide_RideNotFound() {
        // Mock data
        String username = "john";
        Long rideId = 1L;

        // Mock repository methods
        when(rr.findById(rideId)).thenReturn(Optional.empty());

        // Call the method
        boolean result = rideService.citizenAcceptRide(username, rideId);

        // Verify the calls and assertions
        verify(rr, times(1)).findById(rideId);
        verifyNoMoreInteractions(rr);

        assertFalse(result);
    }

    @Test
    public void testCitizenAcceptRide_AllCitizensPaid() {
        // Mock data
        String username = "john";
        Long rideId = 1L;
        List<String> paidCitizens = Arrays.asList("john", "jane", "james");
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setPaid(String.join("|", paidCitizens));
        ride.getCitizens().add(Citizen.builder().username("john").build());
        ride.getCitizens().add(Citizen.builder().username("jane").build());
        ride.getCitizens().add(Citizen.builder().username("james").build());

        RideService spyRideSerivce = Mockito.spy(rideService);
        Mockito.doReturn(true).when(spyRideSerivce).findDriver(Mockito.any());

        // Mock repository methods
        when(rr.findById(rideId)).thenReturn(Optional.of(ride));

        // Call the method
        boolean result = spyRideSerivce.citizenAcceptRide(username, rideId);

        // Verify the calls and assertions
        verify(rr, times(1)).findById(rideId);
//        verify(rr, times(0)).save(any());
        verifyNoMoreInteractions(rr);

        assertTrue(result);
    }

    @Test
    public void testCitizenAcceptRide_AllCitizensPaidWithExtraPipe() {
        // Mock data
        String username = "john";
        Long rideId = 1L;
        List<String> paidCitizens = Arrays.asList("john", "jane", "james");
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setPaid(String.join("|", paidCitizens) + "|");
        ride.getCitizens().add(Citizen.builder().username("john").build());
        ride.getCitizens().add(Citizen.builder().username("jane").build());
        ride.getCitizens().add(Citizen.builder().username("james").build());

        RideService spyRideSerivce = Mockito.spy(rideService);
        Mockito.doReturn(true).when(spyRideSerivce).findDriver(Mockito.any());

        // Mock repository methods
        when(rr.findById(rideId)).thenReturn(Optional.of(ride));

        // Call the method
        boolean result = spyRideSerivce.citizenAcceptRide(username, rideId);

        // Verify the calls and assertions
        verify(rr, times(1)).findById(rideId);
//        verify(rr, times(0)).save(any());
        verifyNoMoreInteractions(rr);

        assertTrue(result);
    }

    @Test
    public void testCitizenAcceptRide_RideFoundButNoPaidCitizens() {
        // Mock data
        String username = "john";
        Long rideId = 1L;
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setPaid("");
        ride.getCitizens().add(Citizen.builder().username("john").build());

        RideService spyRideSerivce = Mockito.spy(rideService);
        Mockito.doReturn(true).when(spyRideSerivce).findDriver(Mockito.any());

        // Mock repository methods
        when(rr.findById(rideId)).thenReturn(Optional.of(ride));

        // Call the method
        boolean result = spyRideSerivce.citizenAcceptRide(username, rideId);

        // Verify the calls and assertions
        verify(rr, times(1)).findById(rideId);
//        verify(rr, times(1)).save(ride);
        verifyNoMoreInteractions(rr);

        assertTrue(result);
        assertEquals(username, ride.getPaid());
    }

    @Test
    public void testCitizenAcceptRide_RideFoundWithSinglePaidCitizen() {
        // Mock data
        String username = "john";
        Long rideId = 1L;
        String paidCitizen = "jane";
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setPaid(paidCitizen);
        ride.getCitizens().add(Citizen.builder().username("john").build());
        ride.getCitizens().add(Citizen.builder().username("jane").build());

        RideService spyRideSerivce = Mockito.spy(rideService);
        Mockito.doReturn(true).when(spyRideSerivce).findDriver(Mockito.any());

        // Mock repository methods
        when(rr.findById(rideId)).thenReturn(Optional.of(ride));

        // Call the method
        boolean result = spyRideSerivce.citizenAcceptRide(username, rideId);

        // Verify the calls and assertions
        verify(rr, times(1)).findById(rideId);
//        verify(rr, times(1)).save(ride);
        verifyNoMoreInteractions(rr);

        assertTrue(result);
        assertEquals(paidCitizen + "|" + username, ride.getPaid());
    }

}