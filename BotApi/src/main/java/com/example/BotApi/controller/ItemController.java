package com.example.BotApi.controller;

import com.example.BotApi.item.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.BotApi.item.ItemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/menu/items")
public class ItemController {
    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Item>> findAll() {
        List<Item> items = service.findAll();
        return ResponseEntity.ok().body(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> find(@PathVariable("id") Long id) {
        Optional<Item> item = service.find(id);
        return ResponseEntity.of(item);
    }
}