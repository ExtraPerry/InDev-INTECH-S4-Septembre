package com.example.BotApi.repository;


import java.util.List;

import com.example.BotApi.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BotApi.model.Item;


public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByTitle(String title);

    List<Item> findByTitleContaining(String title);
}