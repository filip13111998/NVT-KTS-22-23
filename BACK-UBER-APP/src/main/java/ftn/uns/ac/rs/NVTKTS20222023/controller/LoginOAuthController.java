package ftn.uns.ac.rs.NVTKTS20222023.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.gson.Gson;

import ftn.uns.ac.rs.NVTKTS20222023.service.AccountService;
import ftn.uns.ac.rs.NVTKTS20222023.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;


import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;


import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@RestController
public class LoginOAuthController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AccountService as;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @GetMapping("auth/login/oauth2/code/google")
//    public ResponseEntity<String> handleOAuth2Callback(Principal principal) {
//        // Handle the OAuth2 callback response here
////        System.out.println(authorizationCode);
//        return ResponseEntity.ok(principal.getName());
//    }

    @GetMapping("auth/login/oauth2/code/google")
    public RedirectView handleOAuth2Callbackk(@RequestParam(name = "code") String authorizationCode) throws JsonProcessingException {
        try {
            // Build the request body
            String requestBody = "code=" + authorizationCode
                    + "&client_id=" + "450547483794-u7usqmlhrtr11e0psoef0ghck73sb25u.apps.googleusercontent.com"
                    + "&client_secret=" + "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"
                    + "&redirect_uri=" + "http://localhost:8080/auth/login/oauth2/code/google"
                    + "&grant_type=authorization_code";

            // Create the HTTP client and request
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://oauth2.googleapis.com/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Send the request and read the response
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();

            // Print the response and return it to the client
            System.out.println(responseBody);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String id_token = jsonNode.get("id_token").asText();
            System.out.println(id_token);

            String[] tokenParts = id_token.split("\\.");

            String encodedPayload = tokenParts[1];
            byte[] decodedPayloadBytes = Base64.getUrlDecoder().decode(encodedPayload);
            String decodedPayload = new String(decodedPayloadBytes);
            System.out.println("PROSO");

            System.out.println(decodedPayload);
            JsonNode jsonNode2 = objectMapper.readTree(decodedPayload);
            String username = jsonNode2.get("email").asText();
            System.out.println(username);


            as.saveCitizenByUsername(username);
//
            String token = tokenUtils.generateToken(username, "ROLE_CITIZEN");

//            String redirectUrl = "http://localhost:4200/citizen-home";
            String redirectUrl = "http://localhost:4200/citizen-home/" + token;
            return new RedirectView(redirectUrl);
        } catch (Exception e) {
            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return null;
        }
    }


}

