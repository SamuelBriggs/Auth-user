package com.example.stagetwohng.services;

import com.example.stagetwohng.dtos.requests.UserRegistrationRequest;
import com.example.stagetwohng.dtos.responses.UserRegistrationResponse;

public interface UserService {


    UserRegistrationResponse register (UserRegistrationRequest request);


}
