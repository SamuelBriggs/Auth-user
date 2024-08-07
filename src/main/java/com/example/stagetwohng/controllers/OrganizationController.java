package com.example.stagetwohng.controllers;

import com.example.stagetwohng.dtos.requests.OrganizationRegistrationRequest;
import com.example.stagetwohng.dtos.requests.UserToOrgRequest;
import com.example.stagetwohng.dtos.responses.ApiResponse;
import com.example.stagetwohng.dtos.responses.OrganizationCreationResponse;
import com.example.stagetwohng.dtos.responses.UserOrganizationResponse;
import com.example.stagetwohng.repository.UserRepository;
import com.example.stagetwohng.services.organization.HngOrganizationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrganizationController {

    private final HngOrganizationService organizationService;

    private final UserRepository userRepository;

    public OrganizationController(HngOrganizationService organizationService, UserRepository userRepository) {
        this.organizationService = organizationService;
        this.userRepository = userRepository;
    }


    @GetMapping("/api/organisations")

    public ApiResponse<?> getAllUserOgs(){

        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String email = userId.substring(1, userId.length() - 1);
        var user = userRepository.findByEmail(email);

       var response =  organizationService.getAllUsersOrganizations(user.get().getId());

       ApiResponse<List<UserOrganizationResponse>> apiResponse = new ApiResponse<>();

       apiResponse.setData(response);
       apiResponse.setStatus("Success");
       apiResponse.setMessage("<message>");

        return apiResponse;
    }


    @GetMapping("/api/organisations/{orgId}")

    public ApiResponse<?> getOrgbyId(@PathVariable String orgId){

        var response = organizationService.getOrgById(orgId);
        ApiResponse<UserOrganizationResponse> apiResponse = new ApiResponse<>();

        apiResponse.setData(response);
        apiResponse.setStatus("Success");
        apiResponse.setMessage("<message>");
        return apiResponse;
    }


    @PostMapping("/api/organisations")

    public ApiResponse<?> createOrg (OrganizationRegistrationRequest registrationRequest){

        var response = organizationService.createNewOrganization(registrationRequest);
        ApiResponse<OrganizationCreationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setStatus("Success");
        apiResponse.setMessage("<message>");
        return apiResponse;

    }

    @PostMapping("/api/organisations/{orgId}/users")

    public ApiResponse<?> addUserToOrg(@PathVariable String orgId, UserToOrgRequest userToOrgRequest){

        organizationService.addUserToOrg(userToOrgRequest, orgId);

        ApiResponse<?> response = new ApiResponse<>();
        response.setMessage("Success");
        response.setMessage("User added successfully");
        return response;

    }


}
