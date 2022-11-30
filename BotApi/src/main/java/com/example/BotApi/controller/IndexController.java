package com.example.BotApi.controller;

import com.example.BotApi.model.Message;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController

public class IndexController {

    private List<Message> messages = new ArrayList<Message>();

    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @GetMapping("/messages")
    public String displayMessages() {

        return "ok";
    }

    @PostMapping("/addMessage")
    public String test(@RequestBody Message a) {
        System.out.println(a.getContent());
        Message message = new Message(
                a.getId(),
                a.getContent(),
                a.getAuthor()
        );
        Gson gson = new Gson();
        String json = gson.toJson(message);
        System.out.println(json);
        this.messages.add(a);
        return (json);
    }
}