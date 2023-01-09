package com.example.BotApi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Item {
	
	//DB id value attribute.
	@Id
	@GeneratedValue
	private int id;
	//Attributes.
	private String title;							//The title of the message. Gives an idea of what it is in one sentence or a few words.
    private String link;							//The link that the message will contain.
    
    @ManyToMany
    private List<Tag> tags = new ArrayList<Tag>();	//Association to tags existing in the database.
    
    private Boolean modal;							//A boolean to keep track of whether the message originated from a modal or a normal message that was caught.
    private String description;						//The description given by the user OR the content of the message if it wasn't a modal form.
    
    @ManyToOne
    private DiscordUser discordUser;				//Association to users existing in the database.
    
    private String messageId;						//The id of the message in discord to which this is associated.
    private Long time;								//The time attribute should be in milliseconds as a Long value. This will help with keeping the data compatible between any type of language in theory.
	
    //Constructor.
    public Item() {
    	
    }
    
    public Item(final String title, final String link, final List<Tag> tags,final Boolean modal, final String description,final DiscordUser discordUser, final String messageId, final Long time) {
    	this.setTitle(title);
    	this.setLink(link);
    	this.setTags(tags);
    	this.setModal(modal);
    	this.setDescription(description);
    	this.setDiscordUser(discordUser);
    	this.setMessageId(messageId);
    	this.setTime(time);
    }
    
    //Getter & Setter.
    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public List<Tag> getTags() {
		return this.tags;
	}
	private void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public Boolean getModal() {
		return modal;
	}
	public void setModal(Boolean modal) {
		this.modal = modal;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public DiscordUser getDiscordUser() {
		return this.discordUser;
	}
	private void setDiscordUser(DiscordUser discordUser) {
		this.discordUser = discordUser;
	}
	public String getMessageId() {
		return messageId;
	}
	private void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public Long getTime() {
		return time;
	}
	private void setTime(Long time) {
		this.time = time;
	}
    
}
