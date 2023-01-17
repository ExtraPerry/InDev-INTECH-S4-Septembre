package com.example.BotApi.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		
		//Check if the request parameters are valid. if not return the string.
		if (!(params.containsKey("page") && params.containsKey("size") && params.containsKey("sort"))) {
			return "Parameters are not valid. Must include [page, size and sort].";
		}
		
		//Check & Convert to an integer the String value of the page & size parameter.
		int page;
		int size;
		try {
			page = Integer.parseInt(params.get("page"));
			size = Integer.parseInt(params.get("size"));
		}catch(NumberFormatException error) {
			return "Page or Size was not a valid number. Must be an integer.";
		}		
		
		//Check if the sort string is a valid authorized one.
		String sort = params.get("sort");
		if (!(sort.equals("id") || sort.equals("name") || sort.equals("discordUser") || sort.equals("time"))) {
			return "Sort value is not valid. Try [id, name, discordUser or time].";
		}
		
		//If the parameters are correct then retrieve the asked data.
		Pageable sortedBy = PageRequest.of(page, size, Sort.by(sort));
		return this.getItemRepo().findAll(sortedBy);
	}
	
	
	@GetMapping("/getTagsPage")		//This will get a list of items matching queried tags.
	public Object getTags(@RequestParam final Map<String, String> params){
		
		//Check if the request parameters are valid. if not return the string.
		if (!params.containsKey("q")) {
			return "Parameters are not valid. Must include [q].";
		}
		
		//Check if the tags in the query exist in the database and retrieve them if so.
		String[] tagNames = params.get("q").split(" ");
		Set<Tag> queriedTags = new HashSet<Tag>();
		for(String tagName : tagNames) {	//For each query check if the tag exists.
			Tag tag = this.findTagByName(this.getTagRepo().findAll(), tagName);
			if (tag  != null) {	//If the tag exists add it to the list.
					queriedTags.add(tag);
			} else {	//If the tag does not exist return the string.
				return "One of the queried tags does not exist or query was not built properly.";
			}
		}
		
		//Find the tag with the smallest list of items to simplify the search.
		Tag smallestTag = null;
		for(Tag tag : queriedTags) {	//We are searching for the tag with the smallest amount of items it relates to.
			if (smallestTag != null) {	//Check if there is a tag considered smallest if not then add the tag.
				if (smallestTag.getItems().size() > tag.getItems().size()) {	//Check if current tag is smaller than previous tag.
					smallestTag = tag;	//If current tag is smaller assign it to smallestTag.
				}
			} else {
				smallestTag = tag;		//If there is no smallest tag assign current tag.
			}
		}
		
		//Compare all tags to the list of the smallest tag. To make a results Set.
		Set<Item> results = new HashSet<Item>();
		boolean tagsCheck = true;
		for(Item item : smallestTag.getItems()) {	//Verify all items of the list.
			tagsCheck = true;
			for(Tag tag : queriedTags) {	//Verify if the item contains the queried tags.
				if (!item.getTags().contains(tag)) {	//If it does not break and ignore the item. (tagsCheck = false).
					tagsCheck = false;
					break;
				}
			}
			if (tagsCheck) {	//If tagsCheck = false then it implies the item does not meet criteria so must be ignored.
				results.add(item);	//If it does meet criteria add it the the results.
			}
		}
		
		//If everything checks out then return the results. If there were no results return an empty Set.
		return results;
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
	public Tag findTagByName (final Iterable<Tag> tagIterable, final String name) {
		for (Tag tag : tagIterable) {
			if (name.equals(tag.getName())) {
				return tag;
			}
		}
		return null;
	}
}
