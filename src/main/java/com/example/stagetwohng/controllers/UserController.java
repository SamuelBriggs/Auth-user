package com.example.stagetwohng.controllers;

import com.example.stagetwohng.dtos.requests.UserRegistrationRequest;
import com.example.stagetwohng.dtos.responses.ApiResponse;
import com.example.stagetwohng.dtos.responses.UserRegistrationResponse;
import com.example.stagetwohng.services.HngUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
public class UserController {

    private final HngUserService userService;

    public UserController(HngUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")

    public ApiResponse<UserRegistrationResponse> registerUser(@RequestBody UserRegistrationRequest registrationRequest){
        var response = userService.register(registrationRequest);
        ApiResponse<UserRegistrationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Registration Successful");
        apiResponse.setSuccess("Success");
        apiResponse.setData(response);

        return apiResponse;
    }




}
