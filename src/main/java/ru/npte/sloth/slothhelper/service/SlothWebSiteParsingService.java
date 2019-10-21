package ru.npte.sloth.slothhelper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
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

    //@Scheduled(fixedRate=900000)
    //@Scheduled(fixedRate=10000)
    public void getAucItemsNames() {

        List<String> aucItems = Arrays.stream(client.get().split("\\r?\\n"))
                .parallel()
                .filter(s -> !s.matches("^AUC_\\d{1,3} -1$"))
                .map(AucItem::new)
                .filter(i -> storageService.isNewAutItem(i))
                .map(item -> messageCreationService.createMessage(item))
                .collect(Collectors.toList());

        //aucItems.forEach(i -> );
    }
}