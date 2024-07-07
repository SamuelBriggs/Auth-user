package com.example.stagetwohng.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.stereotype.Service;

@Setter
@Getter
public class UserData {

    private String userId;
    private String firstName;
    private String lastName;

    public UserData(String userId, String firstName, String lastName, String email, String phone) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    private String email;
    private String phone;


}
