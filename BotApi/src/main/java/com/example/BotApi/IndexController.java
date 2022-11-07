package com.example.BotApi;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


//@CrossOrigin(origins = "http://localhost:8080/messages")
@RestController

public class IndexController {

    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @GetMapping("/messages")
    public String displayMessages() {
        return "ok";
    }
    @GetMapping("/addMessage")
    public String test() {
        return "okaaaay";
    }
}