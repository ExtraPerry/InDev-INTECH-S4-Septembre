package com.example.BotApi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

//Class.
@Entity
public class Tag {
	
	//DB id value attribute.
	@Id
	@GeneratedValue
	private int id;
	//Attributes.
	private String name;
	
	@ManyToMany(mappedBy = "tags")	//MappedBy relates to the variable name in the other class it is associated to.
	private Set<Item> items = new HashSet<Item>();
	
	@ManyToMany(mappedBy = "tags")	//MappedBy relates to the variable name in the other class it is associated to.
	private Set<Category> categories = new HashSet<Category>();
	
	//Constructor.
	public Tag() {
		
	}
	
	public Tag(final String name) {
		this.setName(name);
	}
	
	//Getter & Setter.
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
