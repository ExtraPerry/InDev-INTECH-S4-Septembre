package com.example.BotApi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

//Class.
@Entity
public class Category {
		
	//DB id value attribute.
	@Id
	@GeneratedValue
	private int id;
	//Attributes.
	private String name;
	
	@ManyToMany(mappedBy = "categories")	//MappedBy relates to the variable name in the other class it is associated to.
	@JsonBackReference						//Category is the Child of the Item_Category relationship.
	private Set<Item> items = new HashSet<Item>();
	
	@ManyToMany
	@JsonManagedReference					//Category is the Parent of the Category_Tag relationship.
	private Set<Tag> tags = new HashSet<Tag>();
		
	//Constructor.
	public Category() {
			
	}
		
	public Category(final String name) {
		this.setName(name);
	}
		
	//Getter & Setter.
	public int getId() {
    	return this.id;
    }
	public String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	public Set<Item> getItems(){
		return this.items;
	}
		
	//Custom items attribute.
	public Item findItem(String name) {
	    for (Item item : this.getItems()) {
	    	if (item.getName().equals(name)) {
	    		return item;
	    	}
	    }
	    return null;
	}
		
	public void addItem(Item item) {
		this.getItems().add(item);
	}
	
	public void removeItem(Item item) {
		this.getItems().remove(item);
	}
}

