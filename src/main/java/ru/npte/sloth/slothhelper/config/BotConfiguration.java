package ru.npte.sloth.slothhelper.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class BotConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BotConfiguration.class);

    @Bean
    public SlothAucHelperBot slothAucHelperBot() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("bot.properties")) {

            prop.load(input);

        } catch (FileNotFoundException e) {
            logger.error("Bot config file not found", e);
        } catch (IOException e) {
            logger.error("Error while reading bot config file", e);
        }

        return new SlothAucHelperBot(prop.getProperty("bot.name"),
                prop.getProperty("bot.token"),
                prop.getProperty("channel.name"));
    }
}
