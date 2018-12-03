package ru.npte.sloth.slothhelper.component;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;

import ru.npte.sloth.slothhelper.service.SlothWebSiteParsingService;

@Component
public class SlothHelperBot extends AbilityBot {

    @Autowired
    private SlothWebSiteParsingService slothWebSiteParsingService;

    private static final Logger logger = LoggerFactory.getLogger(SlothHelperBot.class);

    @Value("539413361:AAFEtAdBwlCnY9_ZcYUpdgCOEIl5Q99D41Y")
	private String token;

	@Value("SlothBot")
	private String username;

    @Value("157655912")
    private Integer creatorId;

    @Override
    public int creatorId() {
        return this.creatorId;
    }

    /*
	@Override
	public String getBotToken() {
		return token;
	}

	@Override
	public String getBotUsername() {
		return username;
	}
    */

    public SlothHelperBot(@Value("539413361:AAFEtAdBwlCnY9_ZcYUpdgCOEIl5Q99D41Y") String token, @Value("SlothBot") String username) {
        super(token, username);
    }


    /*
    @Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
			Message message = update.getMessage();
			SendMessage response = new SendMessage();
			Long chatId = message.getChatId();
			response.setChatId(chatId);
			String text = message.getText();
            try {
                response.setText(String.join("\r\n\r\n", slothWebSiteParsingService.getAucItemsNames()));
                response.enableHtml(true);
				execute(response);
				logger.info("Sent message \"{}\" to {}", text, chatId);
			} catch (Exception e) {
				logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
                e.printStackTrace();
			}
		}
	}
    */

    public Ability getAucList() {
        return Ability.builder()
            .name("lots")
            .info("return auction lots")
            .locality(Locality.ALL)
            .privacy(Privacy.PUBLIC)
            .action(ctx -> silent.send("Hellooo!", ctx.chatId()))
            .build();
    }

	@PostConstruct
	public void start() {
		logger.info("username: {}, token: {}", username, token);
	}

}