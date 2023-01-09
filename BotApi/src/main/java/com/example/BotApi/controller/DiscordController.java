package com.example.BotApi.controller;

import com.example.BotApi.model.DiscordMessage;
import com.example.BotApi.model.DiscordUser;
import com.example.BotApi.model.Item;
import com.example.BotApi.model.Tag;
import com.example.BotApi.repository.DiscordMessageRepository;
import com.example.BotApi.repository.DiscordUserRepository;
import com.example.BotApi.repository.ItemRepository;
import com.example.BotApi.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class DiscordController {
	
	//Attributes.
	@Autowired	//Assign to the database.
	private DiscordMessageRepository messageRepo;
	@Autowired
	private ItemRepository itemRepo;
	@Autowired
	private TagRepository tagRepo;
	@Autowired
	private DiscordUserRepository discordUserRepo;
	
	//Endpoints.
	@GetMapping("/getMessages") //Get all the messages from the database that were given by the discord bot.
	public Iterable<DiscordMessage> getMessages(){
		return getMessageRepo().findAll();
	}
	@PostMapping("/addMessage")	//Add a modal form report from the discord bot to the database.
    public String fallback(@RequestBody DiscordMessage discordMessage) {
		
		//Check if the discord user exists else make a new user. <----->
		DiscordUser discordUser = this.findDiscordUserByUserId(this.getDiscordUserRepo().findAll(), discordMessage.getUserId());
		if (discordUser == null) {	
			//Make a new user and store it in the local variable then into the database.
			discordUser = this.getDiscordUserRepo().save(
					new DiscordUser(
							discordMessage.getUserTag(), 
							discordMessage.getUserId()
					));
		}
		
		//Check if each tag in the list exists else make a new tag for the list if needed.	<----->
		List<Tag> tagList = new ArrayList<Tag>();
		for (String messageTag : discordMessage.getTags()) {
			//Check if tag exists.
			Tag tag = this.findTagByName(this.getTagRepo().findAll(), messageTag);
			if (tag == null) {
				//Make a new tag and store it in the local variable then into the database.
				tag = this.getTagRepo().save(new Tag(messageTag));
			}
			//Save tag to the tag list.
			tagList.add(tag);
		}
		
		//Create a new Item to store the message date into the database.	<----->
		Item newItem = this.getItemRepo().save(
				new Item(
					discordMessage.getTitle(),
					discordMessage.getLink(),
					tagList,
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
        return ("message added");
    }
	
    @PostMapping("/addMessageFallback")	//It's a testing endpoint for the discord bot to see if it connects properly. (Basically just sending a response).
    public String fallbackTest() {
        return ("Nothing added just returned that we recieved something.");
    }

    //Getter & Setters.
	public DiscordMessageRepository getMessageRepo() {
		return messageRepo;
	}
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
	public DiscordUser findDiscordUserByUserId(final Iterable<DiscordUser> discordUserIterable, final String userId) {
		for (DiscordUser discordUser : discordUserIterable) {
			if (userId.equals(discordUser.getUserId())) {
				return discordUser;
			}
		}
		return null;
	}
	
	public Tag findTagByName (final Iterable<Tag> tagIterable, final String name) {
		for (Tag tag : tagIterable) {
			if (name.equals(tag.getName())) {
				return tag;
			}
		}
		return null;
	}
}