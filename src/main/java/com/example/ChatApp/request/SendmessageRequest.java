package com.example.ChatApp.request;

public class SendmessageRequest {
    private Integer userId;
    private Integer chatId;
    private String content;

    public SendmessageRequest() {
    }

    public SendmessageRequest(Integer userId, Integer chatId, String content) {
        this.userId = userId;
        this.chatId = chatId;
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
