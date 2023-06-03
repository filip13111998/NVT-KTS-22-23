package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.response.CitizenBlockDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.DriverBlockDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import ftn.uns.ac.rs.NVTKTS20222023.model.Driver;
import ftn.uns.ac.rs.NVTKTS20222023.repository.CitizenRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BlockServiceTest {

    @Mock
    private CitizenRepository citizenRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private BlockService blockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllCitizens() {
        // Create a list of Citizen entities for mocking
        List<Citizen> citizenList = new ArrayList<>();
        citizenList.add(Citizen.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .city("New York")
                .phone("1234567890")
                .block(false)
                .build());
        citizenList.add(Citizen.builder()
                .username("jane")
                .firstName("Jane")
                .lastName("Smith")
                .city("Los Angeles")
                .phone("9876543210")
                .block(true)
                .build());

        // Mock the findAll() method of CitizenRepository
        when(citizenRepository.findAll()).thenReturn(citizenList);

        // Invoke the service method
        List<CitizenBlockDTO> result = blockService.findAllCitizens();

        // Verify the result
        assertEquals(citizenList.size(), result.size());

        // Verify the mapping of Citizen entities to CitizenBlockDTO objects
        List<String> usernames = citizenList.stream().map(Citizen::getUsername).collect(Collectors.toList());
        List<String> resultUsernames = result.stream().map(CitizenBlockDTO::getUsername).collect(Collectors.toList());
        assertEquals(usernames, resultUsernames);

        List<String> cities = citizenList.stream().map(Citizen::getCity).collect(Collectors.toList());
        List<String> resultCities = result.stream().map(CitizenBlockDTO::getCity).collect(Collectors.toList());
        assertEquals(cities, resultCities);

        List<String> firstNames = citizenList.stream().map(Citizen::getFirstName).collect(Collectors.toList());
        List<String> resultFirstNames = result.stream().map(CitizenBlockDTO::getFirstName).collect(Collectors.toList());
        assertEquals(firstNames, resultFirstNames);

        List<String> lastNames = citizenList.stream().map(Citizen::getLastName).collect(Collectors.toList());
        List<String> resultLastNames = result.stream().map(CitizenBlockDTO::getLastName).collect(Collectors.toList());
        assertEquals(lastNames, resultLastNames);

        List<String> phones = citizenList.stream().map(Citizen::getPhone).collect(Collectors.toList());
        List<String> resultPhones = result.stream().map(CitizenBlockDTO::getPhone).collect(Collectors.toList());
        assertEquals(phones, resultPhones);

        List<Boolean> blocks = citizenList.stream().map(Citizen::isBlock).collect(Collectors.toList());
        List<Boolean> resultBlocks = result.stream().map(CitizenBlockDTO::isBlock).collect(Collectors.toList());
        assertEquals(blocks, resultBlocks);

        // Verify any other assertions as needed
        // ...
    }

    @Test
    void testFindAllDrivers() {
        // Create a list of Driver entities for mocking
        List<Driver> driverList = new ArrayList<>();
//        driverList.add(new Driver("john", "New York", "John", "Doe", "1234567890", false));
//        driverList.add(new Driver("jane", "Los Angeles", "Jane", "Smith", "9876543210", true));
        driverList.add(Driver.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .city("New York")
                .phone("1234567890")
                .block(false)
                .build());
        driverList.add(Driver.builder()
                .username("jane")
                .firstName("Jane")
                .lastName("Smith")
                .city("Los Angeles")
                .phone("9876543210")
                .block(true)
                .build());
        // Mock the findAll() method of DriverRepository
        when(driverRepository.findAll()).thenReturn(driverList);

        // Invoke the service method
        List<DriverBlockDTO> result = blockService.findAllDrivers();

        // Verify the result
        assertEquals(driverList.size(), result.size());

        // Verify the mapping of Driver entities to DriverBlockDTO objects
        List<String> usernames = driverList.stream().map(Driver::getUsername).collect(Collectors.toList());
        List<String> resultUsernames = result.stream().map(DriverBlockDTO::getUsername).collect(Collectors.toList());
        assertEquals(usernames, resultUsernames);

        List<String> cities = driverList.stream().map(Driver::getCity).collect(Collectors.toList());
        List<String> resultCities = result.stream().map(DriverBlockDTO::getCity).collect(Collectors.toList());
        assertEquals(cities, resultCities);

        List<String> firstNames = driverList.stream().map(Driver::getFirstName).collect(Collectors.toList());
        List<String> resultFirstNames = result.stream().map(DriverBlockDTO::getFirstName).collect(Collectors.toList());
        assertEquals(firstNames, resultFirstNames);

        List<String> lastNames = driverList.stream().map(Driver::getLastName).collect(Collectors.toList());
        List<String> resultLastNames = result.stream().map(DriverBlockDTO::getLastName).collect(Collectors.toList());
        assertEquals(lastNames, resultLastNames);

        List<String> phones = driverList.stream().map(Driver::getPhone).collect(Collectors.toList());
        List<String> resultPhones = result.stream().map(DriverBlockDTO::getPhone).collect(Collectors.toList());
        assertEquals(phones, resultPhones);

        List<Boolean> blocks = driverList.stream().map(Driver::isBlock).collect(Collectors.toList());
        List<Boolean> resultBlocks = result.stream().map(DriverBlockDTO::isBlock).collect(Collectors.toList());
        assertEquals(blocks, resultBlocks);

        // Verify any other assertions as needed
        // ...
    }

    @Test
    void testBlockCitizen() {
        // Create a sample citizen
        Citizen citizen = Citizen.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .city("New York")
                .phone("1234567890")
                .block(false)
                .build();

        // Mock the findByUsername() method of CitizenRepository
        when(citizenRepository.findByUsername("john")).thenReturn(citizen);

        // Invoke the service method
        boolean result = blockService.blockCitizen("john", "Blocked for inappropriate behavior");

        // Verify the result
        assertTrue(result);

        // Verify that the citizen's block status and comment are updated
        assertTrue(citizen.isBlock());
        assertEquals("Blocked for inappropriate behavior", citizen.getComment());

        // Verify that the save() method of CitizenRepository is called
//        verify(citizenRepository, times(1)).save(citizen);
    }

    @Test
    void testBlockCitizen_NonExistingCitizen() {
        // Mock the findByUsername() method of CitizenRepository to return null
        when(citizenRepository.findByUsername("john")).thenReturn(null);

        // Invoke the service method
        boolean result = blockService.blockCitizen("john", "Blocked for inappropriate behavior");

        // Verify the result
        assertFalse(result);

        // Verify that the save() method of CitizenRepository is not called
        verify(citizenRepository, never()).save(any());
    }

    @Test
    void testBlockDriver() {
        // Create a sample driver
        Driver driver = Driver.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .city("New York")
                .phone("1234567890")
                .block(false)
                .build();

        // Mock the findByUsername() method of DriverRepository
        when(driverRepository.findByUsername("john")).thenReturn(driver);

        // Invoke the service method
        boolean result = blockService.blockDriver("john", "Blocked for traffic violation");

        // Verify the result
        assertTrue(result);

        // Verify that the driver's block status and comment are updated
        assertTrue(driver.isBlock());
        assertEquals("Blocked for traffic violation", driver.getComment());
    }

    @Test
    void testBlockDriver_NonExistingDriver() {
        // Mock the findByUsername() method of DriverRepository to return null
        when(driverRepository.findByUsername("john")).thenReturn(null);

        // Invoke the service method
        boolean result = blockService.blockDriver("john", "Blocked for traffic violation");

        // Verify the result
        assertFalse(result);
    }


    @Test
    void testBlockCitizen_EmptyComment() {
        // Create a sample citizen
        Citizen citizen = Citizen.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .city("New York")
                .phone("1234567890")
                .block(false)
                .build();

        // Mock the findByUsername() method of CitizenRepository
        when(citizenRepository.findByUsername("john")).thenReturn(citizen);

        // Invoke the service method with an empty comment
        boolean result = blockService.blockCitizen("john", "");

        // Verify the result
        assertTrue(result);

        // Verify that the citizen's block status is updated and comment is empty
        assertTrue(citizen.isBlock());
        assertEquals("", citizen.getComment());
    }

    @Test
    void testBlockDriver_EmptyComment() {
        // Create a sample driver
        Driver driver = Driver.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .city("New York")
                .phone("1234567890")
                .block(false)
                .build();

        // Mock the findByUsername() method of DriverRepository
        when(driverRepository.findByUsername("john")).thenReturn(driver);

        // Invoke the service method with an empty comment
        boolean result = blockService.blockDriver("john", "");

        // Verify the result
        assertTrue(result);

        // Verify that the driver's block status is updated and comment is empty
        assertTrue(driver.isBlock());
        assertEquals("", driver.getComment());
    }
    @Test
    void testFindAllCitizens_EmptyList() {
        // Mock the behavior of the findAll() method to return an empty list
        when(citizenRepository.findAll()).thenReturn(Collections.emptyList());

        // Invoke the service method
        List<CitizenBlockDTO> result = blockService.findAllCitizens();

        // Verify that the result is an empty list
        assertEquals(0, result.size());
    }

    @Test
    void testFindAllDrivers_EmptyList() {
        // Mock the behavior of the findAll() method to return an empty list
        when(driverRepository.findAll()).thenReturn(Collections.emptyList());

        // Invoke the service method
        List<DriverBlockDTO> result = blockService.findAllDrivers();

        // Verify that the result is an empty list
        assertEquals(0, result.size());
    }

}
