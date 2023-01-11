package com.example.BotApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.BotApi.model.Category;
import com.example.BotApi.model.Item;
import com.example.BotApi.model.Tag;
import com.example.BotApi.model.Contract.PageFormat;
import com.example.BotApi.repository.CategoryRepository;
import com.example.BotApi.repository.DiscordUserRepository;
import com.example.BotApi.repository.ItemRepository;
import com.example.BotApi.repository.TagRepository;

@RestController
public class SpaController {
	
	//Attributes.
	@Autowired
	private ItemRepository itemRepo;
	@Autowired
	private TagRepository tagRepo;
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private DiscordUserRepository discordUserRepo;
	
	//Endpoints.
	@GetMapping("/getItemsPage")	//This will be used to get an infinite scrolling page by requesting it in portions at a time.
	public Page<Item> getItemPage(@RequestBody final PageFormat pageFormat){
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		return this.getItemRepo().findAll(sortedBy);
	}
	@GetMapping("/getTagsPage")		//This will help get a proposition of tags when trying to search for a specific tag inside of a search bar.
	public Page<Tag> getTags(@RequestBody final PageFormat pageFormat){
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		return this.getTagRepo().findAll(sortedBy);
	}
	@GetMapping("/getTagsById")		//This will be used to get a specific tag
	public Page<Tag> getTagsById(){
		return null;
	}
	@GetMapping("/getCategories")
	public Iterable<Category> getCategories(){
		Iterable<Category> categories = this.getCategoryRepo().findAll();
		
		return categories;
	}
	
	//Getter & Setters.
	private ItemRepository getItemRepo() {
		return this.itemRepo;
	}
	private List<Item> getItemRepoSorted(){
		return this.getItemRepo().findAllByOrderByIdAsc();
	}
	private TagRepository getTagRepo() {
		return this.tagRepo;
	}
	private CategoryRepository getCategoryRepo() {
		return this.categoryRepo;
	}
	private DiscordUserRepository getDiscordUserRepo() {
		return this.discordUserRepo;
	}
	
	//Custom functions.
	private List<Item> getTop50FromItems(List<Item> items){	//Note must be sorted list from "this.getItemRepoSorted()" function.
		//Get the size of the list
		int itemsSize = items.size();
		//if the total amount of items is lower than 50 just send the whole List.
		if (itemsSize <= 50) {
			return items;
		}
		//if the total amount is higher than 50 then parse it down to a size of 50.
		items = items.subList((itemsSize - 50) - 1, itemsSize - 1);
		//Then send the new list back.
		return items;
	}
	
}
