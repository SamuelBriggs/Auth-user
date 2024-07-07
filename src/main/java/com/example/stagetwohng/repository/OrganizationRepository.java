package com.example.stagetwohng.repository;

import com.example.stagetwohng.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long>  {

    List<Organization> findAll();

}
