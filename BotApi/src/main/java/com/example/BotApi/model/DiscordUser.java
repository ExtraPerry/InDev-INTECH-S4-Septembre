package com.example.BotApi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "items" })	//To get the list of items a user owns and endpoint will have to be built for that.
public class DiscordUser {
	//DB id value attribute.
	@Id
	@GeneratedValue
	private int id;
	//Attributes.
	private String name;							//NameTag of the discord user.
	private String userId;							//UserId of the discord user.
	
	@OneToMany(mappedBy = "discordUser")			//MappedBy relates to the variable name in the other class it is associated to.
	@JsonBackReference								//DiscordUser is the Child of the Item_DiscordUser relationship.
	private Set<Item> Items = new HashSet<Item>();	//Association to the items created by the discord user.

	//Constructor.
	public DiscordUser() {
		
	}
	
	public DiscordUser(final String name, final String userId) {
		this.setName(name);
		this.setUserId(userId);
	}
	
	//Getter & Setter
	public int getId() {
    	return this.id;
    }
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(final String userId) {
		this.userId = userId;
	}
	public Set<Item> getItems(){
		return this.Items;
	}
	
	//Custom items attribute.
	public Item findItem(final String name) {
	    for (Item item : this.getItems()) {
	    	if (item.getName().equals(name)) {
	    		return item;
	    	}
	    }
	    return null;
	}
		
	public void addItem(final Item item) {
		this.getItems().add(item);
	}
	
	public void removeItem(final Item item) {
		this.getItems().remove(item);
	}
	
}
