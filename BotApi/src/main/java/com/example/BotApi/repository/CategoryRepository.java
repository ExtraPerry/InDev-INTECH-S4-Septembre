package com.example.BotApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BotApi.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
}
