package com.example.stagetwohng;

import com.example.stagetwohng.dtos.requests.LoginRequest;
import com.example.stagetwohng.dtos.requests.OrganizationRegistrationRequest;
import com.example.stagetwohng.dtos.requests.UserRegistrationRequest;
import com.example.stagetwohng.model.Organization;
import com.example.stagetwohng.model.User;
import com.example.stagetwohng.security.JwtUtils;
import com.example.stagetwohng.services.organization.OrganizationService;
import com.example.stagetwohng.services.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class authSpec {

    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    private User user = new User();

    /**
     * It Should Register User Successfully with Default Organisation:Ensure a user is registered successfully when no organisation details are provided.
     * Verify the default organisation name is correctly generated (e.g., "John's Organisation" for a user with the first name "John").
     * Check that the response contains the expected user details and access token.
     * It Should Log the user in successfully:Ensure a user is logged in successfully when a valid credential is provided and fails otherwise.
     * Check that the response contains the expected user details and access token.
     * It Should Fail If Required Fields Are Missing:Test cases for each required field (firstName, lastName, email, password) missing.
     * Verify the response contains a status code of 422 and appropriate error messages.
     * It Should Fail if there’s Duplicate Email or UserID:Attempt to register two users with the same email.
     * Verify the response contains a status code of 422 and appropriate error messages.
     * */

    @BeforeEach()
    public void setUp() {
        user.setFirstName("Diana");
        user.setLastName("Exodus");
        user.setUserId("");
        user.setEmail("");
        user.setPassword("");
        user.setEmail("");
        user.setOrganizations(new ArrayList<>());
    }

    @Test
    @DisplayName("Test token expired after 24 hours")
    public void test_token_expired_after_24_hours() {
        String token = jwtUtils.generateAccessToken("badmuscros@gmail.com");
        String tokenState = jwtUtils.validateTokenExpiration(token);
        assertThat(tokenState).isEqualTo("Expired");
    }

    @Test
    @DisplayName("Test token valid within 24 hours")
    public void test_token_valid_within_24_hours() {
        String token = jwtUtils.generateAccessToken("badmuscros@gmail.com");
        String tokenState = jwtUtils.validateTokenExpiration(token);
        assertThat(tokenState).isEqualTo("Valid");
    }

    @Test
    @DisplayName("Test user")
    public void test_user_can_not_view_organization_data_not_part_of() {

    }

    @Test
    @DisplayName("User registration is successful with Default Organization")
    public void test_user_registration_is_successful_without_organization_details() {
        UserRegistrationRequest request = new UserRegistrationRequest(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getPhone());
        var response = userService.register(request);
        assert response != null;
        assertThat(response.getAccessToken()).isNotNull();
        assertThat(response.getUser()).isNotNull();
        System.out.println(response);
    }


    @Test
    @DisplayName("")
    public void test_organization_name_carries_user_first_name() {
        UserRegistrationRequest request = new UserRegistrationRequest(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getPhone());
        var response = userService.register(request);
        String userId = response.getUser().getUserId();
        Organization organization = null;
        List<Organization> allOrganization = organizationService.findAllOrganization();
        for (Organization org: allOrganization) {
            if (org.getUserId().equals(userId)) {
                organization = org;
            }
        }

        assertThat(organization).isNotNull();
        assert organization != null;
        assertThat(organization.getName()).isEqualTo(user.getFirstName() + "'s Organization");
    }


    @Test
    @DisplayName("")
    public void test_response_contains_expected_user_details() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest("John", "Doe", "john.doe@example.com", "password123", "1234567890");
        userService.register(request);

        // Convert request to JSON
        String registrationRequestJson = objectMapper.writeValueAsString(request);

        // Perform the POST request to the registration endpoint
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registrationRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Registration successful"))
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.user.userId").exists())
                .andExpect(jsonPath("$.data.user.firstName").value("John"))
                .andExpect(jsonPath("$.data.user.lastName").value("Doe"))
                .andExpect(jsonPath("$.data.user.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.data.user.phone").value("1234567890"));


        // login response contains appropiate details


        // organization response contains appropiate details
        OrganizationRegistrationRequest organizationRegistrationRequest = new OrganizationRegistrationRequest();
        organizationRegistrationRequest.setDescription("We are the next big deal");
        organizationRegistrationRequest.setName("New Age Organization");
        String organizationJson = objectMapper.writeValueAsString(organizationRegistrationRequest);
        mockMvc.perform(post("/api/organisations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(organizationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Organisation created successfully"))
                .andExpect(jsonPath("$.data.orgId").exists())
                .andExpect(jsonPath("$.data.name").exists())
                .andExpect(jsonPath("$.data.description").exists());
    }

    @Test
    @DisplayName("Test that a user with valid login details logs in successfully")
    public void test_user_with_valid_login_details_log_in_successfully() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword("");
        String loginRequestJson = objectMapper.writeValueAsString(loginRequest);
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.user.userId").exists())
                .andExpect(jsonPath("$.data.user.firstName").value("John"))
                .andExpect(jsonPath("$.data.user.lastName").value("Doe"))
                .andExpect(jsonPath("$.data.user.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.data.user.phone").value("1234567890"));
    }


    @Test
    @DisplayName("")
    public void test_registration_fails_with_incomplete_fields() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest("John", "", "", "", "");
        // Missing lastName, email, password, and phone

        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("")
    public void test_login_fails_with_incomplete_fields() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john.doe@example.com");

        String requestJson = objectMapper.writeValueAsString(loginRequest);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").exists());

    }

    @Test
    @DisplayName("")
    public void test_registration_with_duplicate_email_fails() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest("John", "Doe", "duplicate@example.com", "password123", "1234567890");

        String requestJson = objectMapper.writeValueAsString(request);

        // First registration attempt
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        // Second registration attempt with the same email
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("")
    public void test_error_message_returns_422_stats_code_and_appropiate_fields() throws Exception {
        // Create a registration request with invalid data (e.g., missing email)
        UserRegistrationRequest request = new UserRegistrationRequest("John", "Doe",  "", "password123", "1234567890");

        // Convert request to JSON
        String requestJson = objectMapper.writeValueAsString(request);

        // Perform the POST request to the registration endpoint
        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").value("email"))
                .andExpect(jsonPath("$.errors[0].message").exists());

    }



}
