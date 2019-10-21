package ru.npte.sloth.slothhelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.npte.sloth.slothhelper.component.ItemsCache;
import ru.npte.sloth.slothhelper.dto.AucItem;

import java.util.List;

import static ru.npte.sloth.slothhelper.utils.ListUtils.isEmpty;

@Service
public class MessageFactory {

    @Autowired
    private ItemsCache itemsCache;

    public String createMessage(AucItem aucItem) {

        StringBuilder sb = new StringBuilder();

        sb.append(aucItem.getSeller())
                .append(" now sell ")
                .append(aucItem.getName())
                .append("<br/>")
                .append("Current price: ")
                .append(aucItem.getCurrent());

        if (!"-1".equals(aucItem.getBuyout())) {
            sb.append("<br/>").append(aucItem.getCurrent());
        }

        sb.append("<br/>")
                .append(aucItem.getAge());

        List<String> itemInfo = itemsCache.getItemInfo(aucItem.getName());

        if (isEmpty(itemInfo)) {
            return sb.toString();
        }

        if (itemInfo.size() == 1) {
            return sb.append(itemInfo.get(0)).toString();
        }

        sb.append("<br/>In eqlist few items with this desc:<br/>");

    }
}
