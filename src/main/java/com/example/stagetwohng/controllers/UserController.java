package com.example.stagetwohng.controllers;

import com.example.stagetwohng.dtos.requests.UserRegistrationRequest;
import com.example.stagetwohng.dtos.responses.ApiResponse;
import com.example.stagetwohng.dtos.responses.UserData;
import com.example.stagetwohng.dtos.responses.UserRegistrationResponse;
import com.example.stagetwohng.services.user.HngUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final HngUserService userService;

    public UserController(HngUserService userService) {
        this.userService = userService;
    }


<<<<<<< HEAD
    @PostMapping("/auth/register")
    public ApiResponse<UserRegistrationResponse> registerUser(@RequestBody UserRegistrationRequest registrationRequest){
=======
    public ResponseEntity<ApiResponse<UserRegistrationResponse>> registerUser(@RequestBody UserRegistrationRequest registrationRequest){
>>>>>>> ce2814c9ff6697beb6e579f13e8d07e770c31439
        var response = userService.register(registrationRequest);
        ApiResponse<UserRegistrationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Registration Successful");
        apiResponse.setStatus("Success");
        apiResponse.setData(response);
<<<<<<< HEAD
        return apiResponse;
=======
        return HapiResponse;
>>>>>>> ce2814c9ff6697beb6e579f13e8d07e770c31439

    }

    @GetMapping("/api/user/{id}")
    public ApiResponse<?> getUser(@PathVariable Long id){
        var response = userService.getUser(id);

        ApiResponse<UserData> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("<message>");
        apiResponse.setStatus("Success");
        apiResponse.setData(response);

        return apiResponse;
    }



}
