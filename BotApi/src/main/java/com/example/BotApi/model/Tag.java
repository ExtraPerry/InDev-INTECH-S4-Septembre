package com.example.BotApi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	
	@ManyToMany(mappedBy = "tags")	//MappedBy relates to the variable name in the other class it is associated to.
	@JsonBackReference				//Tag is the Child of the Category_Tag relationship.
	private Set<Category> categories = new HashSet<Category>();
	
	//Constructor.
	public Tag() {
		this.setItemCount(0);
	}
	
	public Tag(final String name) {
		this.setName(name);
		this.setItemCount(0);
	}
	
	//Getter & Setter.
	public int getId() {
    	return this.id;
    }
	public String getName() {
		return name;
	}
	private void setName(final String name) {
		this.name = name;
	}
	public int getItemCount() {
		return this.itemCount;
	}
	private void setItemCount(final int itemCount) {
		this.itemCount = itemCount;
	}
	private Set<Item> getItems(){
		return this.items;
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
		this.setItemCount(this.getItems().size());
	}
	
	public void removeItem(final Item item) {
		this.getItems().remove(item);
		this.setItemCount(this.getItems().size());
	}
}
