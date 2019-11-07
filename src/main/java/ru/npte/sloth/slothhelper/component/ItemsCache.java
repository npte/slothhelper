package ru.npte.sloth.slothhelper.component;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.npte.sloth.slothhelper.mapper.EqListElementToStringMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.npte.sloth.slothhelper.enums.EqListClassNames.ITEM;
import static ru.npte.sloth.slothhelper.utils.ListUtils.isNotEmpty;

@Component
public class ItemsCache {

    private static final Logger logger = LoggerFactory.getLogger(ItemsCache.class);

    private static final String EQ_LIST_URL = "http://slothmudeq.tk/?search=";

    private final Map<String, List<String>> cache = new HashMap<>();

    public List<String> getItemInfo(String itemName) {
        List<String> itemInfo = cache.get(itemName);

        if (itemInfo != null) {
            return itemInfo;
        }

        itemInfo = getInfoFromWeb(itemName);

        if (isNotEmpty(itemInfo)) {
            cache.put(itemName, itemInfo);
        }

        return  itemInfo;
    }

    private List<String> getInfoFromWeb(String itemName) {
        List<String> res = null;
        try {
            res = Jsoup.connect(EQ_LIST_URL + searchQuery(itemName))
                    .get()
                    .getElementsByTag("div").stream()
                    .filter(it -> it.hasClass(ITEM.getName()))
                    .map(EqListElementToStringMapper::map)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Failed while get item info from eqlist", e);
        }
        return res;
    }

    private String searchQuery(String itemName) {
        String searchQuery =  itemName.replaceAll("(^a )|(^A )|(^an )|(^An )|(^the )|(^The )", "").replace("'", "%27").replace(" ", "+");
        logger.info("Search query: {} -> {}", itemName, searchQuery);
        return searchQuery;
    }
}
