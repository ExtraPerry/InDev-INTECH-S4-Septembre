package com.example.BotApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BotApi.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

}
