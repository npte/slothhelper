package ru.npte.sloth.slothhelper.component;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.npte.sloth.slothhelper.dto.Item;
import ru.npte.sloth.slothhelper.mapper.EqListElementToItemMapper;

import java.io.IOException;
import java.util.Collections;
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
        try {
            List<Item> res = Jsoup.connect(EQ_LIST_URL + searchQuery(itemName))
                    .get()
                    .getElementsByClass(ITEM.getName()).stream()
                    .map(EqListElementToItemMapper::map)
                    .filter(it -> itemName.equalsIgnoreCase(it.getName()))
                    .collect(Collectors.toList());

            if (res.size() > 0) {
                return res.stream().map(Item::toString).collect(Collectors.toList());
            }

            //Если пусто - возможно это руна
            String runeName = itemName.replaceAll("(^a )|(^A )|(^an )|(^An )|(^the )|(^The )", "") + " rune";
            res = Jsoup.connect(EQ_LIST_URL + searchQuery(runeName))
                    .get()
                    .getElementsByClass(ITEM.getName()).stream()
                    .map(EqListElementToItemMapper::map)
                    .filter(it -> runeName.replaceAll(" ", "_").equalsIgnoreCase(it.getKeywords().replaceAll(" ", "_")))
                    .collect(Collectors.toList());

            if (res.size() > 0) {
                Item item = new Item();
                item.setTitle(itemName);
                item.setCpFor(res.stream().map(Item::toString).collect(Collectors.toList()));
                return Collections.singletonList(item.toString());
            }

        } catch (IOException e) {
            logger.error("Failed while get item info from eqlist", e);
        }
        return null;
    }

    private String searchQuery(String itemName) {
        //String searchQuery =  .replace("'", "%27").replace(" ", "+");
        String searchQuery =  itemName.replaceAll("'", "%27").replaceAll(" ", "+");
        logger.info("Search query: {} -> {}", itemName, searchQuery);
        return searchQuery;
    }
}
