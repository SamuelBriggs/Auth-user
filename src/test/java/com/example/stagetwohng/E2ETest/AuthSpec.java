//package com.example.stagetwohng.E2ETest;
//
//import com.example.stagetwohng.dtos.requests.LoginRequest;
//import com.example.stagetwohng.dtos.requests.OrganizationRegistrationRequest;
//import com.example.stagetwohng.dtos.requests.UserRegistrationRequest;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import org.springframework.http.MediaType;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AuthSpec {
//
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    @DisplayName("It Should Register User Successfully with Default Organisation")
//    public void testRegisterUserSuccessfully() throws Exception {
//        UserRegistrationRequest request = new UserRegistrationRequest();
//        request.setFirstName("John");
//        request.setLastName("Doe");
//        request.setEmail("john.doe@example.com");
//        request.setPassword("password123");
//        request.setPhone("1234567890");
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.status").value("success"))
//                .andExpect(jsonPath("$.message").value("Registration successful"))
//                .andExpect(jsonPath("$.data.user.firstName").value("John"))
//                .andExpect(jsonPath("$.data.user.lastName").value("Doe"))
//                .andExpect(jsonPath("$.data.user.email").value("john.doe@example.com"))
//                .andExpect(jsonPath("$.data.accessToken").isNotEmpty())
//                .andExpect(jsonPath("$.data.user.phone").value("1234567890"));
//    }
//
//    @Test
//    @DisplayName("It Should Fail If Required Fields Are Missing")
//    public void testRegisterUserWithMissingFields() throws Exception {
//        UserRegistrationRequest request = new UserRegistrationRequest();
//        request.setFirstName("");
//        request.setLastName("");
//        request.setEmail("");
//        request.setPassword("");
//        request.setPhone("1234567890");
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(jsonPath("$.errors").isArray())
//                .andExpect(jsonPath("$.errors.length()").value(4));
//    }
//
//    @Test
//    @DisplayName("It Should Fail if there’s Duplicate Email or UserID")
//    public void testRegisterUserWithDuplicateEmail() throws Exception {
//        UserRegistrationRequest request = new UserRegistrationRequest();
//        request.setFirstName("Jane");
//        request.setLastName("Doe");
//        request.setEmail("john.doe@example.com");
//        request.setPassword("password123");
//        request.setPhone("1234567890");
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(jsonPath("$.errors").isArray())
//                .andExpect(jsonPath("$.errors[0].field").value("email"));
//    }
//
//    @Test
//    @DisplayName("Test that Login fails with Incomplete Fields")
//    public void testLoginFailsWithIncompleteFields() throws Exception {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setEmail("john.doe@example.com");
//
//        String requestJson = objectMapper.writeValueAsString(loginRequest);
//
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.status").value("error"))
//                .andExpect(jsonPath("$.message").exists());
//
//    }
//
//    @Test
//    @DisplayName("Test that a user with valid login details logs in successfully")
//    public void testUserWithValidLoginDetailsLogInSuccessfully() throws Exception {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setEmail("john.doe@example.com");
//        loginRequest.setPassword("");
//        String loginRequestJson = objectMapper.writeValueAsString(loginRequest);
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(loginRequestJson))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("success"))
//                .andExpect(jsonPath("$.message").value("Login successful"))
//                .andExpect(jsonPath("$.data.accessToken").exists())
//                .andExpect(jsonPath("$.data.user.userId").exists())
//                .andExpect(jsonPath("$.data.user.firstName").value("John"))
//                .andExpect(jsonPath("$.data.user.lastName").value("Doe"))
//                .andExpect(jsonPath("$.data.user.email").value("john.doe@example.com"))
//                .andExpect(jsonPath("$.data.user.phone").value("1234567890"));
//    }
//
//
//    @Test
//    @DisplayName("Test organization register successfully")
//    public void testOrganizationRegisterSuccessfully() throws Exception {
//        OrganizationRegistrationRequest organizationRegistrationRequest = new OrganizationRegistrationRequest();
//        organizationRegistrationRequest.setDescription("We are the next big deal");
//        organizationRegistrationRequest.setName("New Age Organization");
//        String organizationJson = objectMapper.writeValueAsString(organizationRegistrationRequest);
//        mockMvc.perform(post("/api/organisations")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(organizationJson))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("success"))
//                .andExpect(jsonPath("$.message").value("Organisation created successfully"))
//                .andExpect(jsonPath("$.data.orgId").exists())
//                .andExpect(jsonPath("$.data.name").exists())
//                .andExpect(jsonPath("$.data.description").exists());
//    }
//
//    @Test
//    @DisplayName("")
//    public void test_error_message_returns_422_stats_code_and_appropiate_fields() throws Exception {
//        UserRegistrationRequest request = new UserRegistrationRequest();
//        request.setFirstName("Jane");
//        request.setLastName("Doe");
//        request.setEmail("");
//        request.setPassword("password123");
//        request.setPhone("1234567890");
//
//        String requestJson = objectMapper.writeValueAsString(request);
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(jsonPath("$.errors").isArray())
//                .andExpect(jsonPath("$.errors[0].field").value("email"))
//                .andExpect(jsonPath("$.errors[0].message").exists());
//
//    }
//=======
//   @Autowired
//   private MockMvc mockMvc;
//
//   @Autowired
//   private ObjectMapper objectMapper;
//
//   @Test
//   @DisplayName("It Should Register User Successfully with Default Organisation")
//   public void testRegisterUserSuccessfully() throws Exception {
//       UserRegistrationRequest request = new UserRegistrationRequest();
//       request.setFirstName("John");
//       request.setLastName("Doe");
//       request.setEmail("john.doe@example.com");
//       request.setPassword("password123");
//       request.setPhone("1234567890");
//
//       mockMvc.perform(post("/auth/register")
//                       .contentType(MediaType.APPLICATION_JSON)
//                       .content(objectMapper.writeValueAsString(request)))
//               .andExpect(status().isCreated())
//               .andExpect(jsonPath("$.status").value("success"))
//               .andExpect(jsonPath("$.message").value("Registration successful"))
//               .andExpect(jsonPath("$.data.user.firstName").value("John"))
//               .andExpect(jsonPath("$.data.user.lastName").value("Doe"))
//               .andExpect(jsonPath("$.data.user.email").value("john.doe@example.com"))
//               .andExpect(jsonPath("$.data.accessToken").isNotEmpty())
//               .andExpect(jsonPath("$.data.user.phone").value("1234567890"));
//   }
//
//   @Test
//   @DisplayName("It Should Fail If Required Fields Are Missing")
//   public void testRegisterUserWithMissingFields() throws Exception {
//       UserRegistrationRequest request = new UserRegistrationRequest();
//       request.setFirstName("");
//       request.setLastName("");
//       request.setEmail("");
//       request.setPassword("");
//       request.setPhone("1234567890");
//
//       mockMvc.perform(post("/auth/register")
//                       .contentType(MediaType.APPLICATION_JSON)
//                       .content(objectMapper.writeValueAsString(request)))
//               .andExpect(status().isUnprocessableEntity())
//               .andExpect(jsonPath("$.errors").isArray())
//               .andExpect(jsonPath("$.errors.length()").value(4));
//   }
//
//   @Test
//   @DisplayName("It Should Fail if there’s Duplicate Email or UserID")
//   public void testRegisterUserWithDuplicateEmail() throws Exception {
//       UserRegistrationRequest request = new UserRegistrationRequest();
//       request.setFirstName("Jane");
//       request.setLastName("Doe");
//       request.setEmail("john.doe@example.com");
//       request.setPassword("password123");
//       request.setPhone("1234567890");
//
//       mockMvc.perform(post("/auth/register")
//                       .contentType(MediaType.APPLICATION_JSON)
//                       .content(objectMapper.writeValueAsString(request)))
//               .andExpect(status().isUnprocessableEntity())
//               .andExpect(jsonPath("$.errors").isArray())
//               .andExpect(jsonPath("$.errors[0].field").value("email"));
//   }
//
//   @Test
//   @DisplayName("Test that Login fails with Incomplete Fields")
//   public void testLoginFailsWithIncompleteFields() throws Exception {
//       LoginRequest loginRequest = new LoginRequest();
//       loginRequest.setEmail("john.doe@example.com");
//
//       String requestJson = objectMapper.writeValueAsString(loginRequest);
//
//       mockMvc.perform(post("/auth/login")
//                       .contentType(MediaType.APPLICATION_JSON)
//                       .content(requestJson))
//               .andExpect(status().isBadRequest())
//               .andExpect(jsonPath("$.status").value("error"))
//               .andExpect(jsonPath("$.message").exists());
//
//   }
//
//   @Test
//   @DisplayName("Test that a user with valid login details logs in successfully")
//   public void testUserWithValidLoginDetailsLogInSuccessfully() throws Exception {
//       LoginRequest loginRequest = new LoginRequest();
//       loginRequest.setEmail("john.doe@example.com");
//       loginRequest.setPassword("");
//       String loginRequestJson = objectMapper.writeValueAsString(loginRequest);
//       mockMvc.perform(post("/auth/login")
//                       .contentType(MediaType.APPLICATION_JSON)
//                       .content(loginRequestJson))
//               .andExpect(status().isOk())
//               .andExpect(jsonPath("$.status").value("success"))
//               .andExpect(jsonPath("$.message").value("Login successful"))
//               .andExpect(jsonPath("$.data.accessToken").exists())
//               .andExpect(jsonPath("$.data.user.userId").exists())
//               .andExpect(jsonPath("$.data.user.firstName").value("John"))
//               .andExpect(jsonPath("$.data.user.lastName").value("Doe"))
//               .andExpect(jsonPath("$.data.user.email").value("john.doe@example.com"))
//               .andExpect(jsonPath("$.data.user.phone").value("1234567890"));
//   }
//
//
//   @Test
//   @DisplayName("Test organization register successfully")
//   public void testOrganizationRegisterSuccessfully() throws Exception {
//       OrganizationRegistrationRequest organizationRegistrationRequest = new OrganizationRegistrationRequest();
//       organizationRegistrationRequest.setDescription("We are the next big deal");
//       organizationRegistrationRequest.setName("New Age Organization");
//       String organizationJson = objectMapper.writeValueAsString(organizationRegistrationRequest);
//       mockMvc.perform(post("/api/organisations")
//                       .contentType(MediaType.APPLICATION_JSON)
//                       .content(organizationJson))
//               .andExpect(status().isOk())
//               .andExpect(jsonPath("$.status").value("success"))
//               .andExpect(jsonPath("$.message").value("Organisation created successfully"))
//               .andExpect(jsonPath("$.data.orgId").exists())
//               .andExpect(jsonPath("$.data.name").exists())
//               .andExpect(jsonPath("$.data.description").exists());
//   }
//
//   @Test
//   @DisplayName("Test error message returns 422 status code and appropiate fields")
//   public void testErrorMessageReturns422StatusCodeAndAppropiateFields() throws Exception {
//       UserRegistrationRequest request = new UserRegistrationRequest();
//       request.setFirstName("Jane");
//       request.setLastName("Doe");
//       request.setEmail("");
//       request.setPassword("password123");
//       request.setPhone("1234567890");
//
//       String requestJson = objectMapper.writeValueAsString(request);
//
//       mockMvc.perform(post("/auth/register")
//                       .contentType(MediaType.APPLICATION_JSON)
//                       .content(requestJson))
//               .andExpect(status().isUnprocessableEntity())
//               .andExpect(jsonPath("$.errors").isArray())
//               .andExpect(jsonPath("$.errors[0].field").value("email"))
//               .andExpect(jsonPath("$.errors[0].message").exists());
//
//   }
//
//}
