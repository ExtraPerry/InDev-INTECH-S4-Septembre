package com.example.BotApi.model;

import java.util.ArrayList;
import java.util.List;

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
	
	@ManyToMany(mappedBy = "tags")
	private List<Item> items = new ArrayList<Item>();
	
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
	public List<Item> getItems(){
		return this.items;
	}
	public void addItem(Item item) {
		this.getItems().add(item);
	}
}
