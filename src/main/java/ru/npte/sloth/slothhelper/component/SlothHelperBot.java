package ru.npte.sloth.slothhelper.component;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class SlothHelperBot  extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(SlothHelperBot.class);

    @Value("SlothBot")
	private String token;

	@Value("539413361:AAFEtAdBwlCnY9_ZcYUpdgCOEIl5Q99D41Y")
	private String username;

	@Override
	public String getBotToken() {
		return token;
	}

	@Override
	public String getBotUsername() {
		return username;
	}

    @Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
			Message message = update.getMessage();
			SendMessage response = new SendMessage();
			Long chatId = message.getChatId();
			response.setChatId(chatId);
			String text = message.getText();
			response.setText(text);
			try {
				execute(response);
				logger.info("Sent message \"{}\" to {}", text, chatId);
			} catch (TelegramApiException e) {
				logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
			}
		}
	}

	@PostConstruct
	public void start() {
		logger.info("username: {}, token: {}", username, token);
	}

}