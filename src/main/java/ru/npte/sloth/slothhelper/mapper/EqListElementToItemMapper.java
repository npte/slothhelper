package ru.npte.sloth.slothhelper.mapper;

import org.jsoup.nodes.Element;
import ru.npte.sloth.slothhelper.dto.Item;
import ru.npte.sloth.slothhelper.exceptions.AcDivNotFoundException;
import ru.npte.sloth.slothhelper.exceptions.NameDivNotFoundException;
import ru.npte.sloth.slothhelper.exceptions.TitleDivNotFoundException;

import java.util.Optional;
import java.util.stream.Collectors;

import static ru.npte.sloth.slothhelper.enums.EqListClassNames.*;

public class EqListElementToItemMapper {

    public static Item map(Element element) {
        Item item = new Item();

        Element titleDiv = element
                .getElementsByClass(CELL_TITLE.getName())
                .stream()
                .findFirst()
                .orElseThrow(TitleDivNotFoundException::new);

        String title = titleDiv
                .getElementsByClass(NAME.getName())
                .stream()
                .findFirst()
                .orElseThrow(NameDivNotFoundException::new)
                .text();

        item.setTitle(title);

        item.setName(title.replaceAll("\\[.+\\]", ""));

        titleDiv.getElementsByClass(EXTRA.getName())
                .stream()
                .findFirst()
                .ifPresent(e -> item.setFlags(e.text()));


        item.setAc(element
                .getElementsByClass(CELL_AC.getName())
                .stream()
                .findFirst()
                .orElseThrow(TitleDivNotFoundException::new)
                .getElementsByTag("span")
                .stream()
                .findFirst()
                .orElseThrow(AcDivNotFoundException::new)
                .text());

        Optional<Element> statsDiv = element
                .getElementsByClass(CELL_STATS.getName())
                .stream()
                .findFirst();

        statsDiv.ifPresent(a -> a.childNodes()
                .stream()
                .filter(e -> ((Element)e).classNames().isEmpty())
                .findFirst()
                .ifPresent(e -> item.setStats(((Element) e).text())));

        if (statsDiv.isPresent()) {

            statsDiv.get().getElementsByClass(CPS_FOR.getName()).stream().findFirst().ifPresent(
                    e -> item.setCpFor(e.childNodes()
                            .stream()
                            .filter(el -> el instanceof Element)
                            .filter(el -> ((Element)el).hasClass(ATTACH_ITEM.getName()))
                            .map(el -> ((Element) el).text())
                            .collect(Collectors.toList()))
            );

            statsDiv.get().getElementsByClass(HAS_CLASP.getName()).stream().findFirst().ifPresent(
                    e -> item.setClasps(e.childNodes()
                            .stream()
                            .filter(el -> el instanceof Element)
                            .filter(el -> ((Element)el).hasClass(ATTACH_ITEM.getName()))
                            .map(el -> ((Element) el).text())
                            .collect(Collectors.toList()))
            );

            statsDiv.get().getElementsByClass(ATTACHES.getName()).stream().findFirst().ifPresent(
                   e -> item.setAttachTo(e.childNodes()
                           .stream()
                           .filter(el -> el instanceof Element)
                           .filter(el -> ((Element)el).hasClass(ATTACH_ITEM.getName()))
                           .map(el -> ((Element) el).text())
                           .collect(Collectors.toList()))
            );
        }

        return item;
    }

}


