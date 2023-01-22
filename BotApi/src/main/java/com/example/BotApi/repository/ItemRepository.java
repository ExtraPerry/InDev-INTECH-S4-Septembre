package com.example.BotApi.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BotApi.model.Item;
import com.example.BotApi.model.Tag;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	public List<Item> findAllByOrderByIdAsc();
	
	public Page<Item> findAllByTagsIn(Set<Tag> tags, Pageable pageable);
	
	public Page<Item> findByName(String name, Pageable pageable);
}
