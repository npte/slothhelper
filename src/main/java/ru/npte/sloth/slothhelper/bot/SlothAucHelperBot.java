package ru.npte.sloth.slothhelper.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.npte.sloth.slothhelper.httpclient.HttpClient;
import ru.npte.sloth.slothhelper.utils.ListUtils;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SlothAucHelperBot {

	private static final Logger logger = LoggerFactory.getLogger(SlothAucHelperBot.class);

	@Autowired
	private HttpClient client;

	private final String name;
	private final String token;
	private final String channel;

	public SlothAucHelperBot(String name, String token, String channel) {
		this.name = name;
		this.token = token;
		this.channel = channel;
	}

	public void sendMessage(List<String> messages) {
		if (ListUtils.isEmpty(messages)) {
			return;
		}

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.schedule(new Runnable() {
			private int i = 0;
			@Override
			public void run() {
				client.sendMessage(token, channel, messages.get(i));
				if (i + 1 < messages.size()) {
					i++;
				}
			}
		}, 1, TimeUnit.SECONDS);
	}
}