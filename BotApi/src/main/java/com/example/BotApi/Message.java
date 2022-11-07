package com.example.BotApi;

public class Message {
    private String contenu;
    private int id;

    public Message(int id, String contenu){
        this.contenu = contenu;
        this.id = id;
    }

    public String getContenu(){
        return contenu;
    }
    public int getId(){
        return id;
    }
}
