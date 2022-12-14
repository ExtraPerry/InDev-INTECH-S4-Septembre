package com.example.BotApi.controller;

import com.example.BotApi.item.InMemoryItemRepository;
import com.example.BotApi.item.Item;
import com.example.BotApi.item.Itemrepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// ✨ New! 👇 Compact imports ✨
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/menu/items")
public class ItemController {
    @Autowired
    private InMemoryItemRepository repo;

    public ItemController(InMemoryItemRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Item>> findAll() {
        List<Item> items = repo.findAll();
        return ResponseEntity.ok().body(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> find(@PathVariable("id") Long id) {
        Optional<Item> item = repo.findById(id);
        return ResponseEntity.of(item);
    }

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item) {
        Item created = repo.create(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> update(
            @PathVariable("id") Long id,
            @RequestBody Item updatedItem) {

        Optional<Item> updated = repo.update(id, updatedItem);

        return updated
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> {
                    Item created = repo.create(updatedItem);
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(created.getId())
                            .toUri();
                    return ResponseEntity.created(location).body(created);
                });
    }

    // ✨ New! 👇 DELETE definition ✨
    @DeleteMapping("/{id}")
    public ResponseEntity<Item> delete(@PathVariable("id") Long id) {
        repo.delete(id);
        return ResponseEntity.noContent().build();
    }
}