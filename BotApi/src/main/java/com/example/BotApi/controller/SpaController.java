package com.example.BotApi.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BotApi.model.Category;
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
	//Get a page of items.
	@GetMapping("/getItemsPage")	//This will be used to get an infinite scrolling page by requesting it in portions at a time.
	public Object getItemPage(@RequestParam final Map<String, String> params){
		
		//Check if the request parameters are valid. if not return the string.
		if (!(params.containsKey("page") && params.containsKey("size") && params.containsKey("sort"))) {
			return "Parameters are not valid. Must include [page, size and sort].";
		}
		
		//Check the pageFormat parameter values.
		PageFormat pageFormat = this.checkPageableParams(params, "item");
		if (pageFormat == null) {
			return "Paging parameter values were incorrect. Page & Size should be integer. Sort should be a string and be named properly.";
		}
		
		//If the parameters are correct then retrieve the asked data.
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		return this.getItemRepo().findAll(sortedBy);
	}
	
	//Get a page of tags.
	@GetMapping("/getTagsPage")		//This will be used to retrieve tags in packs at a time.
	public Object getTagsById(@RequestParam final Map<String, String> params){
		
		//Check if the request parameters are valid. if not return the string.
		if (!(params.containsKey("page") && params.containsKey("size") && params.containsKey("sort"))) {
			return "Parameters are not valid. Must include [page, size and sort].";
		}
		
		//Check the pageFormat parameter values.
		PageFormat pageFormat = this.checkPageableParams(params, "tag");
		if (pageFormat == null) {
			return "Paging parameter values were incorrect. Page & Size should be integer. Sort should be a string and be named properly.";
		}
		
		//If the parameters are correct then retrieve the asked data.
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		return this.getTagRepo().findAll(sortedBy);
	}
	
	//Get a page of Categories.
	@GetMapping("/getCategoryPage")		//This will be used to retrieve tags in packs at a time.
	public Object getCategoryPage(@RequestParam final Map<String, String> params){
		
		//Check if the request parameters are valid. if not return the string.
		if (!(params.containsKey("page") && params.containsKey("size") && params.containsKey("sort"))) {
			return "Parameters are not valid. Must include [page, size and sort].";
		}
		
		//Check the pageFormat parameter values.
		PageFormat pageFormat = this.checkPageableParams(params, "category");
		if (pageFormat == null) {
			return "Paging parameter values were incorrect. Page & Size should be integer. Sort should be a string and be named properly.";
		}
		
		//If the parameters are correct then retrieve the asked data.
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		return this.getCategoryRepo().findAll(sortedBy);
	}
	
	//Get a page of discordUsers.
	@GetMapping("/getDiscordUsersPage")		//This will be used to retrieve tags in packs at a time.
	public Object getDiscordUsersPage(@RequestParam final Map<String, String> params){
		
		//Check if the request parameters are valid. if not return the string.
		if (!(params.containsKey("page") && params.containsKey("size") && params.containsKey("sort"))) {
			return "Parameters are not valid. Must include [page, size and sort].";
		}
		
		//Check the pageFormat parameter values.
		PageFormat pageFormat = this.checkPageableParams(params, "generic");
		if (pageFormat == null) {
			return "Paging parameter values were incorrect. Page & Size should be integer. Sort should be a string and be named properly.";
		}
		
		//If the parameters are correct then retrieve the asked data.
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		return this.getDiscordUserRepo().findAll(sortedBy);
	}
	
	//Get a page of items sorted by queried name.
	@GetMapping("/getNameFilteredPage")
	public Object getNameFilteredPage(@RequestParam final Map<String, String> params) {
		//Check if the request parameters are valid. if not return the string.
		if (!(params.containsKey("q") && params.containsKey("page") && params.containsKey("size") && params.containsKey("sort"))) {
			return "Parameters are not valid. Must include [q, page, size and sort].";
		}
		
		//Check the pageFormat parameter values.
		PageFormat pageFormat = this.checkPageableParams(params, "item");
		if (pageFormat == null) {
			return "Paging parameter values were incorrect. Page & Size should be integer. Sort should be a string named [id, name, discordUser or time].";
		}
		
		//If everything checks out then return the results. If there were no results return an empty Set.
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		return this.getItemRepo().findByName(params.get("q"), sortedBy);
	}
	
	//There is no point in searching by Id since the client does not always know all the tags that exist.
	//Will returns matching items containing the listed tags if they have one or more. So an OR condition not AND for the search.
	@GetMapping("/getTagsFilteredPage")		//This will get a list of items matching queried tags.
	public Object getTagsFilteredPage(@RequestParam final Map<String, String> params){
		//Check if the request parameters are valid. if not return the string.
		if (!(params.containsKey("q") && params.containsKey("page") && params.containsKey("size") && params.containsKey("sort"))) {
			return "Parameters are not valid. Must include [q, page, size and sort].";
		}
		
		//Check the pageFormat parameter values.
		PageFormat pageFormat = this.checkPageableParams(params, "item");
		if (pageFormat == null) {
			return "Paging parameter values were incorrect. Page & Size should be integer. Sort should be a string named [id, name, discordUser or time].";
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
		
		//If everything checks out then return the results. If there were no results return an empty Set.
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		return this.getItemRepo().findAllByTagsIn(queriedTags, sortedBy);
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
	
	private PageFormat checkPageableParams(final Map<String, String> params, final String checkType) {
		//Check & Convert to an integer the String value of the page & size parameter.
		int page;
		int size;
		try {
			page = Integer.parseInt(params.get("page"));
			size = Integer.parseInt(params.get("size"));
		}catch(NumberFormatException error) {
			//return "Page or Size was not a valid number. Must be an integer.";
			return null;
		}		
		
		//Get the sort String and decide which type of check it should go through.
		String sort = params.get("sort");
		switch(checkType) {
		
		case "item":
			//Check if the sort string is a valid authorized one.
			if (!(sort.equals("id") || sort.equals("name") || sort.equals("discordUser") || sort.equals("time"))) {
				//return "Sort value is not valid. Try [id, name, discordUser or time].";
				return null;
			}
			break;
			
		case "tag":
			//Check if the sort string is a valid authorized one.
			if (!(sort.equals("id") || sort.equals("name") || sort.equals("itemCount"))) {
				//return "Sort value is not valid. Try [id, name, itemCount].";
				return null;
			}
			break;
			
		case "category":
			//Check if the sort string is a valid authorized one.
			if (!(sort.equals("id") || sort.equals("name") || sort.equals("itemCount") || sort.equals("tagCount"))) {
				//return "Sort value is not valid. Try [id, name, itemCount, tagCount].";
				return null;
			}
			break;

		case "generic":
			//Check if the sort string is a valid authorized one.
			if (!(sort.equals("id") || sort.equals("name"))) {
				//return "Sort value is not valid. Try [id, name].";
				return null;
			}
			break;
			
		default:
			//If the search type is null or wrong then sort by Id. Since all models should have an Id.
			sort = "id";
			break;
		}
		
		//If all check out return a PageFormat.
		return new PageFormat(page, size, sort);

	}
	
}
