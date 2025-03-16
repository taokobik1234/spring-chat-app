package com.example.ChatApp.request;

public class SingleChatRequest {
    private Integer userId;

    public SingleChatRequest(Integer userId) {
        this.userId = userId;
    }

    public SingleChatRequest() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
