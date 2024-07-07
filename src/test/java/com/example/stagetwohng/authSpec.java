package com.example.stagetwohng;

import com.example.stagetwohng.security.JwtUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class authSpec {

    private JwtUtils jwtUtils;

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

    }
    @Test
    @DisplayName("")
    public void test_organization_name_carries_user_first_name() {

    }
    @Test
    @DisplayName("")
    public void test_response_contains_expected_user_details() {

    }

    @Test
    @DisplayName("")
    public void test_user_with_valid_login_details_log_in_successfully() {

    }

    @Test
    @DisplayName("")
    public void test_registration_fails_with_incomplete_fields() {

    }

    @Test
    @DisplayName("")
    public void test_login_fails_with_incomplete_fields() {

    }

    @Test
    @DisplayName("")
    public void test_registration_with_dplicate_email_fails() {

    }

    @Test
    @DisplayName("")
    public void test_error_message_retrns_422_stats_code_and_appropiate_fields() {

    }



}
