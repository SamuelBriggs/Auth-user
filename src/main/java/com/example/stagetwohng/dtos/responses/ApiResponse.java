package com.example.stagetwohng.dtos.responses;

import lombok.*;


public class ApiResponse<T> {
    private String message;
    private String status;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String isSuccess() {
        return status;
    }

    public void setSuccess(String success) {
        this.status = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
