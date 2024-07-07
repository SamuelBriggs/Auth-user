package com.example.stagetwohng.dtos.responses;

import lombok.ToString;

@ToString
public class UserRegistrationResponse {


    private String accessToken;

    private UserData UserData;


    public void setUser(UserData UserData) {
        this.UserData = UserData;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public UserData getUser() {
        return UserData;
    }




}



