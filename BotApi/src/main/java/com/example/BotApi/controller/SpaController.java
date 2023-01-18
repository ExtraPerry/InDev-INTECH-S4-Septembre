package com.example.BotApi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	@CrossOrigin(origins = "http://127.0.0.1:5173/")
	@GetMapping("/getItemsPage")	//This will be used to get an infinite scrolling page by requesting it in portions at a time.
	public Object getItemPage(@RequestParam final Map<String, String> params){
		//Check if the request parameters are valid. if not return the string. If they are the check string is null.
		String check = this.checkMapParam(params);
		if (check != null) return check;
		//If the parameters are correct then retrieve the asked data.
		Pageable sortedBy = PageRequest.of(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("size")), Sort.by(params.get("sort")));
		return this.getItemRepo().findAll(sortedBy);
	}
	@GetMapping("/getTagsPage")		//This will help get a proposition of tags when trying to search for a specific tag inside of a search bar.
	public Object getTags(@RequestParam final Map<String, String> params){
		//Check if the request parameters are valid. if not return the string. If they are the check string is null.
		String check = this.checkMapParam(params);
		if (check != null) return check;
		//If the parameters are correct then retrieve the asked data.
		Pageable sortedBy = PageRequest.of(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("size")), Sort.by(params.get("sort")));
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
	private String checkMapParam(final Map<String, String> params) {
		//Check if required parameters exist.
		if (!(params.containsKey("page") && params.containsKey("size") && params.containsKey("sort"))) {
			return "Parameters are not valid. Must include [page, size and sort].";
		}
		//Check & Convert to an integer the String value of the page & size parameter.
		try {
			int page = Integer.parseInt(params.get("page"));
			int size = Integer.parseInt(params.get("size"));
		}catch(NumberFormatException error) {
			return "Page or Size was not a valid number. Must be an integer.";
		}		
		//Check if the sort string is a valid authorised one.
		String sort = params.get("sort");
		if (!(sort.equals("id") || sort.equals("name") || sort.equals("discordUser") || sort.equals("time"))) {
			return "Sort value is not valid. Try [id, name, discordUser or time].";
		}
		//If everything checks out return a null value.
		return null;
	}
}
