package com.example.stagetwohng.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationRegistrationRequest {
    private String name;
    @NotBlank
    private String description;





}
