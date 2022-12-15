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
    private String author;
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

    public Message(String id, String content, String author, String description){
        this.content = content;
        this.id = id;
        this.author = author;
        this.description = description;
    }
    public Message(String id, String content, String author){
        this.content = content;
        this.id = id;
        this.author = author;
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
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
