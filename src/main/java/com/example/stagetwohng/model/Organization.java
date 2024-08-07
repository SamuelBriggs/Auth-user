package com.example.stagetwohng.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public void setUserId(List<String> userId) {
        this.userId = userId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public @NotBlank String getName() {
        return name;
    }

    public List<String> getUserId() {
        return userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getDescription() {
        return description;
    }


    @NotBlank
    private String name;

    private List<String> userId = new ArrayList<>();

    @Column(unique = true)

    private String orgId;

    private String description;









}
