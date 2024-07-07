package com.example.stagetwohng.dtos.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class UserRegistrationRequest {

    @NotBlank(message = "Name cannot be blank or contain only spaces.")
    @Size(min = 3, message = "Name must be at least 3 characters long.")
    private String firstName;


    public @NotBlank String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }

    public @NotBlank(message = "Provide a Password") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Provide a Password") String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NotBlank(message = "Name cannot be blank or contain only spaces.")
    @Size(min = 3, message = "Name must be at least 3 characters long.")
    private String lastName;

    @NotBlank
    @Column (unique = true)
    private String email;

    @NotBlank(message = "Provide a Password")
    private String password;

    private String phone;


}




