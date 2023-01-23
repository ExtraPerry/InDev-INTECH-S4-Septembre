package com.example.BotApi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BotApi.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

	public Tag findByName(String name);
	
	public Page<Tag> findAllByNameStartingWithIgnoreCase(String name, Pageable pageable);
}
