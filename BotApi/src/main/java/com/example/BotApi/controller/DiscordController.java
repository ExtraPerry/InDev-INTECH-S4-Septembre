package com.example.BotApi.controller;

import com.example.BotApi.model.Category;
import com.example.BotApi.model.DiscordUser;
import com.example.BotApi.model.Item;
import com.example.BotApi.model.Tag;
import com.example.BotApi.model.Contract.DiscordMessage;
import com.example.BotApi.repository.DiscordUserRepository;
import com.example.BotApi.repository.ItemRepository;
import com.example.BotApi.repository.TagRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DiscordController {
	
	//Attributes.
	@Autowired
	private ItemRepository itemRepo;
	@Autowired
	private TagRepository tagRepo;
	@Autowired
	private DiscordUserRepository discordUserRepo;
	
	//Endpoints.
	@PostMapping("/addMessage")	//Add a modal form report from the discord bot to the database.
    public ResponseEntity<?> addMessage(@RequestBody final DiscordMessage discordMessage) {
		
		//<-!!!->
		//When creating a new tag or user and only when creating a new tag or user for the first time if two messages are received at aprox the same time.
		//Then it will create a duplicate tag or user inside of the database.
		//This won't happen though if the tag or user already exists inside of the database beforehand.
		
		//<-!!!->
		//Also technically if a user sends the exact identical modal forms or message it'll make a new item regardless.
		//A solution to this would be to have on startup or a routine check to find duplicate titles or links and reference them to a separate list.
		//Like that the admin on the website can decide on how to manage it.
		
		//Check if the discord user exists else make a new user. <-----> Check first if more than one user with the same id exist. If so merge em back together.
		ArrayList<DiscordUser> discordUserList = this.getDiscordUserRepo().findAllByuserId(discordMessage.getUserId());
		
		DiscordUser discordUser = null;					//Make an empty discordUser.
		if (!discordUserList.isEmpty()) {				//If the discordUserList is not empty assign first result to discordUser.
			discordUser = discordUserList.get(0);
			if (discordUserList.size() > 1) {			//Check if the list is bigger than 1. If so there is a duplicate object of the same user.
				discordUserList.remove(discordUser);	//Then remove the initial first result of the discordUser from the list.
				for (DiscordUser dupplicateDiscordUser : discordUserList) {	//Now go through the list that now only contain the duplicate users.
					for (Item item : dupplicateDiscordUser.getItems()) {	//Transfer items belonging to the duplicate to the original.
						discordUser.addItem(item);
						dupplicateDiscordUser.removeItem(item);
					}
					for (Tag tag : dupplicateDiscordUser.getTags()) {	//Transfer tags belonging to the duplicate to the original.
						discordUser.addTag(tag);
						dupplicateDiscordUser.removeTag(tag);
					}
					for (Category category : dupplicateDiscordUser.getCategories()) {	//Transfer categories belonging to the duplicate to the original.
						discordUser.addCategory(category);
						dupplicateDiscordUser.removeCategory(category);
					}
					this.getDiscordUserRepo().delete(dupplicateDiscordUser);	//Finally removes the duplicate from the repository.
					System.out.println("!!! There was a dupplicate user. !!!");
				}
			}	//This is a rare exception upon creation of the user. Where two messages are received at milliseconds of interval and check the database if one already exists. 
		}		//Problem is if they check at such close times the first one hasn't created the user yet. So the second one creates a duplicated user.
				//But the bot only knows users by their userId (from discord) so when expecting one result looking for the user by it's userId it returns 2 when there should only be 1.
				//Thus for the next time that duplicate user is requested there must be a system to handle this.
		
		if (discordUser == null) {	
			//Make a new user and store it in the local variable then into the database.
			discordUser = this.getDiscordUserRepo().save(
					new DiscordUser(
							discordMessage.getUserTag(), 
							discordMessage.getUserId()
					));
		}
		
		//Check if each tag in the list exists else make a new tag for the list if needed.	<----->
		Set<Tag> tagSet = new HashSet<Tag>();
		for (String messageTag : discordMessage.getTags()) {
			messageTag.toLowerCase();
			//Check if tag exists.
			Tag tag = this.getTagRepo().findByName(messageTag);
			if (tag == null) {
				//Make a new tag and store it in the local variable then into the database.
				tag = this.getTagRepo().save(new Tag(messageTag, discordUser));
			}
			//Save tag to the tag list.
			tagSet.add(tag);
		}
		
		//Create a new Item to store the message date into the database.	<----->
		Item newItem = this.getItemRepo().save(
				new Item(
					discordMessage.getTitle(),
					discordMessage.getLink(),
					tagSet,
					discordMessage.isModal(),
					discordMessage.getDescription(),
					discordUser,
					discordMessage.getMessageId(),
					discordMessage.getTime()
				));
		
		//Sync up the relation between the tags & user to the item.
		newItem.getDiscordUser().addItem(newItem);
		for (Tag tag : newItem.getTags()) {
			tag.addItem(newItem);
		}
		
		//Send response message to confirm everything was done correctly.
        return new ResponseEntity<>("message added", HttpStatus.OK);
    }
	
    @PostMapping("/addMessageFallback")	//It's a testing endpoint for the discord bot to see if it connects properly. (Basically just sending a response).
    public ResponseEntity<?> fallback() {
        return new ResponseEntity<>("Nothing added just returned that we recieved something.", HttpStatus.OK);
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
	
	//Custom.
	
}