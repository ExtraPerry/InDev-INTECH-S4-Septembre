package com.example.BotApi.controller;

import com.example.BotApi.contract.MessageContract;
import org.springframework.web.bind.annotation.*;


@RestController

public class IndexController {

    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @GetMapping("/messages")
    public String displayMessages() {
        return "ok";
    }
    @PostMapping("/addMessage")
    public String test(@RequestBody MessageContract messageContract) {
        System.out.println(messageContract.getContent());
        return "okaaaay";
    }
}