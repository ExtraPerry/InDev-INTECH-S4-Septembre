package com.example.BotApi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

//Class.
@Entity
public class Tag {
	
	//DB id value attribute.
	@Id
	@GeneratedValue
	private int id;
	//Attributes.
	private String name;
	private int itemCount;
	
	@ManyToMany(mappedBy = "tags")	//MappedBy relates to the variable name in the other class it is associated to.
	@JsonBackReference				//Tag is the Child of the Item_Tag relationship.
	private Set<Item> items = new HashSet<Item>();
	
	@ManyToOne
    @JsonManagedReference			//Tag is the Parent of the Tag_DiscordUser relationship.
    private DiscordUser discordUser;//Association to the user who made the tag existing in the database.
	
	@ManyToMany(mappedBy = "tags")	//MappedBy relates to the variable name in the other class it is associated to.
	@JsonBackReference				//Tag is the Child of the Category_Tag relationship.
	private Set<Category> categories = new HashSet<Category>();
	
	//Constructor.
	public Tag() {
		this.setItemCount(0);
	}
	
	public Tag(final String name, final DiscordUser discordUser) {
		this.setName(name);
		this.setItemCount(0);
		this.setDiscordUser(discordUser);
	}
	
	//Getter & Setter.
	public int getId() {
    	return this.id;
    }
	public String getName() {
		return name;
	}
	private void setName(final String name) {
		this.name = name.toLowerCase();
	}
	public int getItemCount() {
		return this.itemCount;
	}
	private void setItemCount(final int itemCount) {
		this.itemCount = itemCount;
	}
	public DiscordUser getDiscordUser() {
		return this.discordUser;
	}
	public void setDiscordUser(final DiscordUser discordUser) {
		this.discordUser = discordUser;
	}
	public Set<Item> getItems(){
		return this.items;
	}
	public Set<Category> getCategories(){
		return this.categories;
	}
	
	//Custom items attribute.
	public void addItem(final Item item) {
		this.getItems().add(item);
		this.setItemCount(this.getItemCount() + 1);
	}
	
	public void removeItem(final Item item) {
		this.getItems().remove(item);
		this.setItemCount(this.getItemCount() - 1);
	}
	public void addCategory(final Category category) {
		this.getCategories().add(category);
	}
	
	public void removeCategory(final Category category) {
		this.getCategories().remove(category);
	}
}
