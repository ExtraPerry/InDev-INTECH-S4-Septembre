package com.example.BotApi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Message {
    @Id
    private Integer id;
    private String contenu;

    public Message() {}

    public Message(int id, String contenu){
        this.contenu = contenu;
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getContenu(){
        return contenu;
    }
    public int getId(){
        return id;
    }
}
