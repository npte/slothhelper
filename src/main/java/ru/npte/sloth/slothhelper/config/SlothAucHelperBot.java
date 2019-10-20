package ru.npte.sloth.slothhelper.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlothAucHelperBot {

	private static final Logger logger = LoggerFactory.getLogger(SlothAucHelperBot.class);

	private final String name;
	private final String token;
	private final String channel;

	public SlothAucHelperBot(String name, String token, String channel) {
		this.name = name;
		this.token = token;
		this.channel = channel;
	}

	public void sendMessage(String message) {

	}
}