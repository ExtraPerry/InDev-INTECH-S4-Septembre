package com.example.BotApi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


import java.sql.Timestamp;

//Class.
@Entity
public class Message {
	
	//DB id value attribute.
	@Id
	@GeneratedValue
	private int id;
	//Attributes.
    private String title;
    private String link;
    private String[] tags;
    private String description;
    private String userId;
    private String messageId;
    private Timestamp time;
    
    //Constructors.
    public Message() {
   
    }
    
    public Message(final String title, final String link, final String[] tags, final String description, final String userId, final String messageId, final Timestamp time) {
    	this.setTitle(title);
    	this.setLink(link);
    	this.setTags(tags);
    	this.setDescription(description);
    	this.setUserId(userId);
    	this.setMessageId(messageId);
    	this.setTime(time);
    }
    
    //Getter & Setters.
    public int getId() {
    	return id;
    }
	public String getTitle() {
		return title;
	}
	private void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	private void setLink(String link) {
		this.link = link;
	}
	public String[] getTags() {
		return tags;
	}
	private void setTags(String[] tags) {
		this.tags = tags;
	}
	public String getDescription() {
		return description;
	}
	private void setDescription(String description) {
		this.description = description;
	}
	public String getUserId() {
		return userId;
	}
	private void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMessageId() {
		return messageId;
	}
	private void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public Timestamp getTime() {
		return time;
	}
	private void setTime(Timestamp time) {
		this.time = time;
	}
	
}