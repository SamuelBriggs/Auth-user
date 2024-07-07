package com.example.stagetwohng.services.organization;

import com.example.stagetwohng.dtos.requests.OrganizationRegistrationRequest;
import com.example.stagetwohng.dtos.requests.UserToOrgRequest;
import com.example.stagetwohng.dtos.responses.OrganizationCreationResponse;
import com.example.stagetwohng.dtos.responses.UserOrganizationResponse;
import com.example.stagetwohng.model.Organization;
import com.example.stagetwohng.repository.OrganizationRepository;
import com.example.stagetwohng.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
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
       response.setDescription(organizationRegistrationRequest.getDescription());
       response.setOrgId(savedOrg.getOrgId());
       response.setName(organizationRegistrationRequest.getName());
        return response;
    }

    @Override
    public List<Organization> findAllOrganization() {
        return organizationRepository.findAll();
    }

    @Override
    public UserOrganizationResponse getOrgById(String orgId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String email = userId.substring(1, userId.length() - 1);
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

    @Override
    public void addUserToOrg(UserToOrgRequest userToOrgRequest, String orgId) {
      var allOrgs =  organizationRepository.findAll();
      if (allOrgs.isEmpty()) return;
      System.out.println(allOrgs.size());
      Organization org = new Organization();
      for (int i = 0; i < allOrgs.size() ; i++) {
          if(allOrgs.get(i).getOrgId() == null) continue;
          if(allOrgs.get(i).getOrgId().equals(orgId)) org = allOrgs.get(i);
        }
        System.out.println(org.toString());
        List<String> list =  org.getUserId();
        List<String> listOfIds = new ArrayList<>(list);
        listOfIds.add(userToOrgRequest.getUserId());

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


