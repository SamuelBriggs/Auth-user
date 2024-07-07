package com.example.stagetwohng.services.organization;

import com.example.stagetwohng.dtos.requests.OrganizationRegistrationRequest;
import com.example.stagetwohng.dtos.responses.OrganizationCreationResponse;
import com.example.stagetwohng.dtos.responses.UserOrganizationResponse;
import com.example.stagetwohng.model.Organization;
import com.example.stagetwohng.repository.OrganizationRepository;
import com.example.stagetwohng.repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HngOrganizationService implements OrganizationService{

    private final UserRepository userRepository;

    private final OrganizationRepository organizationRepository;

    public HngOrganizationService(UserRepository userRepository, OrganizationRepository organizationRepository) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public List<UserOrganizationResponse> getAllUsersOrganizations(Long userId) {

        var user = userRepository.findById(userId);

        var response = user.get().getOrganizations();

        return mapOrgToDto(response);
    }

    @Override
    public OrganizationCreationResponse createNewOrganization(OrganizationRegistrationRequest organizationRegistrationRequest) {
        Organization organization = new Organization();
        organization.setName(organizationRegistrationRequest.getName());
        organization.setDescription(organizationRegistrationRequest.getDescription());
       Organization savedOrg = organizationRepository.save(organization);
       savedOrg.setOrgId(savedOrg.getId().toString());
       organizationRepository.save(savedOrg);

       OrganizationCreationResponse response = new OrganizationCreationResponse();
       response.setDescription(savedOrg.getDescription());
       response.setOrgId(savedOrg.getOrgId());
       response.setName(savedOrg.getName());
        return response;
    }

    @Override
    public List<Organization> findAllOrganization() {
        return organizationRepository.findAll();
    }

    @Override
    public UserOrganizationResponse getOrgById(String orgId) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        email = email.substring(0, email.length()-1);
       var user  = userRepository.findByEmail(email);
       var response = user.get().getOrganizations();

       Organization organization = new Organization();

        for (int i = 0; i < response.size() ; i++) {
            if(response.get(i).getOrgId().equals(orgId)) organization = response.get(i);
        }

        UserOrganizationResponse userOrganizationResponse = new UserOrganizationResponse();

        userOrganizationResponse.setDescription(organization.getDescription());
        userOrganizationResponse.setName(organization.getName());
        userOrganizationResponse.setOrgId(orgId);

        return userOrganizationResponse;
    }


    private List<UserOrganizationResponse> mapOrgToDto(List<Organization> organizations){

        List<UserOrganizationResponse> listOfOrgs = new ArrayList<>();

        for (Organization organization : organizations) {
            UserOrganizationResponse org = new UserOrganizationResponse();
            org.setOrgId(organization.getOrgId());
            org.setName(organization.getName());
            org.setDescription(organization.getDescription());
            listOfOrgs.add(org);
        }
    return listOfOrgs;

    }



}


