package com.example.stagetwohng.services;

import com.example.stagetwohng.dtos.requests.UserRegistrationRequest;
import com.example.stagetwohng.dtos.responses.UserRegistrationResponse;
import com.example.stagetwohng.dtos.responses.UserData;
import com.example.stagetwohng.model.Organization;
import com.example.stagetwohng.model.User;
import com.example.stagetwohng.repository.OrganizationRepository;
import com.example.stagetwohng.repository.UserRepository;
import com.example.stagetwohng.security.JwtUtils;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class HngUserService implements UserService{
    private final OrganizationRepository organizationRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    public HngUserService(OrganizationRepository organizationRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {

        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public UserRegistrationResponse register(UserRegistrationRequest request) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setPhone(request.getPhone());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);


        Organization organization = new Organization();
        organization.setName(request.getFirstName() + " 's" + " Organization");
        organization.setUserId(savedUser.getUserId());
        organizationRepository.save(organization);
        String userId = savedUser.getUserId();
        String firstName = savedUser.getFirstName();
        String lastName = savedUser.getLastName();
        String email = savedUser.getEmail();
        String phone = savedUser.getPhone();

        UserData userData = new UserData(userId, firstName, lastName, email, phone);

        UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
        userRegistrationResponse.setUser(userData);
        userRegistrationResponse.setAccessToken(jwtUtils.generateAccessToken(request.getEmail()));

        return userRegistrationResponse;
    }





}
