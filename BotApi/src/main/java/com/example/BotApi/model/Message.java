package com.example.BotApi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Message {
    @Id
    private Integer id;
    private String contenu;
    private String auteur;
    private String description;

    public Message() {}

    public Message(int id, String contenu, String auteur, String description){
        this.contenu = contenu;
        this.id = id;
        this.auteur = auteur;
        this.description = description;
    }
    public Message(int id, String contenu, String auteur){
        this.contenu = contenu;
        this.id = id;
        this.auteur = auteur;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    public String getContenu(){return contenu;}
}
