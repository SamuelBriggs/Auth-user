package com.example.stagetwohng.services.user;

import com.example.stagetwohng.dtos.requests.UserRegistrationRequest;
import com.example.stagetwohng.dtos.responses.UserData;
import com.example.stagetwohng.dtos.responses.UserRegistrationResponse;
import com.example.stagetwohng.model.User;

public interface UserService {


    UserRegistrationResponse register (UserRegistrationRequest request);

    UserData getUser(Long userId);

    UserData save(User user);

    User findByUserId(String userId);
}
