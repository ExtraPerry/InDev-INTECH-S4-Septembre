package com.example.BotApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BotApi.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
