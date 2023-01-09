package com.example.BotApi.repository;

import com.example.BotApi.model.DiscordMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscordMessageRepository extends JpaRepository<DiscordMessage, Integer> {
}
