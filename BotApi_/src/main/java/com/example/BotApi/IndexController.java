package com.example.BotApi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {


    @GetMapping("/messages")
    public String displayMessages() {
        return "{\"coord\":{\"lon\":2.3488,\"lat\":48.8534},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"base\":\"stations\",\"main\":{\"temp\":21.39,\"feels_like\":21.41,\"temp_min\":20.21,\"temp_max\":22.77,\"pressure\":1018,\"humidity\":70},\"visibility\":10000,\"wind\":{\"speed\":5.14,\"deg\":220},\"clouds\":{\"all\":20},\"dt\":1666786819,\"sys\":{\"type\":2,\"id\":2041230,\"country\":\"FR\",\"sunrise\":1666765651,\"sunset\":1666802484},\"timezone\":7200,\"id\":2988507,\"name\":\"Paris\",\"cod\":200}";
    }
}