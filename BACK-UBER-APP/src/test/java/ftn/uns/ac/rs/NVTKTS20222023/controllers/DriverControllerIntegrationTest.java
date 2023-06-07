package ftn.uns.ac.rs.NVTKTS20222023.controllers;

import ftn.uns.ac.rs.NVTKTS20222023.controller.DriverController;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.CitizenBlockDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.RideNotificationDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import ftn.uns.ac.rs.NVTKTS20222023.service.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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

    @Test
    public void testNewRideInvalidUsername() throws Exception {
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
    public void testNewRide() throws Exception {
        String username = "john";

        // Create a mock RideNotificationDTO
        RideNotificationDTO mockNotification = RideNotificationDTO.builder()
                .id(1L)
                .price(10.0)
                .text("New ride notification")
                .build();

        // Mock the behavior of the DriverService
        when(driverService.newRide(anyString())).thenReturn(mockNotification);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/new/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("New ride notification"));
    }

    @Test
    public void testRejectRideSuccess() throws Exception {
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
    public void testRejectRideFailure() throws Exception {
        String username = "john";
        String message = "Not available";

        // Mock the behavior of the DriverService
        when(driverService.rejectRide(anyString(), anyString())).thenReturn(false);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/reject/{username}/{message}", username, message)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    public void testStartRide() throws Exception {
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
    public void testStartRideSuccess() throws Exception {
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

        // Mock the behavior of the DriverService
        when(driverService.startRide(anyString())).thenReturn(false);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/start/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    public void testFinishRideSuccess() throws Exception {
        String username = "john";

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

        // Mock the behavior of the DriverService
        when(driverService.finishRide(anyString())).thenReturn(false);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/driver/finish/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

}
