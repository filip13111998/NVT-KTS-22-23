package ftn.uns.ac.rs.NVTKTS20222023.controllers;

import ftn.uns.ac.rs.NVTKTS20222023.dto.response.CitizenBlockDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)  // Enable Spring support
@SpringBootTest  // Start the full application context
@AutoConfigureMockMvc  // Auto-configure the MockMvc instance
public class BlockControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAllCitizensAPI() throws Exception {
        // Perform the request and validate the response
        //{"username":"c2","firstName":"Marko","lastName":"Lazarevic","city":"Novi Sad","phone":"666-666","block":false}
//        String expected = "{\"username\":\"c2\",\"firstName\":\"Marko\",\"lastName\":\"Lazarevic\",\"city\":\"Novi Sad\",\"phone\":\"666-666\",\"block\":false}";
//        String expectedJson = "{\"username\":\"c2\",\"firstName\":\"Marko\",\"lastName\":\"Lazarevic\",\"city\":\"Novi Sad\",\"phone\":\"666-666\",\"block\":false}";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/block/citizens")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].username").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("c2"));

//                .andExpect(MockMvcResultMatchers.jsonPath("$[*].citizenId").isNotEmpty());
    }

}
