package com.example.BotApi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	public Object getItemPage(@RequestParam final Map<String, String> params){
		//Check if parameters are correct.
		if (!(params.containsKey("page") && params.containsKey("size") && params.containsKey("sort"))) {
			return "Parameters are not valid.";
		}
		//Check & Convert to an integer the String value of the page & size parameter.
		int page;
		int size;
		try {
			page = Integer.parseInt(params.get("page"));
			size = Integer.parseInt(params.get("size"));
		}catch(NumberFormatException error) {
			return "Page or Size was not a valid number.";
		}
		
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		return this.getItemRepo().findAll(sortedBy);
	}
	@GetMapping("/getTagsPage")		//This will help get a proposition of tags when trying to search for a specific tag inside of a search bar.
	public Page<Tag> getTags(@RequestParam final Map<String, String> params){
		Pageable sortedBy = PageRequest.of();
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
	
}
