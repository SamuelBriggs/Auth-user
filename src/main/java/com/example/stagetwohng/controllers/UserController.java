package com.example.stagetwohng.controllers;

import com.example.stagetwohng.dtos.requests.UserRegistrationRequest;
import com.example.stagetwohng.dtos.responses.ApiResponse;
import com.example.stagetwohng.dtos.responses.UserData;
import com.example.stagetwohng.dtos.responses.UserRegistrationResponse;
import com.example.stagetwohng.services.user.HngUserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final HngUserService userService;

    public UserController(HngUserService userService) {
        this.userService = userService;
    }


    @PostMapping("/auth/register")
    public ApiResponse<UserRegistrationResponse> registerUser(@RequestBody UserRegistrationRequest registrationRequest){
        var response = userService.register(registrationRequest);
        ApiResponse<UserRegistrationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Registration Successful");
        apiResponse.setSuccess("Success");
        apiResponse.setData(response);
        return apiResponse;

    }

    @GetMapping("/api/user/{id}")
    public ApiResponse<?> getUser(@PathVariable Long id){
        var response = userService.getUser(id);

        ApiResponse<UserData> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Registration Successful");
        apiResponse.setSuccess("Success");
        apiResponse.setData(response);

        return apiResponse;
    }



}
