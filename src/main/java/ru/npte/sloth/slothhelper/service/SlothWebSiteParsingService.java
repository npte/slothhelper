package ru.npte.sloth.slothhelper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.npte.sloth.slothhelper.bot.SlothAucHelperBot;
import ru.npte.sloth.slothhelper.dto.AucItem;
import ru.npte.sloth.slothhelper.httpclient.HttpClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class SlothWebSiteParsingService {

    private static final Logger logger = LoggerFactory.getLogger(SlothWebSiteParsingService.class);

    @Autowired
    private HttpClient client;

    @Autowired
    private StorageService storageService;

    @Autowired
    private MessageCreationService messageCreationService;

    @Autowired
    private SlothAucHelperBot slothAucHelperBot;

    //@Scheduled(fixedRate=900000)
    @Scheduled(fixedRate=10000)
    public void getAucItemsNames() {
        logger.info("Execute auc parsing");
        List<String> aucItems = Arrays.stream(client.get().split("\\r?\\n"))
                .parallel()
                .filter(s -> !s.matches("^AUC_\\d{1,3} -1$"))
                .map(AucItem::new)
                .filter(i -> storageService.isNewAutItem(i))
                .map(item -> messageCreationService.createMessage(item))
                .collect(Collectors.toList());

        logger.info("Collect {} items", aucItems.size());

        slothAucHelperBot.sendMessage(aucItems);
    }
}