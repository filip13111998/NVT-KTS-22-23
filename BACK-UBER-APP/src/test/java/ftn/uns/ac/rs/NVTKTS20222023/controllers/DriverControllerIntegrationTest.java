package ftn.uns.ac.rs.NVTKTS20222023.controllers;

import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideNotificationDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Driver;
import ftn.uns.ac.rs.NVTKTS20222023.model.Ride;
import ftn.uns.ac.rs.NVTKTS20222023.repository.DriverRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.RideRepository;
import ftn.uns.ac.rs.NVTKTS20222023.service.DriverService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


//@WebMvcTest(DriverController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DriverControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverService driverService;

    @Autowired
    private RestTemplate restTemplate;


    @Test
    public void testNewRideInvalidUsername() throws Exception {
        String username = "pera";

        ResponseEntity<RideNotificationDTO> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + 8080 + "/api/driver/new/{username}",
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

        // Mock the behavior of the DriverService to return an empty RideNotificationDTO
//        when(driverService.newRide(anyString())).thenReturn(RideNotificationDTO.builder().build());

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/new/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").doesNotExist());
    }

    @Test
    public void testNewRide() {
        String username = "dr7";

        ResponseEntity<RideNotificationDTO> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + 8080 + "/api/driver/new/{username}",
                RideNotificationDTO.class,
                username
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        RideNotificationDTO rideNotificationDTO = responseEntity.getBody();
        assertEquals(5l, rideNotificationDTO.getId());
        assertEquals(5545.0, rideNotificationDTO.getPrice());
        assertEquals("NOVA VOZNJA : voznja next", rideNotificationDTO.getText());
    }


    @Test
    public void testNewRideMvc() throws Exception {
        String username = "pera";

        // Create a mock RideNotificationDTO
        RideNotificationDTO mockNotification = RideNotificationDTO.builder()
                .id(4l)
                .price(10.0)
                .text("New ride notification")
                .build();

        // Mock the behavior of the DriverService
        when(driverService.newRide(anyString())).thenReturn(mockNotification);



        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/new/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(4l))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("New ride notification"));
    }

    @Test
    public void testRejectRideSuccess() throws Exception {
        String username = "dr5";
        String message = "Comment";

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + 8080 + "/api/driver/reject/{username}/{message}",
                Boolean.class,
                username,
                message
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Boolean answer = responseEntity.getBody();
        assertEquals(true,answer);
    }

    @Test
    public void testRejectRideSuccessMvc() throws Exception {
        String username = "john";
        String message = "Not available";

        // Mock the behavior of the DriverService
        when(driverService.rejectRide(username, message)).thenReturn(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/reject/{username}/{message}", username, message)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void testRejectRideFailure(){
        String username = "john";
        String message = "Not available";

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + 8080 + "/api/driver/reject/{username}/{message}",
                Boolean.class,
                username,
                message
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Boolean answer = responseEntity.getBody();
        assertEquals(false,answer);
    }

    @Test
    public void testRejectRideFailureMvc() throws Exception {
        String username = "john";
        String message = "Not available";

        // Mock the behavior of the DriverService
//        when(driverService.rejectRide(anyString(), anyString())).thenReturn(false);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/reject/{username}/{message}", username, message)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    public void testStartRideSuccess() throws Exception {
        String username = "dr4";

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + 8080 + "/api/driver/start/{username}",
                Boolean.class,
                username
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Boolean answer = responseEntity.getBody();
        assertEquals(true,answer);
    }

    @Test
    public void testStartRideSuccessMvc() throws Exception {
        String username = "john";

        // Mock the behavior of the DriverService
        when(driverService.startRide(anyString())).thenReturn(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/start/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void testStartRideFailure() throws Exception {
        String username = "john";

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + 8080 + "/api/driver/start/{username}",
                Boolean.class,
                username
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Boolean answer = responseEntity.getBody();
        assertEquals(false,answer);
    }

    @Test
    public void testStartRideFailureMvc() throws Exception {
        String username = "john";

        // Mock the behavior of the DriverService
//        when(driverService.startRide(anyString())).thenReturn(false);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/start/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    public void testFinishRideSuccess() throws Exception {
        String username = "dr6";

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + 8080 + "/api/driver/finish/{username}",
                Boolean.class,
                username
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Boolean answer = responseEntity.getBody();
        assertEquals(true,answer);
    }

    @Test
    public void testFinishRideSuccessMvc() throws Exception {
        String username = "dr5";

        // Mock the behavior of the DriverService
        when(driverService.finishRide(anyString())).thenReturn(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/finish/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void testFinishRideFailure() throws Exception {
        String username = "john";

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + 8080 + "/api/driver/finish/{username}",
                Boolean.class,
                username
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Boolean answer = responseEntity.getBody();
        assertEquals(false,answer);
    }

    @Test
    public void testFinishRideFailureMvc() throws Exception {
        String username = "john";

        // Mock the behavior of the DriverService
//        when(driverService.finishRide(anyString())).thenReturn(false);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/finish/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

}
