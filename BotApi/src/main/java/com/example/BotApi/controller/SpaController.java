package com.example.BotApi.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BotApi.model.Category;
import com.example.BotApi.model.DiscordUser;
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
	//Get a page of items.
	@GetMapping("/getItemsPage")	//This will be used to get an infinite scrolling page by requesting it in portions at a time.
	public ResponseEntity<?> getItemPage(@RequestParam final Map<String, String> params){
		
		//Check the pageFormat parameter values.
		PageFormat pageFormat = this.checkParams(params,"pageable", "item");
		if (pageFormat.isError()) {
			return pageFormat.getResponse();
		}
		
		//If the parameters are correct then retrieve the asked data.
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		Page<Item> page = this.getItemRepo().findAll(sortedBy);
		
		//Return the page though specify if the content is empty if it is.
		if (page.getContent().isEmpty()) {
			return new ResponseEntity<>(page, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
	
	//Get a page of tags.
	@GetMapping("/getTagsPage")		//This will be used to retrieve tags in packs at a time.
	public ResponseEntity<?> getTagsById(@RequestParam final Map<String, String> params){
		
		//Check the pageFormat parameter values.
		PageFormat pageFormat = this.checkParams(params,"pageable", "tag");
		if (pageFormat.isError()) {
			return pageFormat.getResponse();
		}
		
		//If the parameters are correct then retrieve the asked data.
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		Page<Tag> page = this.getTagRepo().findAll(sortedBy);
		
		//Return the page though specify if the content is empty if it is.
		if (page.getContent().isEmpty()) {
			return new ResponseEntity<>(page, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(page, HttpStatus.OK);
		
	}
	
	//Get a page of Categories.
	@GetMapping("/getCategoryPage")		//This will be used to retrieve tags in packs at a time.
	public ResponseEntity<?> getCategoryPage(@RequestParam final Map<String, String> params){
		
		//Check the pageFormat parameter values.
		PageFormat pageFormat = this.checkParams(params,"pageable", "category");
		if (pageFormat.isError()) {
			return pageFormat.getResponse();
		}
		
		//If the parameters are correct then retrieve the asked data.
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		Page<Category> page = this.getCategoryRepo().findAll(sortedBy);
		
		//Return the page though specify if the content is empty if it is.
		if (page.getContent().isEmpty()) {
			return new ResponseEntity<>(page, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(page, HttpStatus.OK);
		
	}
	
	//Get a page of items sorted by queried name. <-!!!-> No fuzzy matching yet.
	@GetMapping("/getNameFilteredItemPage")
	public ResponseEntity<?> getNameFilteredPage(@RequestParam final Map<String, String> params) {
		
		//Check the pageFormat parameter values.
		PageFormat pageFormat = this.checkParams(params,"pageableQuery", "item");
		if (pageFormat.isError()) {
			return pageFormat.getResponse();
		}
		
		//If everything checks out then return the results. If there were no results return an empty Set.
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		Page<Item> page = this.getItemRepo().findAllByNameStartingWith(params.get("q"), sortedBy);
		
		//Return the page though specify if the content is empty if it is.
		if (page.getContent().isEmpty()) {
			return new ResponseEntity<>(page, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
	
	//There is no point in searching by Id since the client does not always know all the tags that exist.
	//Will returns matching items containing the listed tags if they have one or more. So an OR condition not AND for the search.
	@GetMapping("/getTagsFilteredItemPage")		//This will get a list of items matching queried tags.
	public ResponseEntity<?> getTagsFilteredPage(@RequestParam final Map<String, String> params){
		
		//Check the pageFormat parameter values.
		PageFormat pageFormat = this.checkParams(params,"pageableQuery", "item");
		if (pageFormat.isError()) {
			return pageFormat.getResponse();
		}
		
		//Check if the tags in the query exist in the database and retrieve them if so.
		String[] tagNames = params.get("q").split(" ");
		Set<Tag> queriedTags = new HashSet<Tag>();
		for(String tagName : tagNames) {	//For each query check if the tag exists.
			Tag tag = this.getTagRepo().findByName(tagName);
			if (tag  != null) {	//If the tag exists add it to the list.
					queriedTags.add(tag);
			} else {	//If the tag does not exist return the string.
				return new ResponseEntity<>("One of the queried tags does not exist or query was not built properly.", HttpStatus.NOT_FOUND);
			}
		}
		
		//If everything checks out then return the results. If there were no results return an empty Set.
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		Page<Item> page = this.getItemRepo().findAllByTagsIn(queriedTags, sortedBy);
		
		//Return the page though specify if the content is empty if it is.
		if (page.getContent().isEmpty()) {
			return new ResponseEntity<>(page, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
	
	@GetMapping("/getDiscordUserFilteredItemPage")		//This will get a list of items matching queried userId.
	public ResponseEntity<?> getUserFilteredPage(@RequestParam final Map<String, String> params){
		
		//Check the pageFormat parameter values.
		PageFormat pageFormat = this.checkParams(params,"pageableQuery", "item");
		if (pageFormat.isError()) {
			return pageFormat.getResponse();
		}
		
		//Check if the DiscordUser in the query exist in the database and retrieve it if so.
		DiscordUser discordUser = this.getDiscordUserRepo().findByuserId(params.get("q"));
		if (discordUser == null) {
			return new ResponseEntity<>("The queried userId does not exist or query was not built properly.", HttpStatus.NOT_FOUND);
		}
		
		//If everything checks out then return the results. If there were no results return an empty Set.
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		Page<Item> page = this.getItemRepo().findAllBydiscordUser(discordUser, sortedBy);
				
		//Return the page though specify if the content is empty if it is.
		if (page.getContent().isEmpty()) {
			return new ResponseEntity<>(page, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
	
	//Get a page of tags sorted by queried name. This can be used to get proposed searches for tags. Like a small box under the search bar where you can click a suggested tag.
	//Note this endpoint should be called like after the person has stopped typing or every 0.5-1s.
	@GetMapping("/getNameFilteredTagPage")
	public ResponseEntity<?> getNameFilteredTagPage(@RequestParam final Map<String, String> params) {
			
		//Check the pageFormat parameter values.
		PageFormat pageFormat = this.checkParams(params,"pageableQuery", "tag");
		if (pageFormat.isError()) {
			return pageFormat.getResponse();
		}
			
		//If everything checks out then return the results. If there were no results return an empty Set.
		Pageable sortedBy = PageRequest.of(pageFormat.getPage(), pageFormat.getSize(), Sort.by(pageFormat.getSort()));
		Page<Tag> page = this.getTagRepo().findAllByNameStartingWith(params.get("q"), sortedBy);
			
		//Return the page though specify if the content is empty if it is.
		if (page.getContent().isEmpty()) {
			return new ResponseEntity<>(page, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(page, HttpStatus.OK);
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
	//Used to check GetRequest that need a Pageable return.
	private PageFormat checkParams(final Map<String, String> params,final String paramType, final String checkType) {
		
		switch(paramType) {	//Check if the request parameters are valid. Based on the indented parameters that should be received. if not return the HttpStatus.
		
			case "pageable":
				if (!(params.containsKey("page") && params.containsKey("size") && params.containsKey("sort") && (params.size() == 3))) {
					return new PageFormat(new ResponseEntity<>("Parameters are not valid. Must include [page, size and sort].", HttpStatus.BAD_REQUEST));
				}
				break;
				
			case "pageableQuery":
				if (!(params.containsKey("q") && params.containsKey("page") && params.containsKey("size") && params.containsKey("sort") && (params.size() == 4))) {
					return new PageFormat(new ResponseEntity<>("Parameters are not valid. Must include [q, page, size and sort].", HttpStatus.BAD_REQUEST));
				}
				break;
		}
		
		//Check & Convert to an integer the String value of the page & size parameter.
		int page;
		int size;
		try {
			page = Integer.parseInt(params.get("page"));
			size = Integer.parseInt(params.get("size"));
		}catch(NumberFormatException error) {
			return new PageFormat(new ResponseEntity<>("Page or Size was not a valid number. Must be an integer.", HttpStatus.BAD_REQUEST));
		}		
		
		//Get the sort String and decide which type of check it should go through.
		String sort = params.get("sort");
		switch(checkType) {	//Check if the sort string is a valid authorised one. Based on the type of Object that must be returned by the endpoint.
		
			case "item":
				if (!(sort.equals("id") || sort.equals("name") || sort.equals("discordUser") || sort.equals("time"))) {
					return new PageFormat(new ResponseEntity<>("Sort value is not valid. Try [id, name, discordUser or time].", HttpStatus.BAD_REQUEST));
				}
				break;
			
			case "tag":
				if (!(sort.equals("id") || sort.equals("name") || sort.equals("itemCount"))) {
					return new PageFormat(new ResponseEntity<>("Sort value is not valid. Try [id, name, itemCount].", HttpStatus.BAD_REQUEST));
				}
				break;
			
			case "category":
				if (!(sort.equals("id") || sort.equals("name") || sort.equals("itemCount") || sort.equals("tagCount"))) {
					return new PageFormat(new ResponseEntity<>("Sort value is not valid. Try [id, name, itemCount, tagCount].", HttpStatus.BAD_REQUEST));
				}
				break;

			case "generic":
				if (!(sort.equals("id") || sort.equals("name"))) {
					return new PageFormat(new ResponseEntity<>("Sort value is not valid. Try [id, name].", HttpStatus.BAD_REQUEST));
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
