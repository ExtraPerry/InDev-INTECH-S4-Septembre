package com.example.BotApi.controller;

import com.example.BotApi.model.Message;
import com.example.BotApi.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class IndexController {
	
	//Attributes.
	@Autowired	//Assign to the database.
	private MessageRepository messageRepo;
	
	//Endpoints.
	@GetMapping("/getMessages") //Get all the messages from the database that were given by the discord bot.
	public Iterable<Message> getMessages(){
		return getMessageRepo().findAll();
	}
	@PostMapping("/addMessage")	//Add a modal form report from the discord bot to the database.
    public String fallback(@RequestBody Message message) {
		this.getMessageRepo().save(message);
        return ("message added");
    }
	
    @PostMapping("/addMessageFallback")	//It's a testing endpoint for the discord bot to see if it connects properly. (Basically just sending a response).
    public String fallbackTest() {
        return ("Nothing added just returned that we recieved something.");
    }

    //Getter & Setters.
	public MessageRepository getMessageRepo() {
		return messageRepo;
	}
}