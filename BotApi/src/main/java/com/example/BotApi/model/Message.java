package com.example.BotApi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Message {
    @Id
    private String id;
    private String content;
    private String auteur;
    private String description;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Message() {}

    public Message(String id, String content, String auteur, String description){
        this.content = content;
        this.id = id;
        this.auteur = auteur;
        this.description = description;
    }
    public Message(String id, String content, String auteur){
        this.content = content;
        this.id = id;
        this.auteur = auteur;
    }


    public void setId(String id) {
        this.id = id;
    }
    public String getId(){
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent(){
        return content;
    }

    public String getAuthor() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
