package com.example.BotApi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	
	@ManyToMany
	@JoinTable(
			name = "Tags",
			joinColumns = @JoinColumn(name = "tag_id"),
			inverseJoinColumns = @JoinColumn(name = "item_id")
	)
	private List<Item> Items = new ArrayList<Item>();
	
	//Constructor.
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
		return this.Items;
	}
}
