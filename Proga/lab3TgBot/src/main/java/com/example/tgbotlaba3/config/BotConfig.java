package com.example.tgbotlaba3.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("file:/Users/Timoxa/IdeaProjects/TgBotLaba3/src/main/resources/application.properties")
public class BotConfig {
	@Value("${bot.name}")
	String botName;
	@Value("${bot.token}")
	String botToken;
}