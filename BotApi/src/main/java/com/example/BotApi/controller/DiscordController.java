package com.example.BotApi.controller;

import com.example.BotApi.model.DiscordUser;
import com.example.BotApi.model.Item;
import com.example.BotApi.model.Tag;
import com.example.BotApi.model.Contract.DiscordMessage;
import com.example.BotApi.repository.DiscordUserRepository;
import com.example.BotApi.repository.ItemRepository;
import com.example.BotApi.repository.TagRepository;

import java.util.HashSet;
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
		//A solution to this would be to have on startup or a routine check to find duplicate titles or links and refference them to a seperate list.
		//Like that the admin on the website can decide on how to manage it.
		
		//Check if the discord user exists else make a new user. <----->
		DiscordUser discordUser = this.getDiscordUserRepo().findByuserId(discordMessage.getUserId());
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