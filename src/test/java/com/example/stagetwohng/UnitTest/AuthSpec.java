//package com.example.stagetwohng.UnitTest;
//
//import com.example.stagetwohng.dtos.requests.LoginRequest;
//import com.example.stagetwohng.dtos.requests.OrganizationRegistrationRequest;
//import com.example.stagetwohng.dtos.requests.UserRegistrationRequest;
//import com.example.stagetwohng.model.Organization;
//import com.example.stagetwohng.model.User;
//import com.example.stagetwohng.security.JwtUtils;
//import com.example.stagetwohng.services.organization.OrganizationService;
//import com.example.stagetwohng.services.user.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import java.util.ArrayList;
//import java.util.List;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AuthSpec {
//
//    private JwtUtils jwtUtils;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private OrganizationService organizationService;
//
//    private User user = new User();
//
//    /**
//     * It Should Register User Successfully with Default Organisation:Ensure a user is registered successfully when no organisation details are provided.
//     * Verify the default organisation name is correctly generated (e.g., "John's Organisation" for a user with the first name "John").
//     * Check that the response contains the expected user details and access token.
//     * It Should Log the user in successfully:Ensure a user is logged in successfully when a valid credential is provided and fails otherwise.
//     * Check that the response contains the expected user details and access token.
//     * It Should Fail If Required Fields Are Missing:Test cases for each required field (firstName, lastName, email, password) missing.
//     * Verify the response contains a status code of 422 and appropriate error messages.
//     * It Should Fail if there’s Duplicate Email or UserID:Attempt to register two users with the same email.
//     * Verify the response contains a status code of 422 and appropriate error messages.
//     * */
//
//    @BeforeEach()
//    public void setUp() {
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setEmail("john.doe@example.com");
//        user.setPassword("password123");
//        user.setPhone("1234567890");
//    }
//
//    @Test
//    @DisplayName("Test token expired after 24 hours")
//    public void testTokenExpiredAfter24Hours() {
//        String token = jwtUtils.generateAccessToken("badmuscros@gmail.com");
//        String tokenState = jwtUtils.validateTokenExpiration(token);
//        assertThat(tokenState).isEqualTo("Expired");
//    }
//
//    @Test
//    @DisplayName("Test token valid within 24 hours")
//    public void testTokenValidWithin24Hours() {
//        String token = jwtUtils.generateAccessToken("badmuscros@gmail.com");
//        String tokenState = jwtUtils.validateTokenExpiration(token);
//        assertThat(tokenState).isEqualTo("Valid");
//    }
//
//
//    @Test
//    @DisplayName("User registration is successful with Default Organization")
//    public void testUserRegistrationIsSuccessfulWithoutOrganizationDetails() {
//        UserRegistrationRequest request = new UserRegistrationRequest();
//        request.setFirstName(user.getFirstName());
//        request.setLastName(user.getLastName());
//        request.setEmail(user.getEmail());
//        request.setPassword(user.getPassword());
//        request.setPhone(user.getPhone());
//        var response = userService.register(request);
//        assert response != null;
//        assertThat(response.getAccessToken()).isNotNull();
//        assertThat(response.getUser()).isNotNull();
//        System.out.println(response);
//    }
//
//
//    @Test
//    @DisplayName("")
//    public void test_organization_name_carries_user_first_name() {
//        UserRegistrationRequest request = new UserRegistrationRequest();
//        request.setFirstName(user.getFirstName());
//        request.setLastName(user.getLastName());
//        request.setEmail(user.getEmail());
//        request.setPassword(user.getPassword());
//        request.setPhone(user.getPhone());
//        var response = userService.register(request);
//        String userId = response.getUser().getUserId();
//        Organization organization = null;
//        List<Organization> allOrganization = organizationService.findAllOrganization();
//        for (Organization org: allOrganization) {
//            if (org.getUserId().equals(userId)) {
//                organization = org;
//            }
//        }
//
//        assertThat(organization).isNotNull();
//        assert organization != null;
//        assertThat(organization.getName()).isEqualTo(user.getFirstName() + "'s Organization");
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
