package com.example.BotApi.contract;



public class MessageContract {
    private String content;

    public MessageContract() {}

    public MessageContract(String a) {
        this.content = a;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
