package com.example.BotApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BotApi.model.Category;
import com.example.BotApi.model.Item;
import com.example.BotApi.model.Tag;
import com.example.BotApi.repository.CategoryRepository;
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
	private CategoryRepository categoryRepo;
	@Autowired
	private DiscordUserRepository discordUserRepo;
	
	//Endpoints.
	@GetMapping("/getInitialItems")
	public List<Item> getInitialItems(){
		List<Item> items = this.getItemRepo().findAllByOrderByIdAsc();
		
		return items;
	}
	@GetMapping("/getTags")
	public Iterable<Tag> getTags(){
		Iterable<Tag> tags = this.getTagRepo().findAll();
		
		return tags;
	}
	@GetMapping("/getCategories")
	public Iterable<Category> getCategories(){
		Iterable<Category> categories = this.getCategoryRepo().findAll();
		
		return categories;
	}
	
	//Getter & Setters.
	public ItemRepository getItemRepo() {
		return this.itemRepo;
	}
	public TagRepository getTagRepo() {
		return this.tagRepo;
	}
	public CategoryRepository getCategoryRepo() {
		return this.categoryRepo;
	}
	public DiscordUserRepository getDiscordUserRepo() {
		return this.discordUserRepo;
	}
}
