package com.example.BotApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BotApi.model.DiscordUser;

public interface DiscordUserRepository extends JpaRepository<DiscordUser, Integer> {

	public DiscordUser findByuserId(String userId);
}
