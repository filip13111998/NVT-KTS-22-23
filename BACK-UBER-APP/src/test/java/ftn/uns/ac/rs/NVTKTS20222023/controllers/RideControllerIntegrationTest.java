package ftn.uns.ac.rs.NVTKTS20222023.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.MarkerDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RideSaveDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.request.RoutePartDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideNotificationDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Location;
import ftn.uns.ac.rs.NVTKTS20222023.service.DriverService;
import ftn.uns.ac.rs.NVTKTS20222023.service.LoginHistoryService;
import ftn.uns.ac.rs.NVTKTS20222023.service.RideService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class RideControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RideService rideService;


    @Autowired
    private RestTemplate restTemplate;
//    @MockBean
    @Autowired
    private LoginHistoryService lhs;

    @Test
    public void testCitizenAcceptRideSuccessInvalidData() throws Exception {
        String username = "Jane";
        Long rideId = 43435L;

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + 8080 + "/api/ride/history/{username}/{id}",
                Boolean.class,
                username,
                rideId
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Boolean answer = responseEntity.getBody();
        assertEquals(false,answer);

    }

    @Test
    public void testCitizenAcceptRideSuccessInvalidDataMvc() throws Exception {
        String username = "Jane";
        Long rideId = 43435L;

        // Mock the behavior of the RideService
//        when(rideService.citizenAcceptRide(username, rideId)).thenReturn(false);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/history/{username}/{id}", username, rideId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    public void testCitizenAcceptRideSuccessMvc() throws Exception {
        String username = "c2";
        Long rideId = 12345L;

        // Mock the behavior of the RideService
        when(rideService.citizenAcceptRide(username, rideId)).thenReturn(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/history/{username}/{id}", username, rideId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void testHistoryRideInvalidUsername() throws Exception {
        String username = "dr76";

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + 8080 + "/api/ride/history/{username}",
                Boolean.class,
                username
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Boolean answer = responseEntity.getBody();
        assertEquals(false,answer);
    }

    @Test
    public void testHistoryRideInvalidUsernameMvc() throws Exception {
        String username = "dr76";

        // Mock the behavior of the LoginHistoryService
//        when(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours(username)).thenReturn(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/history/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }


    @Test
    public void testHistoryRideSuccess() throws Exception {
        String username = "dr5";

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + 8080 + "/api/ride/history/{username}",
                Boolean.class,
                username
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Boolean answer = responseEntity.getBody();
        assertEquals(true,answer);
    }

    @Test
    public void testHistoryRideSuccessMvc() throws Exception {
        String username = "dr5";

        // Mock the behavior of the LoginHistoryService
//        when(lhs.hasDriverBeenLoggedLowerThan8HoursIn24Hours(username)).thenReturn(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/history/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void testSaveRide() throws Exception {
        RideSaveDTO rideSaveDTO = RideSaveDTO.builder()
                .name("Test Ride")
                .pets(true)
                .baby(true)
                .car_type("CAR")
                .price(100L)
                .distance(10L)
                .users(Arrays.asList("c2", "c33"))
                .routePartInterface(Arrays.asList(
                        RoutePartDTO.builder().id(3l).coordinates(Arrays.asList(MarkerDTO.builder().longitude(21.2).latitude(43.4).build() , MarkerDTO.builder().longitude(23.2).latitude(45.4).build())).build(),
                        RoutePartDTO.builder().id(2l).coordinates(Arrays.asList(MarkerDTO.builder().longitude(55.5).latitude(41.4).build() , MarkerDTO.builder().longitude(76.2).latitude(76.4).build())).build()
                ))
                .favorite(false)
                .minutes(0)
                .build();

        ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + 8080 + "/api/ride/save",
                rideSaveDTO,
                Boolean.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Boolean answer = responseEntity.getBody();
        assertEquals(true, answer);

        // Mock the behavior of the RideService
//        when(rideService.saveRide(rideSaveDTO)).thenReturn(true);

        // Perform the request and assert the response
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride/save")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(rideSaveDTO)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void testSaveRideMvc() throws Exception {
        RideSaveDTO rideSaveDTO = RideSaveDTO.builder()
                .name("Test Ride")
                .pets(true)
                .baby(true)
                .car_type("CAR")
                .price(100L)
                .distance(10L)
                .users(Arrays.asList("c2", "c33"))
                .routePartInterface(Arrays.asList(
                        RoutePartDTO.builder().id(3l).coordinates(Arrays.asList(MarkerDTO.builder().longitude(21.2).latitude(43.4).build() , MarkerDTO.builder().longitude(23.2).latitude(45.4).build())).build(),
                        RoutePartDTO.builder().id(2l).coordinates(Arrays.asList(MarkerDTO.builder().longitude(55.5).latitude(41.4).build() , MarkerDTO.builder().longitude(76.2).latitude(76.4).build())).build()
                ))
                .favorite(false)
                .minutes(0)
                .build();

        // Mock the behavior of the RideService
        when(rideService.saveRide(rideSaveDTO)).thenReturn(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(rideSaveDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void testSaveRideInvalidInput() throws Exception {
        RideSaveDTO rideSaveDTO = RideSaveDTO.builder()
                .name("Test Ride")
                .pets(false)
                .baby(false)
                .car_type("Sedan")
                .price(100L)
                .distance(10L)
                .users(Arrays.asList("user1", "user2"))
                .routePartInterface(Arrays.asList(
                        RoutePartDTO.builder().id(3l).coordinates(Arrays.asList(MarkerDTO.builder().longitude(21.2).latitude(43.4).build() , MarkerDTO.builder().longitude(23.2).latitude(45.4).build())).build(),
                        RoutePartDTO.builder().id(2l).coordinates(Arrays.asList(MarkerDTO.builder().longitude(55.5).latitude(41.4).build() , MarkerDTO.builder().longitude(76.2).latitude(76.4).build())).build()
                ))
                .favorite(false)
                .minutes(30)
                .build();

        ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + 8080 + "/api/ride/save",
                rideSaveDTO,
                Boolean.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Boolean answer = responseEntity.getBody();
        assertEquals(false, answer);
//        assertEquals(5545.0, rideNotificationDTO.getPrice());
//        assertEquals("NOVA VOZNJA : voznja next", rideNotificationDTO.getText());
        // Mock the behavior of the RideService
//        when(rideService.saveRide(rideSaveDTO)).thenReturn(false);

        // Perform the request and assert the response
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride/save")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(rideSaveDTO)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    public void testSaveRideInvalidInputMvc() throws Exception {
        RideSaveDTO rideSaveDTO = RideSaveDTO.builder()
                .name("Test Ride")
                .pets(false)
                .baby(false)
                .car_type("Sedan")
                .price(100L)
                .distance(10L)
                .users(Arrays.asList("user1", "user2"))
                .routePartInterface(Arrays.asList(
                        RoutePartDTO.builder().id(3l).coordinates(Arrays.asList(MarkerDTO.builder().longitude(21.2).latitude(43.4).build() , MarkerDTO.builder().longitude(23.2).latitude(45.4).build())).build(),
                        RoutePartDTO.builder().id(2l).coordinates(Arrays.asList(MarkerDTO.builder().longitude(55.5).latitude(41.4).build() , MarkerDTO.builder().longitude(76.2).latitude(76.4).build())).build()
                ))
                .favorite(false)
                .minutes(30)
                .build();

        // Mock the behavior of the RideService
//        when(rideService.saveRide(rideSaveDTO)).thenReturn(false);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(rideSaveDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    // Helper method to convert objects to JSON strings
    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    @Test
    public void testNewRideInvalidUsername() throws Exception {
        String username = "pera";

        ResponseEntity<RideNotificationDTO> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + 8080 + "/api/ride/new/{username}",
                RideNotificationDTO.class,
                username
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        RideNotificationDTO rideNotificationDTO = responseEntity.getBody();
        assertEquals(null, rideNotificationDTO.getId());
        assertEquals(0.0, rideNotificationDTO.getPrice());
        assertEquals(null, rideNotificationDTO.getText());
    }

    @Test
    public void testNewRideInvalidUsernameMvc() throws Exception {
        String username = "pera";

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/new/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").doesNotExist());
    }

    @Test
    public void testNewRide() throws Exception {
        String username = "c33";


        ResponseEntity<RideNotificationDTO> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + 8080 + "/api/ride/new/{username}",
                RideNotificationDTO.class,
                username
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        RideNotificationDTO rideNotificationDTO = responseEntity.getBody();
        assertEquals(6l, rideNotificationDTO.getId());
        assertEquals(5545.0, rideNotificationDTO.getPrice());
        assertEquals("NOVA VOZNJA : voznja next", rideNotificationDTO.getText());

    }

    @Test
    public void testNewRideMvc() throws Exception {
        String username = "john";

        // Create a mock RideNotificationDTO
        RideNotificationDTO mockNotification = RideNotificationDTO.builder()
                .id(1L)
                .price(10.0)
                .text("New ride notification")
                .build();

        // Mock the behavior of the DriverService
        when(rideService.citizenNewRide(anyString())).thenReturn(mockNotification);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/new/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("New ride notification"));
    }

}
