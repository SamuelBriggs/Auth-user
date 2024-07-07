package com.example.stagetwohng.services;

import com.example.stagetwohng.dtos.requests.OrganizationRegistrationRequest;
import com.example.stagetwohng.dtos.responses.OrganizationCreationResponse;

public interface OrganizationService {


    UserOrganizationResponse getAllUsersOrganizations(Long userId);

    OrganizationCreationResponse createNewOrganization(OrganizationRegistrationRequest organizationRegistrationRequest);



}
