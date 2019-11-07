package ru.npte.sloth.slothhelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.npte.sloth.slothhelper.component.ItemsCache;
import ru.npte.sloth.slothhelper.dto.AucItem;

import java.util.List;

import static ru.npte.sloth.slothhelper.utils.ListUtils.isEmpty;

@Service
public class MessageCreationService {

    @Autowired
    private ItemsCache itemsCache;

    private static final int MINUTES_IN_DAY = 24 * 60;
    private static final int MINUTEST_IN_HOUR = 60;

    public String createMessage(AucItem aucItem) {

        StringBuilder sb = new StringBuilder();

        sb.append(aucItem.getSeller())
                .append(" now sell ")
                .append(aucItem.getName())
                .append("%0A")
                .append("Current price: ")
                .append(aucItem.getCurrent());

        if (!"-1".equals(aucItem.getBuyout())) {
            sb.append("%0A").append("Buyout: ").append(aucItem.getBuyout());
        }

        sb.append("%0A").append("Ends: ").append(getEndsString(aucItem.getAge()));

        List<String> itemInfo = itemsCache.getItemInfo(aucItem.getName());

        if (isEmpty(itemInfo)) {
            return sb.toString();
        }

        if (itemInfo.size() == 1) {
            return sb.append("%0A").append(itemInfo.get(0)).toString();
        }

        sb.append("%0AEqlist have few items with this desc:");

        itemInfo.forEach(i -> sb.append("%0A").append(i));

        return sb.toString();
    }

    private String getEndsString(String age) {
        int ageInt = Integer.parseInt(age);
        int days = ageInt / MINUTES_IN_DAY;
        int hours = (ageInt % MINUTES_IN_DAY) / MINUTEST_IN_HOUR;
        int minutes = ageInt - days * MINUTES_IN_DAY - hours * MINUTEST_IN_HOUR;

        return String.format("%dd %dh %dm", days, hours, minutes);
    }
}
