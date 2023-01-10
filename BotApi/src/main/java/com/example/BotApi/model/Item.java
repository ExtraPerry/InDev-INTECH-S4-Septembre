package com.example.BotApi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

//Class.
@Entity
public class Item {
	
	//DB id value attribute.
	@Id
	@GeneratedValue
	private int id;
	//Attributes.
	private String name;							//The title of the message. Gives an idea of what it is in one sentence or a few words.
    private String link;							//The link that the message will contain.
    
    @ManyToMany
    @JsonManagedReference							//Item is the Parent of the Item_Tag relationship.
    private Set<Tag> tags = new HashSet<Tag>();		//Association to tags existing in the database.
    
    private Boolean modal;							//A boolean to keep track of whether the message originated from a modal or a normal message that was caught.
    private String description;						//The description given by the user OR the content of the message if it wasn't a modal form.
    
    @ManyToOne
    @JsonManagedReference							//Item is the Parent of the Item_DiscordUser relationship.
    private DiscordUser discordUser;				//Association to the user who made the message existing in the database.
    
    private String messageId;						//The id of the message in discord to which this is associated.
    private Long time;								//The time attribute should be in milliseconds as a Long value. This will help with keeping the data compatible between any type of language in theory.
	
    @ManyToMany
    @JsonManagedReference							//Item is the Parent of the Item_Category relationship.
    private Set<Category> categories = new HashSet<Category>();	//Association to categories existing in the database.
    
    //Constructor.
    public Item() {
    	
    }
    
    public Item(final String name, final String link, final Set<Tag> tags,final Boolean modal, final String description,final DiscordUser discordUser, final String messageId, final Long time) {
    	this.setName(name);
    	this.setLink(link);
    	this.setTags(tags);
    	this.setModal(modal);
    	this.setDescription(description);
    	this.setDiscordUser(discordUser);
    	this.setMessageId(messageId);
    	this.setTime(time);
    }
    
    //Getter & Setter.
    public int getId() {
    	return this.id;
    }
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Set<Tag> getTags() {
		return this.tags;
	}
	private void setTags(Set<Tag> tags) {
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
    public Set<Category> getCategories(){
    	return this.categories;
    }
	
	//Custom tags attribute.
    public Tag findTag(String name) {
    	for (Tag tag : this.getTags()) {
    		if (tag.getName().equals(name)) {
    			return tag;
    		}
    	}
    	return null;
    }
    
	public void addTag(Tag tag) {
		this.getTags().add(tag);
	}
	
	public void removeTag(Tag tag) {
		this.getTags().remove(tag);
	}
	
	//Custom categories attribute.
	public Category findCategory(String name) {
    	for (Category category : this.getCategories()) {
    		if (category.getName().equals(name)) {
    			return category;
    		}
    	}
    	return null;
    }
	
	public void addCategory(Category category) {
		this.getCategories().add(category);
	}
	
	public void removeCategory(Category category) {
		this.getCategories().remove(category);
	}
}
