package com.example.BotApi.contract;



public class MessageContract {
    private String content;
    private int id;
    private String author;

    public MessageContract() {}

    public MessageContract(int id, String a, String author) {
        this.id = id;
        this.content = a;
        this.author = author;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
