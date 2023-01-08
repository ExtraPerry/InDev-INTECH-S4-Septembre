package com.example.BotApi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class DiscordUser {
	//DB id value attribute.
	@Id
	@GeneratedValue
	private int id;
	//Attributes.
	private String name;								//NameTag of the discord user.
	private String userId;								//UserId of the discord user.
	
	@OneToMany
	private List<Item> Items = new ArrayList<Item>();	//Association to the items created by the discord user.

	
	//Getter & Setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
