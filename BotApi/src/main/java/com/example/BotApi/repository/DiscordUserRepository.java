package com.example.BotApi.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BotApi.model.DiscordUser;

public interface DiscordUserRepository extends JpaRepository<DiscordUser, Integer> {

	public DiscordUser findByuserId(String userId);
	
	public ArrayList<DiscordUser> findAllByuserId(String userId);
}
