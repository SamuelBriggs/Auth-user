package com.example.stagetwohng.services;

import com.example.stagetwohng.dtos.requests.UserRegistrationRequest;
import com.example.stagetwohng.dtos.responses.UserRegistrationResponse;
import com.example.stagetwohng.dtos.responses.UserData;
import com.example.stagetwohng.exceptions.HngException;
import com.example.stagetwohng.model.Organization;
import com.example.stagetwohng.model.User;
import com.example.stagetwohng.repository.OrganizationRepository;
import com.example.stagetwohng.repository.UserRepository;
import com.example.stagetwohng.security.JwtUtils;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        savedUser.setUserId(savedUser.getId().toString());

     //   userRepository.save(user);


        Organization organization = new Organization();
        organization.setName(request.getFirstName() + "'s" + " Organization");
        List<String> listOfUsers = new ArrayList<>();
        listOfUsers.add(savedUser.getUserId());
        organization.setUserId(listOfUsers);
      Organization savedOrg =   organizationRepository.save(organization);
        savedOrg.setOrgId(savedOrg.getId().toString());

        var allOrgs = savedUser.getOrganizations();
        allOrgs.add(organization);

        savedUser.setOrganizations(allOrgs);

        userRepository.save(savedUser);

        String userId = savedUser.getUserId();
        String firstName = savedUser.getFirstName();
        String lastName = savedUser.getLastName();
        String email = savedUser.getEmail();
        String phone = savedUser.getPhone();

        UserData userData = new UserData(userId, firstName, lastName, email, phone);

        UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
        userRegistrationResponse.setUser(userData);
        userRegistrationResponse.setAccessToken(jwtUtils.generateAccessToken(request.getEmail()));

        if(userRegistrationResponse!=null) return userRegistrationResponse;
        else throw new HngException("Error Creating New User");
    }

    @Override

    public UserData getUser(Long userId){

        var user = userRepository.findById(userId);
        String usrId = user.get().getUserId();
        String firstName = user.get().getFirstName();
        String lastName = user.get().getLastName();
        String phone = user.get().getPhone();
        String email = user.get().getEmail();

        return new UserData(usrId, firstName, lastName, email, phone);

    }



}
