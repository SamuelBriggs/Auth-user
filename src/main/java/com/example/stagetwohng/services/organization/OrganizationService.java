package com.example.stagetwohng.services.organization;

import com.example.stagetwohng.dtos.requests.OrganizationRegistrationRequest;
import com.example.stagetwohng.dtos.responses.OrganizationCreationResponse;
import com.example.stagetwohng.dtos.responses.UserOrganizationResponse;
import com.example.stagetwohng.model.Organization;

import java.util.List;

public interface OrganizationService {


    UserOrganizationResponse getAllUsersOrganizations(Long userId);

    OrganizationCreationResponse createNewOrganization(OrganizationRegistrationRequest organizationRegistrationRequest);

    List<Organization> findAllOrganization();
}
