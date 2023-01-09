package com.example.BotApi.controller;

import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BotApi.model.Item;
import com.example.BotApi.repository.DiscordUserRepository;
import com.example.BotApi.repository.ItemRepository;
import com.example.BotApi.repository.TagRepository;

@RestController
public class SpaController {
	
	//Attributes.
	@Autowired	//Assign to the database.
	private ItemRepository itemRepo;
	@Autowired
	private TagRepository tagRepo;
	@Autowired
	private DiscordUserRepository discordUserRepo;
	
	//Endpoints.
	@GetMapping("/getFirstItems")
	public SortedSet<Item> getFirstItems(){
		Iterable<Item> intialSortedSet = this.getItemRepo().findAll();
	}
	
	//Getter & Setters.
	public ItemRepository getItemRepo() {
		return this.itemRepo;
	}
	public TagRepository getTagRepo() {
		return this.tagRepo;
	}
	public DiscordUserRepository getDiscordUserRepo() {
		return this.discordUserRepo;
	}
}
