package ftn.uns.ac.rs.NVTKTS20222023.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import ftn.uns.ac.rs.NVTKTS20222023.model.CustomOAuth2User;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class LoginOAuthController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @GetMapping("auth/login/oauth2/code/google")
//    public ResponseEntity<String> googleCallback(@AuthenticationPrincipal OAuth2AuthenticationToken authentication) {
//        System.out.println("STIGAO");
//        OAuth2AuthorizedClient authorizedClient =
//                this.authorizedClientService.loadAuthorizedClient(
//                        authentication.getAuthorizedClientRegistrationId(),
//                        authentication.getName());
//
//        String accessToken = authorizedClient.getAccessToken().getTokenValue();
//
//        String email = "";
//
//        // Decode the ID token to get the email address
//        try {
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
//                    .setAudience(Arrays.asList("450547483794-4292higt2q9vaq63cu7ssa3jnaggkhod.apps.googleusercontent.com"))
//                    .build();
//
//            GoogleIdToken idTokenObj = verifier.verify(accessToken);
//            if (idTokenObj != null) {
//                GoogleIdToken.Payload payload = idTokenObj.getPayload();
//                email = payload.getEmail();
//
//                // Do something with the email address
//                // ...
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid ID token");
//            }
//        } catch (GeneralSecurityException | IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error decoding ID token");
//        }
//        System.out.println("USAOOO");
//        return ResponseEntity.ok(email);
//    }

//    @GetMapping("auth/login/oauth2/code/google")
//    public ResponseEntity<String> handleOAuth2Callback(Principal principal) {
//        // Handle the OAuth2 callback response here
////        System.out.println(authorizationCode);
//        return ResponseEntity.ok(principal.getName());
//    }

    @GetMapping("auth/login/oauth2/code/google")
    public ResponseEntity<String> handleOAuth2Callbackk(@RequestParam(name = "code") String authorizationCode) {
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
            return ResponseEntity.ok(responseBody);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


//    @GetMapping("auth/login/oauth2/code/google")
//    public ResponseEntity<String> handleOAuth2Callbackk(@RequestParam(name = "code") String authorizationCode) {
//        // Handle the OAuth2 callback response here
//        System.out.println(authorizationCode);
//        return ResponseEntity.ok(authorizationCode.length()+"");
//    }

//    @GetMapping("auth/login/oauth2/code/google")
//    public ResponseEntity<String> handleOAuth2Callback(@RequestParam(name = "code") String authorizationCode){
//        // Exchange the authorization code for an access token and an ID token
////        String accessTokenUri = "https://www.googleapis.com/oauth2/v4/token";
////        String accessTokenUri = "https://oauth2.googleapis.com/token";
////        String clientId = "450547483794-u7usqmlhrtr11e0psoef0ghck73sb25u.apps.googleusercontent.com";
////        String clientSecret = "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";
////        String redirectUri = "http://localhost:8080/auth/login/oauth2/code/google";
//
////        String  email = authentication.getPrincipal().toString();
//
//
//
//        return ResponseEntity.ok(authorizationCode);
//    }


//    @GetMapping("auth/login/oauth2/code/google")
//    public ResponseEntity<String> handleOAuth2Callback(@RequestParam(name = "code") String authorizationCode) throws IOException {
//        // Exchange the authorization code for an access token and an ID token
////        String accessTokenUri = "https://www.googleapis.com/oauth2/v4/token";
//        String accessTokenUri = "https://oauth2.googleapis.com/token";
//        String clientId = "450547483794-u7usqmlhrtr11e0psoef0ghck73sb25u.apps.googleusercontent.com";
//        String clientSecret = "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";
//        String redirectUri = "http://localhost:8080/auth/login/oauth2/code/google";
//
//        System.out.println(authorizationCode);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("code", authorizationCode);
//        params.add("client_id", clientId);
//        params.add("client_secret", clientSecret);
//        params.add("redirect_uri", redirectUri);
//        params.add("grant_type", "authorization_code");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<OAuth2AccessTokenResponse> responseEntity = restTemplate.postForEntity(accessTokenUri, requestEntity, OAuth2AccessTokenResponse.class);
//
//        OAuth2AccessTokenResponse tokenResponse = responseEntity.getBody();
//
//        System.out.println(tokenResponse.getAccessToken());
//        System.out.println(responseEntity.hasBody());
//        String accessToken = null;
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//            if (responseEntity.hasBody()) {
//                // Authorization code is valid
//                 accessToken = responseEntity.getBody().getAccessToken().getTokenValue();
//            } else {
//                // Response body is null
//                String errorMessage = "Response body is null";
//                System.out.println(errorMessage);
//            }
//        } else {
//            // Authorization code is invalid or expired
//            String errorMessage = "Failed to retrieve access token. HTTP error code: " + responseEntity.getStatusCodeValue();
//            System.out.println("Failed to retrieve access token. HTTP error code: " + responseEntity.getStatusCodeValue());
//        }
//
//
//        if (tokenResponse != null && tokenResponse.getAccessToken() != null) {
//            accessToken = tokenResponse.getAccessToken().getTokenValue();
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Access token is null");
//        }
//
//        String apiUrl = "https://people.googleapis.com/v1/people/me?personFields=emailAddresses";
//        HttpHeaders headers_mail = new HttpHeaders();
//        headers.setBearerAuth(accessToken);
//
//        HttpEntity<String> entity = new HttpEntity<>("parameters", headers_mail);
//
//        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, Map.class);
//        Map<String, Object> responseData = response.getBody();
//
//        List<Map<String, String>> emailAddresses = (List<Map<String, String>>) responseData.get("emailAddresses");
//        String email = emailAddresses.get(0).get("value");
//        return ResponseEntity.ok(email);
//    }



//    @GetMapping("auth/login/oauth2/code/google")
//    public ResponseEntity<String> handleOAuth2Callback(@RequestParam(name = "code") String authorizationCode) {
//        // Exchange the authorization code for an access token
//        OAuth2AccessToken accessToken = this.exchangeAuthorizationCode(authorizationCode);
//
//        // Make a request to the user info endpoint to retrieve the user's details
//        String userInfo = this.getUserInfo(accessToken);
//
//        // Create a JWT token containing the user's details
////        String jwtToken = jwtService.createToken(userInfo.getUsername(), userInfo.getEmail());
//
//        // Return the JWT token in the response
//        return ResponseEntity.ok(userInfo);
//    }
//
//    public OAuth2AccessToken exchangeAuthorizationCode(String authorizationCode) {
//        // Construct the request body
//        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//        requestBody.add("grant_type", "authorization_code");
//        requestBody.add("code", authorizationCode);
//        requestBody.add("redirect_uri", "http://localhost:8080/callback");
//        requestBody.add("client_id", "your_client_id");
//        requestBody.add("client_secret", "your_client_secret");
//
//        // Create an HTTP headers object with the content type set to application/x-www-form-urlencoded
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        // Create an HTTP entity object with the request body and headers
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
//
//        // Send the request to the token endpoint and obtain the access token from the response
//        ResponseEntity<OAuth2AccessToken> responseEntity = restTemplate.exchange(
//                "https://oauth2-provider.com/token",
//                HttpMethod.POST,
//                requestEntity,
//                OAuth2AccessToken.class);
//
//        // Return the access token to the calling method
//        return responseEntity.getBody();
//    }
//
//    public String getUserInfo(OAuth2AccessToken accessToken) {
//        // Create an HTTP headers object with the Authorization header set to Bearer <access_token>
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(accessToken.getValue());
//
//        // Create an HTTP entity object with the headers
//        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
//
//        // Send the request to the user info endpoint and obtain the user details from the response
//        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
//                "https://oauth2-provider.com/userinfo",
//                HttpMethod.GET,
//                requestEntity,
//                new ParameterizedTypeReference<Map<String, Object>>() {});
//
//        // Map the user details to a UserInfo object and return it to the calling method
//        Map<String, Object> userAttributes = responseEntity.getBody();
////        UserInfo userInfo = new UserInfo();
////        userInfo.setUsername((String) userAttributes.get("sub"));
////        userInfo.setEmail((String) userAttributes.get("email"));
//        // set any other relevant user attributes
//
//        return ((String) userAttributes.get("email"));
//    }

//    @GetMapping("auth/login/oauth2/code/google")
//    public ResponseEntity<String> googleCallback(@AuthenticationPrincipal OAuth2AuthenticationToken authentication) {
//        OAuth2AuthorizedClient authorizedClient =
//                this.authorizedClientService.loadAuthorizedClient(
//                        authentication.getAuthorizedClientRegistrationId(),
//                        authentication.getName());
//
//        String accessToken = authorizedClient.getAccessToken().getTokenValue();
//
//        // Use the access token to get the email from Google
//        String email = getEmailFromGoogle(accessToken);
//
//        return ResponseEntity.ok(email);
//    }
//
//    private String getEmailFromGoogle(String accessToken) {
//        try {
//            // Set up the Google ID token verifier
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
//                    .setAudience(Arrays.asList("450547483794-4292higt2q9vaq63cu7ssa3jnaggkhod.apps.googleusercontent.com"))
//                    .build();
//
//            // Verify the ID token and get the email address
//            GoogleIdToken idTokenObj = verifier.verify(accessToken);
//            if (idTokenObj != null) {
//                GoogleIdToken.Payload payload = idTokenObj.getPayload();
//                return payload.getEmail();
//            } else {
//                throw new RuntimeException("Invalid ID token");
//            }
//        } catch (GeneralSecurityException | IOException e) {
//            throw new RuntimeException("Error decoding ID token", e);
//        }
//    }
}