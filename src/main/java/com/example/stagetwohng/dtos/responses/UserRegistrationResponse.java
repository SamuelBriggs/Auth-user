package com.example.stagetwohng.dtos.responses;

public class UserRegistrationResponse {

    private String accessToken;

    public UserData getUser() {
        return UserData;
    }

    public void setUser(UserData UserData) {
        this.UserData = UserData;
    }

    private UserData UserData;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}



